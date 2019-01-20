package com.yoyo.makeiteven;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Start_Screen extends Activity {

    ImageView game_logo;
    Button stage_mode_btn,arcade_mode_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__screen);
        //logo animation
        game_logo=findViewById(R.id.game_logo);
        Animation bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        game_logo.startAnimation(bounce);
        //finding views
        stage_mode_btn =findViewById(R.id.stage_mode_btn);
        arcade_mode_btn=findViewById(R.id.arcade_mode_btn);
        //btn animation
        final Animation btn_press = AnimationUtils.loadAnimation(this,R.anim.btn_pressed);
        final Animation btn_releas= AnimationUtils.loadAnimation(this,R.anim.btn_realeas);

        stage_mode_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    stage_mode_btn.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    btn_press.setFillAfter(false);
                    stage_mode_btn.startAnimation(btn_releas);
                }
                return false;
            }
        });
        arcade_mode_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    arcade_mode_btn.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    btn_press.setFillAfter(false);
                    arcade_mode_btn.startAnimation(btn_releas);
                }
                return false;
            }
        });
    }
}
