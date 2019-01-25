package com.yoyo.makeiteven;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class Start_Screen extends Activity {

    ImageView game_logo;
    Button stage_mode_btn,arcade_mode_btn;
    ImageButton setting_btn;
    Boolean isRotated=Boolean.FALSE;
    RelativeLayout main_layout;
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
        setting_btn = findViewById(R.id.setting_btn);
        main_layout=findViewById(R.id.Main_layout);
        //btn animation
        final Animation btn_press = AnimationUtils.loadAnimation(this,R.anim.btn_pressed);
        final Animation btn_releas= AnimationUtils.loadAnimation(this,R.anim.btn_realeas);

        //on touch buttn animation
        View.OnTouchListener btn_animation= new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.startAnimation(btn_releas);
                }
                return false;
            }
        };
        stage_mode_btn.setOnTouchListener(btn_animation);
        arcade_mode_btn.setOnTouchListener(btn_animation);

        //starts arcade mode
        arcade_mode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start_Screen.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Start_Screen.this, setting_activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        stage_mode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    protected void toggleRotation(View v){
        if(isRotated){
            v.setRotation(0.0f);
            isRotated = false;
        }else {
            v.setRotation(90.0f);
            isRotated = true;
        }
    }






    public void ShowDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        final SeekBar seek2 = new SeekBar(this);
        seek.setMax(100);

        popDialog.setTitle("Master Volume");
        popDialog.setView(seek);
        popDialog.setView(R.id.Main_layout);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Do something here with new value
                //txtView.setText("Value of : " + progress);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });


        // Button OK
        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });


        popDialog.create();
        popDialog.show();

    }

    @Override
    protected void onResume() {
        TransitionDrawable transition = (TransitionDrawable) main_layout.getBackground();
        transition.startTransition(350);
        super.onResume();
    }

    @Override
    protected void onPause() {
        TransitionDrawable transition = (TransitionDrawable) main_layout.getBackground();
        transition.reverseTransition(350);
        super.onPause();
    }
}
