package com.yoyo.makeiteven;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.jinatonic.confetti.CommonConfetti;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class GameActivity extends Activity implements View.OnClickListener, EndOfArcadeGameFragment.EndOfArcadeGameFragmentListener {

    //180000
    private long timeLeftInMillSeconds = 30000;//5:00 mints
    private int mLevelNum;
    int selectedOperatorId, selectedNumberId_1, selectedNumberId_2;
    int num1 = Integer.MAX_VALUE, num2 = Integer.MAX_VALUE;
    private boolean timerRunning;

    boolean isOperatorSelected = false, isNumberSelected = false;
    private CountDownTimer mCountDownTimer;
    TextView mCountDownTv, mScoreTv, mActualScoreTv;
    MyTextView mTheDesiredNumberTv;
    List<ToggleButton> gameBtns;
    List<ToggleButton> operators;
    String operator = "";
    int theDesiredNumber = 0;
    Animation scale_out, scale_in, bounce_shake;
    ImageButton mGameResetBtnIb, backBtnIb, hintBtnIb, hintBtn_2, hintBtn_3;
    AbstractGame mAbstractGame;
    String mGameType;
    private int scoreCounter = 0;
    private int winsCounter = 0;
    static final String EXTRA_GAME_TYPE = "extra_game_type";
    static final String EXTRA_LEVEL_NUMBER = "extra_level_number";
    static final String SCORE_COUNTER = "score_counter";
    private ArrayList<StageInfo> stageInfosArray;
    private String mHint;
    private ImageView countdownImageView;
    private AnimationDrawable cuntDownAnim;
    private RelativeLayout hidingLayout;
    private float sound_Effects_Volume;
    private MediaPlayer taDaplayer;


    @Override
    protected void onUserLeaveHint() {
        onBackPressed();
        StartScreenActivity.gameMusic.pause();
        super.onUserLeaveHint();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        StartScreenActivity.gameMusic.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        StartScreenActivity.gameMusic.stop();
        if (mGameType.equals(ArcadeGameMode.TYPE)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCountDownTimer.cancel();
//                    StartScreenActivity.gameMusic.stop();

                    finish();
                }
            }, 3000);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        StartScreenActivity.gameMusic.start();
        if (mGameType.equals(ArcadeGameMode.TYPE)) {
//            StartScreenActivity.gameMusic.stop();
            cuntDownAnim.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    countdownImageView.setVisibility(View.GONE);
                    hidingLayout.setVisibility(View.GONE);
                    startTimer();
                    for (ToggleButton tb : gameBtns) {
                        tb.startAnimation(bounce_shake);
                    }
//                    StartScreenActivity.gameMusic = MediaPlayer.create(GameActivity.this, R.raw.super_duper_by_ian_post);
//                    StartScreenActivity.gameMusic.setVolume((float) (DataStore.getInstance(GameActivity.this).getMainSoundSetting()) / 100, (float) (DataStore.getInstance(GameActivity.this).getMainSoundSetting()) / 100);
//                    StartScreenActivity.gameMusic.start();
                }
            }, 3000);
        } else if (mGameType.equals(StageGameMode.TYPE)) {
            hidingLayout.setVisibility(View.GONE);
            countdownImageView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Explode explode = new Explode();
            explode.setDuration(600);
            getWindow().setEnterTransition(explode);
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        scale_out = AnimationUtils.loadAnimation(this, R.anim.scale_out);
        scale_in = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        final Animation btn_press = AnimationUtils.loadAnimation(this, R.anim.btn_pressed);
        final Animation btn_release = AnimationUtils.loadAnimation(this, R.anim.btn_realeas);
        mScoreTv = findViewById(R.id.score_tv);
        mActualScoreTv = findViewById(R.id.actual_score_tv);
        mTheDesiredNumberTv = findViewById(R.id.the_number);
        mCountDownTv = findViewById(R.id.timer_txt);
        ToggleButton btn1 = findViewById(R.id.btn1);
        ToggleButton btn2 = findViewById(R.id.btn2);
        ToggleButton btn3 = findViewById(R.id.btn3);
        ToggleButton btn4 = findViewById(R.id.btn4);
        ToggleButton plusBtn = findViewById(R.id.plus);
        ToggleButton minusBtn = findViewById(R.id.minus);
        ToggleButton mulBtn = findViewById(R.id.mul);
        ToggleButton divButton = findViewById(R.id.div);
        mGameResetBtnIb = findViewById(R.id.restart_level);
        backBtnIb = findViewById(R.id.game_back_btn);
        hintBtnIb = findViewById(R.id.hint_btn);
        hidingLayout = findViewById(R.id.hiding_layout);
        hintBtn_2 = findViewById(R.id.hint_btn_2);
        hintBtn_3 = findViewById(R.id.hint_btn_3);

        sound_Effects_Volume = (float) (DataStore.getInstance(this).getSoundEffectSetting()) / 100;
        taDaplayer = MediaPlayer.create(this, R.raw.ta_da);
        taDaplayer.setVolume(sound_Effects_Volume, sound_Effects_Volume);

        bounce_shake = AnimationUtils.loadAnimation(this, R.anim.bounce_shake);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.12, 20);
        bounce_shake.setInterpolator(interpolator);


        View.OnClickListener hintListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mGameType.equals(ArcadeGameMode.TYPE)) {
                    ((ImageButton) v).setImageResource(R.drawable.ic_help_off);
                    ((ImageButton) v).setEnabled(false);
                    for (ToggleButton tb : gameBtns) {
                        tb.startAnimation(bounce_shake);
                    }
                    gameInit();
                } else if (mGameType.equals(StageGameMode.TYPE)) {
                    Toasty.info(GameActivity.this, mHint, Toast.LENGTH_SHORT, true).show();

                }

            }
        };
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
        hintBtnIb.setOnClickListener(hintListener);
        hintBtn_3.setOnClickListener(hintListener);
        hintBtn_2.setOnClickListener(hintListener);
        hintBtnIb.setOnTouchListener(btn_animation);
        hintBtn_3.setOnTouchListener(btn_animation);
        hintBtn_2.setOnTouchListener(btn_animation);


        countdownImageView = findViewById(R.id.countdown_imageview);
        countdownImageView.setBackgroundResource(R.drawable.three_two_one);
        cuntDownAnim = (AnimationDrawable) countdownImageView.getBackground();

        Bundle bundle = getIntent().getExtras();
        mGameType = bundle.getString(EXTRA_GAME_TYPE);
        mLevelNum = bundle.getInt(EXTRA_LEVEL_NUMBER);

        final View arcadeContainer = findViewById(R.id.arcade_container);


        createGameModel(mGameType);


        if (mGameType.equals(ArcadeGameMode.TYPE)) {
            arcadeContainer.setVisibility(View.VISIBLE);
            mActualScoreTv.setText("0");
            mGameResetBtnIb.setVisibility(View.GONE);


        } else if (mGameType.equals(StageGameMode.TYPE)) {
            hintBtn_2.setVisibility(View.GONE);
            hintBtn_3.setVisibility(View.GONE);
            arcadeContainer.setVisibility(View.VISIBLE);
            mActualScoreTv.setVisibility(View.GONE);
            mScoreTv.setVisibility(View.INVISIBLE);
            mCountDownTv.setText("Level: " + String.valueOf(mLevelNum));
        }

        init_toasty();

        backBtnIb.setOnTouchListener(btn_animation);
        final Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_restart);

        mGameResetBtnIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameResetBtnIb.startAnimation(rotateAnimation);
                gameInit();
                for (final ToggleButton tb : gameBtns) {
                    tb.startAnimation(bounce_shake);

                }
            }
        });
        backBtnIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        SingleSelectToggleGroup numberGroup = findViewById(R.id.group_choices_of_numbers);
        numberGroup.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {


                ToggleButton checkedToggleButton = findViewById(checkedId);
                if (isNumberSelected && isOperatorSelected) {
                    num2 = Integer.parseInt(checkedToggleButton.getText().toString());
                    selectedNumberId_2 = checkedId;
                } else {
                    num1 = Integer.parseInt(checkedToggleButton.getText().toString());
                    isNumberSelected = true;
                    selectedNumberId_1 = checkedId;
                }
            }
        });


        SingleSelectToggleGroup operatorGroup = findViewById(R.id.group_choices_of_operators);
        operatorGroup.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                ToggleButton checkedToggleButton = findViewById(checkedId);
                operator = checkedToggleButton.getTag().toString();
                isOperatorSelected = true;
                selectedOperatorId = checkedId;
            }

        });

        gameBtns = new ArrayList<>();
        gameBtns.add(btn1);
        gameBtns.add(btn2);
        gameBtns.add(btn3);
        gameBtns.add(btn4);

        operators = new ArrayList<>();
        operators.add(plusBtn);
        operators.add(minusBtn);
        operators.add(mulBtn);
        operators.add(divButton);


        for (ToggleButton b : gameBtns) {
            b.setOnTouchListener(btn_animation);
            b.setOnClickListener(this);
            b.setEnabled(false);
            TiltEffectAttacher.attach(b);
        }
        for (ToggleButton b : operators) {
            b.setOnTouchListener(btn_animation);
            b.setOnClickListener(this);
        }

        gameInit();


    }

    private void gameInit() {
        for (ToggleButton tB : gameBtns) {
            tB.setVisibility(View.VISIBLE);
            tB.setEnabled(true);
            tB.setChecked(false);
        }
        for (ToggleButton b : operators) {
            b.setChecked(false);
        }
        isNumberSelected = false;
        isOperatorSelected = false;


        if (mGameType.equals(StageGameMode.TYPE)) {
            int min = 0, max = 0;
            int currStage = DataStore.getInstance(this).getCurrentStage();
            stageInfosArray = DataStore.getInstance(this).getStageInfo();

            if (stageInfosArray.size() < mLevelNum || currStage < mLevelNum) {
                if (currStage < 10) {
                    min = 0;
                    max = 20;
                    mAbstractGame.setDifficulty(6);
                } else if (currStage < 20) {
                    min = 20;
                    max = 40;
                    mAbstractGame.setDifficulty(8);
                } else if (currStage < 30) {
                    min = 40;
                    max = 60;
                    mAbstractGame.setDifficulty(9);
                } else if (currStage < 40) {
                    min = 60;
                    max = 90;
                    mAbstractGame.setDifficulty(10);
                } else if (currStage < 100) {
                    min = 80;
                    max = 120;
                    mAbstractGame.setDifficulty(12);
                }

                do {
                    theDesiredNumber = mAbstractGame.gameGenerator(gameBtns, min, max);
                } while (theDesiredNumber > max || theDesiredNumber < min);

                mTheDesiredNumberTv.setText(String.valueOf(theDesiredNumber));


                StageInfo stageInfo = new StageInfo(Integer.parseInt(gameBtns.get(0).getText().toString()), Integer.parseInt(gameBtns.get(1).getText().toString()),
                        Integer.parseInt(gameBtns.get(2).getText().toString()), Integer.parseInt(gameBtns.get(3).getText().toString()), theDesiredNumber, mAbstractGame.getHint());
                DataStore.getInstance(this).saveStageInfo(stageInfo);
                mHint = mAbstractGame.getHint();
            } else {
                startSavedGameInfo(gameBtns, stageInfosArray, mLevelNum);
            }
        } else if (mGameType.equals(ArcadeGameMode.TYPE)) {
            int min=0,max=100;
            if (winsCounter < 3) {
                min = 0;
                max = 20;
                mAbstractGame.setDifficulty(6);
            } else if (winsCounter < 8) {
                min = 20;
                max = 40;
                mAbstractGame.setDifficulty(8);
            } else if (winsCounter < 12) {
                min = 40;
                max = 60;
                mAbstractGame.setDifficulty(9);
            } else if (winsCounter < 30) {
                min = 60;
                max = 90;
                mAbstractGame.setDifficulty(10);
            } else if (winsCounter < 100) {
                min = 80;
                max = 120;
                mAbstractGame.setDifficulty(12);
            }
//            mAbstractGame.setDifficulty(12);

            do {
                theDesiredNumber = mAbstractGame.gameGenerator(gameBtns, min, max);
            } while (theDesiredNumber > 100 || theDesiredNumber < 0);

            mTheDesiredNumberTv.setText(String.valueOf(theDesiredNumber));
            mHint = mAbstractGame.getHint();
        }
    }

    private void startSavedGameInfo(List<ToggleButton> gameBtns, ArrayList<StageInfo> stageInfosArray, int levelNum) {
        Collections.shuffle(gameBtns);

        gameBtns.get(0).setTextOff(String.valueOf(stageInfosArray.get(levelNum - 1).getNum1()));
        gameBtns.get(0).setTextOn(String.valueOf(stageInfosArray.get(levelNum - 1).getNum1()));
        gameBtns.get(0).setText(String.valueOf(stageInfosArray.get(levelNum - 1).getNum1()));

        gameBtns.get(1).setTextOff(String.valueOf(stageInfosArray.get(levelNum - 1).getNum2()));
        gameBtns.get(1).setTextOn(String.valueOf(stageInfosArray.get(levelNum - 1).getNum2()));
        gameBtns.get(1).setText(String.valueOf(stageInfosArray.get(levelNum - 1).getNum2()));

        gameBtns.get(2).setTextOff(String.valueOf(stageInfosArray.get(levelNum - 1).getNum3()));
        gameBtns.get(2).setTextOn(String.valueOf(stageInfosArray.get(levelNum - 1).getNum3()));
        gameBtns.get(2).setText(String.valueOf(stageInfosArray.get(levelNum - 1).getNum3()));

        gameBtns.get(3).setTextOff(String.valueOf(stageInfosArray.get(levelNum - 1).getNum4()));
        gameBtns.get(3).setTextOn(String.valueOf(stageInfosArray.get(levelNum - 1).getNum4()));
        gameBtns.get(3).setText(String.valueOf(stageInfosArray.get(levelNum - 1).getNum4()));

        theDesiredNumber = stageInfosArray.get(levelNum - 1).getTarget();
        mTheDesiredNumberTv.setText(String.valueOf(theDesiredNumber));
        mHint = stageInfosArray.get(levelNum - 1).getHint();
    }

    private void createGameModel(final String gameType) {
        mAbstractGame = GameFactory.getGame(gameType, 12);
    }
    

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(timeLeftInMillSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillSeconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

                Bundle bundle = new Bundle();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                bundle.putInt(SCORE_COUNTER, scoreCounter);
                EndOfArcadeGameFragment endOfArcadeGameFragment = new EndOfArcadeGameFragment();
                endOfArcadeGameFragment.setArguments(bundle);

                transaction.add(R.id.game_root_container, endOfArcadeGameFragment);
                transaction.commit();

            }
        }.start();
        timerRunning = true;
    }

    private void updateTimer() {
        int mins = (int) timeLeftInMillSeconds / 60000;
        int secs = (int) timeLeftInMillSeconds % 60000 / 1000;
        String timeLeftText = "";
        if (mins < 10) timeLeftText += "0";
        timeLeftText += "" + mins + ":";
        if (secs < 10) timeLeftText += "0";
        timeLeftText += secs;
        mCountDownTv.setText(timeLeftText);
    }

    private void init_toasty() {
        Toasty.Config.getInstance().tintIcon(false).setTextSize(30).allowQueue(true).apply();
    }

    public static void startGameActivity(Context context, String gameType, int levelNum) {

        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(EXTRA_GAME_TYPE, gameType);
        intent.putExtra(EXTRA_LEVEL_NUMBER, levelNum);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
        context.startActivity(intent, compat.toBundle());


    }


    @Override
    public void onClick(final View v) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                MediaPlayer btn_off, btn_On;
                btn_off = MediaPlayer.create(GameActivity.this, R.raw.btn_off_sound);
                btn_On = MediaPlayer.create(GameActivity.this, R.raw.btn_on_sound);
                btn_On.setVolume(sound_Effects_Volume, sound_Effects_Volume);
                btn_off.setVolume(sound_Effects_Volume, sound_Effects_Volume);

                if (!((ToggleButton) v).isChecked()) {
                    btn_On.start();
                } else if (((ToggleButton) v).isChecked()) {
                    btn_off.start();
                }
            }
        });
        /// checks that nobody checked
        int i = 0;
        for (ToggleButton toggleButton : gameBtns) {

            if (toggleButton.isChecked())
                break;
            i++;
        }
        if (i == 4) {
            num1 = Integer.MAX_VALUE;
            isNumberSelected = false;
            selectedNumberId_1 = 0;
        }
        //checks that no operator checked
        i = 0;
        for (ToggleButton toggleButton : operators) {

            if (toggleButton.isChecked())
                break;
            i++;
        }
        if (i == 4) {
            operator = "";
            isOperatorSelected = false;
            selectedOperatorId = 0;
        }
