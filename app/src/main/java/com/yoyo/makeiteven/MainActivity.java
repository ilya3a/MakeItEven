package com.yoyo.makeiteven;

import android.app.Activity;

import android.graphics.drawable.Animatable;
import android.os.Bundle;

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


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        final Animation btn_releas= AnimationUtils.loadAnimation(this,R.anim.btn_realeas);
        View.OnTouchListener btn_animation = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    v.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.startAnimation(btn_releas);
                }
                return false;
            }
        };
        btn1.setOnTouchListener(btn_animation);
        btn2.setOnTouchListener(btn_animation);
        btn3.setOnTouchListener(btn_animation);
        btn4.setOnTouchListener(btn_animation);


        final Game game = new Game(12);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theNumber.setText(String.valueOf(game.gameGenerator(btns))+"  "+game.getHint());
            }
        });

    }

}


