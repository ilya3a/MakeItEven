package com.yoyo.makeiteven;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import moe.codeest.enviews.ENVolumeView;

public class StartScreenActivity extends Activity {

    ImageView game_logo;
    Button stage_mode_btn, arcade_mode_btn;
    ImageButton setting_btn;
    Boolean isRotated= Boolean.FALSE;
    RelativeLayout main_layout;
    ENVolumeView volumeView;
    SeekBar sbVolume;
//    private Context mContext;
//    private Activity mActivity;

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
        //test
//        mContext = getApplicationContext();
//        mActivity = StartScreenActivity.this;
//        final RelativeLayout mCLayout =findViewById(R.id.Main_layout);

        //logo animation
        game_logo = findViewById(R.id.game_logo);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        game_logo.startAnimation(bounce);
        //finding views
        stage_mode_btn = findViewById(R.id.stage_mode_btn);
        arcade_mode_btn = findViewById(R.id.arcade_mode_btn);
        setting_btn = findViewById(R.id.setting_btn);
        main_layout=findViewById(R.id.Main_layout);

        TiltEffectAttacher.attach(game_logo);
        //btn animation
        final Animation btn_press = AnimationUtils.loadAnimation(this, R.anim.btn_pressed);
        final Animation btn_releas = AnimationUtils.loadAnimation(this, R.anim.btn_realeas);

        //on touch button animation
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
                Intent intent = new Intent(StartScreenActivity.this, GameActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(mContext, GameActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(StartScreenActivity.this);
                startActivity(intent, compat.toBundle());
            }
        });
        stage_mode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreenActivity.this, LevelsActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(mContext, GameActivity.class);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(StartScreenActivity.this);
                startActivity(intent, compat.toBundle());
            }
        });

        //
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(StartScreenActivity.this, SettingActivity.class);
                //startActivity(i);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                final Dialog yourDialog = new Dialog(StartScreenActivity.this);
                yourDialog.setCanceledOnTouchOutside(false);
                LayoutInflater inflater = (LayoutInflater) StartScreenActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layoutToinflate = inflater.inflate(R.layout.activity_setting, (ViewGroup)findViewById(R.id.root_element_settings));
                yourDialog.setContentView(layoutToinflate);
                //dialog inits
                Button reset = layoutToinflate.findViewById(R.id.game_reset_btn);
                volumeView = layoutToinflate.findViewById(R.id.view_volume);
                sbVolume =  layoutToinflate.findViewById(R.id.seek_bar_1);
                sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        volumeView.updateVolumeValue(i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
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
                        rotat_setting();
                    }
                });
                close_btn.setOnTouchListener(btn_animation);
                rotat_setting();
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



    private void Reset_game(){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(StartScreenActivity.this);
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

    private void rotat_setting(){
        ChangeTransform changeTransform = new ChangeTransform();
        changeTransform.setDuration(200);
        changeTransform.setInterpolator(new AccelerateInterpolator());
        TransitionManager.beginDelayedTransition(main_layout,changeTransform);
        toggleRotation(setting_btn);
    }
}