//        Toast.makeText(this, "" + num1, Toast.LENGTH_SHORT).show();
        if (num2 != Integer.MAX_VALUE) {
            int sum = 0;
            boolean isDivideZero = false;
            boolean isFraction = false;
            switch (operator) {
                case "plus":
                    sum = num1 + num2;
                    break;
                case "minus":
                    sum = num1 - num2;
                    break;
                case "div":
                    if (num2 == 0) {
                        isDivideZero = true;
                    } else if (num1 % num2 != 0) {
                        isFraction = true;

                    } else
                        sum = num1 / num2;
                    break;
                case "mul":
                    sum = num1 * num2;
                    break;
            }
            //set new button
            ToggleButton toggleButton = findViewById(selectedNumberId_2);
            toggleButton.startAnimation(scale_out);
            toggleButton.setTextOn(String.valueOf(sum));
            toggleButton.setTextOff(String.valueOf(sum));
            toggleButton.setText(String.valueOf(sum));
            toggleButton.setChecked(false);
            //toggleButton.callOnClick();

            //button to remove+anim
            ToggleButton toggleButtonToHide = findViewById(selectedNumberId_1);
            toggleButtonToHide.startAnimation(scale_out);
            toggleButtonToHide.setVisibility(View.INVISIBLE);
            toggleButtonToHide.setEnabled(false);


            toggleButton.startAnimation(scale_in);

            ToggleButton operator = findViewById(selectedOperatorId);
            operator.setChecked(false);

            //reset flags
            isOperatorSelected = false;
            isNumberSelected = false;
            num2 = Integer.MAX_VALUE;


            i = 0;
            for (ToggleButton tb : gameBtns) {

                if (tb.isEnabled())
                    i++;

            }

            final Dialog winLooseDialog = new Dialog(this);
            winLooseDialog.setCanceledOnTouchOutside(false);
            final View dialogView = getLayoutInflater().inflate(R.layout.win_loose_dialog, null);
            winLooseDialog.setContentView(dialogView);


            ImageView owlIv = dialogView.findViewById(R.id.image_owl);
            final TextView msgTv = dialogView.findViewById(R.id.text_msg);
            ImageButton tryAgainIb = dialogView.findViewById(R.id.try_again_ib);
            ImageButton homeIb = dialogView.findViewById(R.id.home_ib);
            final ImageButton nextIb = dialogView.findViewById(R.id.next_ib);
            final Space space = dialogView.findViewById(R.id.spacer);


            nextIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCountDownTv.setText("Level: " + String.valueOf(++mLevelNum));
                    gameInit();
                    winLooseDialog.dismiss();

                    for (ToggleButton tb : gameBtns) {
                        tb.startAnimation(bounce_shake);
                    }
                }
            });


            tryAgainIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameInit();
                    winLooseDialog.dismiss();

