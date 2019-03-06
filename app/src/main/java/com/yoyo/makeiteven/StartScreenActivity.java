package com.yoyo.makeiteven;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Objects;


public class StartScreenActivity extends AppCompatActivity implements SettingFragment.SettingsFragmentListener {

    ImageView gameLogo;
    Button stageModeBtn, arcadeModeBtn, tutorialBtn;
    ImageButton settingBtn, scoreBoard_btn;
    Boolean isRotated = Boolean.FALSE;
    RelativeLayout mainLayout;
    SettingFragment settingFragment = new SettingFragment();
    float currentMainVolume;
    public static MediaPlayer gameMusic;
    public static MediaPlayer testSound;
    private Toolbar toolBar;
    MenuItem aboutUsActionBar, settingsActionBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu, menu);
        settingsActionBar = menu.findItem(R.id.action_settings);
        settingsActionBar.setIcon(R.drawable.ic_info_black);
        aboutUsActionBar = menu.findItem(R.id.action_about);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                //about
                final Dialog aboutDialog = new Dialog(this);
                aboutDialog.setCanceledOnTouchOutside(false);
                final View dialogView = getLayoutInflater().inflate(R.layout.about_dialog, null);
                aboutDialog.setContentView(dialogView);
                aboutDialog.setCanceledOnTouchOutside(true);
                aboutDialog.show();

                break;
            case R.id.action_settings:
                //settings
                rotate_setting();
                scoreBoard_btn.setEnabled(false);
                settingBtn.setEnabled(false);
                stageModeBtn.setVisibility(View.INVISIBLE);
                arcadeModeBtn.setVisibility(View.INVISIBLE);
                tutorialBtn.setVisibility(View.INVISIBLE);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.add(R.id.start_activity_container, settingFragment).commit();
                item.setEnabled(false);
                break;
            default:

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);

            Explode explode = new Explode();
            explode.setDuration(600);
            getWindow().setEnterTransition(explode);


        }

        setContentView(R.layout.activity_start_screen);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                currentMainVolume = (DataStore.getInstance(StartScreenActivity.this).getMainSoundSetting());
                gameMusic = MediaPlayer.create(StartScreenActivity.this, R.raw.super_duper_by_ian_post);
                gameMusic.setVolume(currentMainVolume / 100, (currentMainVolume / 100));
                gameMusic.setLooping(true);
                gameMusic.start();
            }
        });

        testSound = MediaPlayer.create(this, R.raw.bip_test);
        //logo animation
        gameLogo = findViewById(R.id.game_logo);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        gameLogo.startAnimation(bounce);
        //finding views
        stageModeBtn = findViewById(R.id.stage_mode_btn);
        arcadeModeBtn = findViewById(R.id.arcade_mode_btn);
        settingBtn = findViewById(R.id.setting_btn);
        mainLayout = findViewById(R.id.start_activity_container);
        scoreBoard_btn = findViewById(R.id.scoreBoard_btn);
        aboutUsActionBar = findViewById(R.id.action_about);
        settingsActionBar = findViewById(R.id.action_settings);

        toolBar = findViewById(R.id.app_bar_start_activity);
        setSupportActionBar(toolBar);

        //hide the actionBar title
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TiltEffectAttacher.attach(gameLogo);
        //btn animation
        final Animation btn_press = AnimationUtils.loadAnimation(this, R.anim.btn_pressed);
        final Animation btn_release = AnimationUtils.loadAnimation(this, R.anim.btn_realeas);

        //on touch button animation
        final View.OnTouchListener btn_animation = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(btn_release);
                }
                return false;
            }
        };


        stageModeBtn.setOnTouchListener(btn_animation);
        arcadeModeBtn.setOnTouchListener(btn_animation);
        scoreBoard_btn.setOnTouchListener(btn_animation);
        scoreBoard_btn.setOnTouchListener(btn_animation);

        scoreBoard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenActivity.this, ScoreBoardActivity.class));
            }
        });
        //starts arcade mode
        arcadeModeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GameActivity.startGameActivity(StartScreenActivity.this, ArcadeGameMode.TYPE, 0);

            }
        });


        stageModeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LevelsActivity.startLevelsActivity(StartScreenActivity.this);

            }
        });


        settingBtn.setOnTouchListener(btn_animation);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotate_setting();
                scoreBoard_btn.setEnabled(false);
                settingBtn.setEnabled(false);
                stageModeBtn.setVisibility(View.INVISIBLE);
                arcadeModeBtn.setVisibility(View.INVISIBLE);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.add(R.id.start_activity_container, settingFragment).commit();


