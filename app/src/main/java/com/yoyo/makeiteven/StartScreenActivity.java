package com.yoyo.makeiteven;

import android.app.Activity;
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


public class StartScreenActivity extends AppCompatActivity implements SettingFragment.SettingsFragmentListener {

    ImageView game_logo;
    Button stage_mode_btn, arcade_mode_btn;
    ImageButton setting_btn, scoreBoard_btn;
    Boolean isRotated = Boolean.FALSE;
    RelativeLayout main_layout;
    SettingFragment settingFragment = new SettingFragment();
    float currentMainVolume;
    public static MediaPlayer gameMusic;
    private Toolbar toolBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        setContentView(R.layout.activity_start__screen);

        AudioManager.getInstance(this).startGameMusic();


        //logo animation
        game_logo = findViewById(R.id.game_logo);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        game_logo.startAnimation(bounce);
        //finding views
        stage_mode_btn = findViewById(R.id.stage_mode_btn);
        arcade_mode_btn = findViewById(R.id.arcade_mode_btn);
        setting_btn = findViewById(R.id.setting_btn);
        main_layout = findViewById(R.id.start_activity_container);
        scoreBoard_btn = findViewById(R.id.scoreBoard_btn);

        toolBar = findViewById(R.id.app_bar_start_activity);
        setSupportActionBar(toolBar);

        TiltEffectAttacher.attach(game_logo);
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


        stage_mode_btn.setOnTouchListener(btn_animation);
        arcade_mode_btn.setOnTouchListener(btn_animation);
        scoreBoard_btn.setOnTouchListener(btn_animation);
        scoreBoard_btn.setOnTouchListener(btn_animation);

        scoreBoard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartScreenActivity.this, ScoreBoardActivity.class));
            }
        });
        //starts arcade mode
        arcade_mode_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GameActivity.startGameActivity(StartScreenActivity.this, ArcadeGameMode.TYPE, 0);

            }
        });


        stage_mode_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LevelsActivity.startLevelsActivity(StartScreenActivity.this);

            }
        });


        setting_btn.setOnTouchListener(btn_animation);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotat_setting();
                scoreBoard_btn.setEnabled(false);
                setting_btn.setEnabled(false);
                stage_mode_btn.setVisibility(View.INVISIBLE);
                arcade_mode_btn.setVisibility(View.INVISIBLE);

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

        Button tutorialBtn = findViewById(R.id.totorial_btn);
        tutorialBtn.setOnTouchListener(btn_animation);
        tutorialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreenActivity.this, TutorialActivity.class);
                startActivity(intent);
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


    private void rotat_setting() {
        ChangeTransform changeTransform = new ChangeTransform();
        changeTransform.setDuration(400);
        changeTransform.setInterpolator(new AccelerateInterpolator());
        TransitionManager.beginDelayedTransition(main_layout, changeTransform);
        toggleRotation(setting_btn);
    }

    public static void startStartScreenActivity(Context context) {
        Intent intent = new Intent(context, StartScreenActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void OnSeekBarMainVolume(int mainVolume) {
        AudioManager.getInstance(this).setGameVolume(mainVolume);
    }

    @Override
    public void OnSeekBarSoundEffects(int soundEffectsVolume) {

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        AudioManager.getInstance(this).startGameMusic();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        AudioManager.getInstance(this).pauseGameMusic();
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
        stage_mode_btn.setVisibility(View.VISIBLE);
        arcade_mode_btn.setVisibility(View.VISIBLE);
        scoreBoard_btn.setEnabled(true);
        setting_btn.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (!scoreBoard_btn.isEnabled()) {
            getFragmentManager().beginTransaction().remove(settingFragment).commit();
            OnExit();
        } else {
            super.onBackPressed();
            AudioManager.getInstance(this).stopGameMusic();
        }
    }


}
