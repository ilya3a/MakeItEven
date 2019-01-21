package com.yoyo.makeiteven;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.transition.ChangeTransform;
import android.transition.TransitionManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
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
        arcade_mode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start_Screen.this,MainActivity.class);
                startActivity(intent);
            }
        });
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout mCLayout =findViewById(R.id.Main_layout);
                ChangeTransform changeTransform = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    changeTransform = new ChangeTransform();
                }

                // Set the duration for transition
                changeTransform.setDuration(500);

                // Set the transition interpolator
                changeTransform.setInterpolator(new AccelerateInterpolator());

                // Begin the delayed transition
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    TransitionManager.beginDelayedTransition(mCLayout,changeTransform);
                }

                // Toggle the button rotation state
                toggleRotation(setting_btn);
                ShowDialog();
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
        seek.setMax(100);

        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Please Select Rank 1-100 ");
        popDialog.setView(seek);

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

}
