package com.yoyo.makeiteven;

import android.app.Activity;

import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.TextView;


import java.util.ArrayList;

import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

Boolean ispressd=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
        }
        setContentView(R.layout.activity_main);

        final TextView theNumber = findViewById(R.id.the_number);
        final Button btn1 = findViewById(R.id.btn1);
        final Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button start = findViewById(R.id.start_btn);

        final List<Button> btns = new ArrayList<>();
        btns.add(btn1);
        btns.add(btn2);
        btns.add(btn3);
        btns.add(btn4);

        final Animation scale_out = AnimationUtils.loadAnimation(this,R.anim.scale_out);
        final Animation scale_in = AnimationUtils.loadAnimation(this,R.anim.scale_in);
        final Animation btn_press = AnimationUtils.loadAnimation(this,R.anim.btn_pressed);
        final Animation btn_release= AnimationUtils.loadAnimation(this,R.anim.btn_realeas);
        View.OnTouchListener btn_animation = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    v.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.startAnimation(btn_release);
                }

                return false;
            }
        };
        btn1.setOnTouchListener(btn_animation);
        btn2.setOnTouchListener(btn_animation);
        btn3.setOnTouchListener(btn_animation);
        btn4.setOnTouchListener(btn_animation);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        if(ispressd){
                            btn1.setPressed(false);
                            ispressd=false;
                        }
                        else {
                            btn1.setPressed(true);
                            ispressd=true;
                        }
                    }});

            }
        });


        final Game game = new Game(12);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theNumber.setText(String.valueOf(game.gameGenerator(btns)) + "  " + game.getHint());
            }
        });

    }

    @Override
    public void onClick(View v) {

        v.setTag(true);

    }

}