//                    mTheDesiredNumberTv.startAnimation(bounce_shake);
                    for (ToggleButton tb : gameBtns) {
                        tb.startAnimation(bounce_shake);
                    }
                }
            });


            homeIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartScreenActivity.startStartScreenActivity(GameActivity.this);
                    winLooseDialog.dismiss();
                    finish();
                }
            });


            if (isDivideZero || isFraction) {
                //false division
                if (mGameType.equals(ArcadeGameMode.TYPE)) {
//                    Toasty.warning(this, getResources().getString(R.string.division), Toast.LENGTH_SHORT).show();
                    taDaplayer = MediaPlayer.create(GameActivity.this, R.raw.buzzer_sound);
                    taDaplayer.setVolume(sound_Effects_Volume,sound_Effects_Volume);
                    taDaplayer.start();
                }

                if (mGameType.equals(StageGameMode.TYPE)) {
                    owlIv.setImageResource(R.drawable.loose_owl);
                    msgTv.setText("Level: "+String.valueOf(mLevelNum)+"\nInvalid division no fractions");
                    if (isDivideZero)
                        msgTv.setText("Level: "+String.valueOf(mLevelNum)+"\nInvalid division by 0");
                    winLooseDialog.setContentView(dialogView);
                    nextIb.setVisibility(View.GONE);
                    space.setVisibility(View.VISIBLE);
                    winLooseDialog.show();

                    taDaplayer = MediaPlayer.create(GameActivity.this, R.raw.waa_waa_waaaa);
                    taDaplayer.setVolume(sound_Effects_Volume,sound_Effects_Volume);
                    taDaplayer.start();
                }

                gameInit();
            }


            if (i == 1) {
                //game finished

                //you win
                if (theDesiredNumber == sum) {

                    if (mGameType.equals(ArcadeGameMode.TYPE)) {



                        taDaplayer = MediaPlayer.create(GameActivity.this, R.raw.arcade_win);
                        taDaplayer.setVolume(sound_Effects_Volume,sound_Effects_Volume);
                        taDaplayer.start();


                        RelativeLayout relativeLayout = findViewById(R.id.game_root_container);
                        CommonConfetti.rainingConfetti(relativeLayout, new int[]{Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.CYAN,
                                Color.RED, Color.BLUE})
                                .oneShot();

//                        Toasty.success(this, getResources().getString(R.string.correct_answer), Toast.LENGTH_SHORT).show();
                        gameInit();
                        scoreCounter = scoreCounter + 100;
                        winsCounter = winsCounter + 1;
                        if (winsCounter >= 3)
                            scoreCounter = scoreCounter + 100;
                        winsCounter = winsCounter + 1;
                        if (winsCounter >= 3)
                            scoreCounter = scoreCounter + 100;
                        if (winsCounter >= 7)
                            scoreCounter = scoreCounter + 200;
                        if (winsCounter >= 10) {
                            scoreCounter = scoreCounter + 300;
                        }

                        mActualScoreTv.setText(scoreCounter + "");

                    } else if (mGameType.equals(StageGameMode.TYPE)) {
                        int currentStage = DataStore.getInstance(this).getCurrentStage();
                        if (mLevelNum == currentStage) {
                            currentStage++;
                        }

                        DataStore.getInstance(this).saveCurrentStage(currentStage);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                msgTv.setText("Level: "+String.valueOf(mLevelNum)+"\nCongrats You Did It Right!!!");
                                winLooseDialog.setContentView(dialogView);
                                winLooseDialog.show();


                                taDaplayer = MediaPlayer.create(GameActivity.this, R.raw.ta_da);
                                taDaplayer.setVolume(sound_Effects_Volume,sound_Effects_Volume);
                                taDaplayer.start();


                                RelativeLayout relativeLayout = findViewById(R.id.game_root_container);
                                CommonConfetti.rainingConfetti(relativeLayout, new int[]{Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.CYAN,
                                        Color.RED, Color.BLUE})
                                        .stream(2500);


                            }
                        }, 200);

                        mActualScoreTv.setText(scoreCounter + "");

                    }

                } else {
                    //you loose

                    if (mGameType.equals(ArcadeGameMode.TYPE)) {
//                        stopTimer();
                        gameInit();
//                        startTimer();
                        taDaplayer = MediaPlayer.create(GameActivity.this, R.raw.buzzer_sound);
                        taDaplayer.setVolume(sound_Effects_Volume,sound_Effects_Volume);
                        taDaplayer.start();
//                        Toasty.error(this, getResources().getString(R.string.wrong_answer), Toast.LENGTH_SHORT).show();
                    } else if (mGameType.equals(StageGameMode.TYPE)) {

                        owlIv.setImageResource(R.drawable.loose_owl);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                msgTv.setText("Level: "+String.valueOf(mLevelNum)+"\nYou Are Worng");
                                taDaplayer = MediaPlayer.create(GameActivity.this, R.raw.waa_waa_waaaa);
                                taDaplayer.setVolume(sound_Effects_Volume,sound_Effects_Volume);
                                taDaplayer.start();
                                winLooseDialog.setContentView(dialogView);
                                nextIb.setVisibility(View.GONE);
                                space.setVisibility(View.VISIBLE);
                                winLooseDialog.show();
                            }
                        }, 200);


                        gameInit();

                    }
                }
            }

        }
    }


    @Override
    public void onArcadeGameEndAndPlayAgain(String nickName) {

        DataStore.getInstance(GameActivity.this).saveNameAndScore(nickName, scoreCounter);
        GameActivity.startGameActivity(GameActivity.this, ArcadeGameMode.TYPE, 0);
        finish();
    }

    @Override
    public void onArcadeGameEndAndGoToHome(String nickName) {
        DataStore.getInstance(GameActivity.this).saveNameAndScore(nickName, scoreCounter);
        Intent intent = new Intent(GameActivity.this, StartScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onArcadeGameEndAndGoToScoreboard(String nickName) {

        DataStore.getInstance(GameActivity.this).saveNameAndScore(nickName, scoreCounter);
        Intent intent = new Intent(GameActivity.this, ScoreBoardActivity.class);
        startActivity(intent);
        finish();
    }

    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }
}