//                //Intent i = new Intent(StartScreenActivity.this, SettingFragment.class);
////                //startActivity(i);
////                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
////                final Dialog yourDialog = new Dialog( StartScreenActivity.this );
////                yourDialog.setCanceledOnTouchOutside( false );
////                LayoutInflater inflater = (LayoutInflater) StartScreenActivity.this.getSystemService( LAYOUT_INFLATER_SERVICE );
////                View layoutToinflate = inflater.inflate( R.layout.fragment_setting, (ViewGroup) findViewById( R.id.root_element_settings ) );
////                yourDialog.setContentView( layoutToinflate );
////
////                Button reset = layoutToinflate.findViewById( R.id.game_reset_btn );
////                reset.setOnTouchListener( btn_animation );
////
////                reset.setOnClickListener( new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Reset_game();
////                    }
////                } );
////                ImageButton close_btn = layoutToinflate.findViewById( R.id.close_btn );
////                close_btn.setOnClickListener( new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        yourDialog.dismiss();
////                        rotat_setting();
////                    }
////                } );
////                close_btn.setOnTouchListener( btn_animation );
////                rotat_setting();
////                yourDialog.show();

            }
        });

        tutorialBtn = findViewById(R.id.totorial_btn);
        tutorialBtn.setOnTouchListener(btn_animation);
        tutorialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(StartScreenActivity.this, TutorialActivity.class);
                //startActivity(intent);
            }
        });
    }

    private void toggleRotation(View v) {
        if (isRotated) {
            v.setRotation(0.0f);
            isRotated = false;
        } else {
            v.setRotation(90.0f);
            isRotated = true;
        }
    }


    private void rotate_setting() {
        ChangeTransform changeTransform = new ChangeTransform();
        changeTransform.setDuration(400);
        changeTransform.setInterpolator(new AccelerateInterpolator());
        TransitionManager.beginDelayedTransition(mainLayout, changeTransform);
        toggleRotation(settingBtn);
    }

    public static void startStartScreenActivity(Context context) {
        Intent intent = new Intent(context, StartScreenActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void OnSeekBarMainVolume(int mainVolume) {
        // AudioManager.getInstance(this).setGameVolume(mainVolume);
        gameMusic.setVolume((float) mainVolume / 100, (float) mainVolume / 100);

    }

    @Override
    public void OnSeekBarSoundEffects(int soundEffectsVolume) {
        // AudioManager.getInstance(this).setEffectVolume(soundEffectsVolume);
        testSound.setVolume((float) soundEffectsVolume / 100, (float) soundEffectsVolume / 100);
        testSound.start();


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        gameMusic.start();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        gameMusic.pause();
    }


    @Override
    public void OnResetGame() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(StartScreenActivity.this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.game_reset));
        alertDialogBuilder.setIcon(R.drawable.warning_icon);
        alertDialogBuilder.setMessage(R.string.Progress)
                .setCancelable(false)
                .setPositiveButton(R.string.Yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                int currentStage = 1;
                                DataStore.getInstance(StartScreenActivity.this).saveCurrentStage(currentStage);
                                DataStore.getInstance(StartScreenActivity.this).resetLevels();


                                dialog.cancel();
                            }
                        });
        alertDialogBuilder.setNegativeButton(R.string.No,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void OnExit() {
        stageModeBtn.setVisibility(View.VISIBLE);
        arcadeModeBtn.setVisibility(View.VISIBLE);
//        tutorialBtn.setVisibility(View.VISIBLE);
        scoreBoard_btn.setEnabled(true);
        settingBtn.setEnabled(true);
        settingsActionBar.setEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if (!scoreBoard_btn.isEnabled()) {
            getFragmentManager().beginTransaction().remove(settingFragment).commit();
            OnExit();
        } else {
            super.onBackPressed();
            gameMusic.stop();
        }
    }


}
