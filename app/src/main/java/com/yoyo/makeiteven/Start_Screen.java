package com.yoyo.makeiteven;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class Start_Screen extends Activity {

    ImageView game_logo;
    Button stage_mode_btn, arcade_mode_btn;
    ImageButton setting_btn;
    Boolean isRotated=Boolean.FALSE;
    RelativeLayout main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);

            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);


        }
        setContentView(R.layout.activity_start__screen);

        //logo animation
        game_logo = findViewById(R.id.game_logo);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        game_logo.startAnimation(bounce);
        //finding views
        stage_mode_btn = findViewById(R.id.stage_mode_btn);
        arcade_mode_btn = findViewById(R.id.arcade_mode_btn);
        setting_btn = findViewById(R.id.setting_btn);
        main_layout=findViewById(R.id.Main_layout);
        //btn animation
        final Animation btn_press = AnimationUtils.loadAnimation(this, R.anim.btn_pressed);
        final Animation btn_releas = AnimationUtils.loadAnimation(this, R.anim.btn_realeas);

        //on touch buttn animation
        final View.OnTouchListener btn_animation= new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
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
                Intent intent = new Intent(Start_Screen.this, MainActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(mContext, MainActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(Start_Screen.this,null);
                startActivity(intent, compat.toBundle());
            }
        });
        stage_mode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start_Screen.this, LevelsActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(mContext, MainActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(Start_Screen.this,  null);
                startActivity(intent, compat.toBundle());
            }
        });

        //
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(Start_Screen.this, SettingActivity.class);
                //startActivity(i);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                final Dialog yourDialog = new Dialog(Start_Screen.this);
                LayoutInflater inflater = (LayoutInflater)Start_Screen.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layoutToinflate = inflater.inflate(R.layout.activity_setting, (ViewGroup)findViewById(R.id.root_element));
                yourDialog.setContentView(layoutToinflate);
                Button reset = layoutToinflate.findViewById(R.id.game_reset_btn);
                reset.setOnTouchListener(btn_animation);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Reset_game();
                    }
                });
                ImageButton close_btn=layoutToinflate.findViewById(R.id.close_btn);
                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yourDialog.dismiss();
                    }
                });
                close_btn.setOnTouchListener(btn_animation);
                yourDialog.show();
            }
        });

    }

    protected void toggleRotation(View v) {
        if (isRotated) {
            v.setRotation(0.0f);
            isRotated = false;
        } else {
            v.setRotation(90.0f);
            isRotated = true;
        }
    }


    public void ShowDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        final SeekBar seek2 = new SeekBar(this);
        seek.setMax(100);

        popDialog.setTitle("Master Volume");
        popDialog.setView(seek);
        popDialog.setView(R.id.Main_layout);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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
        //TransitionDrawable transition = (TransitionDrawable) main_layout.getBackground();
       // transition.startTransition(350);
        super.onResume();
    }

    @Override
    protected void onPause() {
       // TransitionDrawable transition = (TransitionDrawable) main_layout.getBackground();
        //transition.reverseTransition(350);
        super.onPause();
    }
    private void Reset_game(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(Start_Screen.this);
        alertDialogBuilder.setTitle("Game Reset");
        alertDialogBuilder.setIcon(R.drawable.warning_icon);
        alertDialogBuilder.setMessage(R.string.Progress)
                .setCancelable(false)
                .setPositiveButton(R.string.Yes,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                dialog.cancel();
                            }
                        });
        alertDialogBuilder.setNegativeButton(R.string.No,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
