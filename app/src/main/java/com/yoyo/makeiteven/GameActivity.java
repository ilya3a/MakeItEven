package com.yoyo.makeiteven;

import android.app.ActionBar;
import android.app.Activity;

import android.os.Build;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.transition.Explode;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.view.animation.RotateAnimation;
import android.widget.Button;


import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import java.util.ArrayList;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class GameActivity extends Activity implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private long timeLefetInMillsecons = 300000;//5:00 mints
    private boolean timerRunning;
    TextView coutDownText;
    List<ToggleButton> gameBtns;
    List<ToggleButton> operators;
    ImageButton startBtn;
    boolean isOperatorSelected = false, isNumberSelected = false;
    int selectedOperatorId, selectedNumberId_1, selectedNumberId_2;
    int num1 = Integer.MAX_VALUE, num2 = Integer.MAX_VALUE;
    String operator = "";
    int theDesiredNumber = 0;
    Animation scale_out;
    Animation scale_in;
    ImageButton game_reset_btn,back_btn,hint_btn;

    @Override
    public void onClick(View v) {
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
        //checks that nooperator checked
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
        Toast.makeText(this, "" + num1, Toast.LENGTH_SHORT).show();
        if (num2 != Integer.MAX_VALUE) {
            int sum = 0;
            switch (operator) {
                case "plus":
                    sum = num1 + num2;
                    break;
                case "minus":
                    sum = num1 - num2;
                    break;
                case "div":
                    if (num2 == 0 || num1 % num2 != 0) {
                        Toast.makeText(this, "divide by 0 or not neutral division ", Toast.LENGTH_SHORT).show();
                        startStop();
                        startBtn.setEnabled(true);

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
            if (i == 1) {
                if (theDesiredNumber == sum) {
                    Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
                    startBtn.setEnabled(true);
                    stopTimer();
                    //you win
                } else {
                    Toast.makeText(this, "YOU LOSE", Toast.LENGTH_SHORT).show();
                    startBtn.setEnabled(true);
                    stopTimer();
                    //you loose
                }
            }

        }

//        String mGameType;
//        static final String EXTRA_GAME_TYPE = "extra_game_type";
//        static final String EXTRA_USER_NAME = "extra_user_nickname";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
//        score = findViewById(R.id.score_tv);
        final TextView theDesiredNumberTV = findViewById(R.id.the_number);
        coutDownText = findViewById(R.id.timer_txt);
        ToggleButton btn1 = findViewById(R.id.btn1);
        ToggleButton btn2 = findViewById(R.id.btn2);
        ToggleButton btn3 = findViewById(R.id.btn3);
        ToggleButton btn4 = findViewById(R.id.btn4);

        ToggleButton plusBtn = findViewById(R.id.plus);
        ToggleButton minusBtn = findViewById(R.id.minus);
        ToggleButton mulBtn = findViewById(R.id.mul);
        ToggleButton divButton = findViewById(R.id.div);
        startBtn = findViewById(R.id.start_btn);
        game_reset_btn=findViewById(R.id.restart_level);
        back_btn=findViewById(R.id.game_back_btn);
        hint_btn=findViewById(R.id.hint_btn);
        final Game game = new Game(12);
        init_toasty();

        View.OnTouchListener btn_animation = new View.OnTouchListener() {
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

        back_btn.setOnTouchListener(btn_animation);
        final Animation rotateAnimation = AnimationUtils.loadAnimation( this, R.anim.rotate_restart);
        game_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_reset_btn.startAnimation(rotateAnimation);
                startBtn.callOnClick();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        Bundle bundle = getIntent().getExtras();
//        mGameType = bundle.getString(EXTRA_GAME_TYPE);
//        final String type = bundle.getString(EXTRA_GAME_TYPE);
//        final String userName = bundle.getString(EXTRA_USER_NAME);

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
        for (Button b : operators) {
            b.setOnTouchListener(btn_animation);
            b.setOnClickListener(this);
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ToggleButton tB : gameBtns) {
                    tB.setVisibility(View.VISIBLE);
                    tB.setEnabled(true);
                    tB.setChecked(false);
                }
                startStop();
                theDesiredNumber = game.gameGenerator(gameBtns, 0, 100);
                theDesiredNumberTV.setText(String.valueOf(theDesiredNumber));
                startBtn.setEnabled(false);


            }
        });
        hint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(GameActivity.this,game.getHint() , Toast.LENGTH_SHORT, true).show();
            }
        });

    }
//        private void createGameModel ( final String gameType, final int difficulty){
//
//            mAbstractGame = GameFactory.getGame(gameType, difficulty);
//
////        final GameEventPresenter gameEventPresenter = new GameEventPresenter() {}
//        }

    private void startStop() {
        if (timerRunning) stopTimer();
        timeLefetInMillsecons = 300000;
        startTimer();
    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLefetInMillsecons, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLefetInMillsecons = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                //you loose
            }
        }.start();
        timerRunning = true;
    }

    private void updateTimer() {
        int mins = (int) timeLefetInMillsecons / 60000;
        int secs = (int) timeLefetInMillsecons % 60000 / 1000;
        String timeLeftText = "";
        if (mins < 10) timeLeftText += "0";
        timeLeftText += "" + mins + ":";
        if (secs < 10) timeLeftText += "0";
        timeLeftText += secs;
        coutDownText.setText(timeLeftText);
    }
    private void init_toasty(){
        Toasty.Config.getInstance().tintIcon(false).setTextSize(30).allowQueue(true).apply();
    }
}




