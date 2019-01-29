package com.yoyo.makeiteven;

import android.app.Activity;

import android.os.Build;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.transition.Explode;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.util.ArrayList;

import java.util.List;


public class GameActivity extends Activity implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private long timeLefetInMillsecons = 300000;//5:00 mints
    private boolean timerRunning;
    TextView coutDownText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
        }
        setContentView(R.layout.activity_game);

        final TextView theNumber = findViewById(R.id.the_number);
        coutDownText = findViewById(R.id.timer_txt);
        final ToggleButton btn1 = findViewById(R.id.btn1);
        final ToggleButton btn2 = findViewById(R.id.btn2);
        ToggleButton btn3 = findViewById(R.id.btn3);
        ToggleButton btn4 = findViewById(R.id.btn4);
        Button startBtn = findViewById(R.id.start_btn);
        Button plusBtn = findViewById(R.id.plus);
        Button minusBtn = findViewById(R.id.minus);
        Button mulBtn = findViewById(R.id.mul);
        Button divButton = findViewById(R.id.div);


        final List<ToggleButton> gameBtns = new ArrayList<>();
        gameBtns.add(btn1);
        gameBtns.add(btn2);
        gameBtns.add(btn3);
        gameBtns.add(btn4);

        final List<Button> operators = new ArrayList<>();
        operators.add(plusBtn);
        operators.add(minusBtn);
        operators.add(mulBtn);
        operators.add(divButton);


        final Animation scale_out = AnimationUtils.loadAnimation(this, R.anim.scale_out);
        final Animation scale_in = AnimationUtils.loadAnimation(this, R.anim.scale_in);
        final Animation btn_press = AnimationUtils.loadAnimation(this, R.anim.btn_pressed);
        final Animation btn_release = AnimationUtils.loadAnimation(this, R.anim.btn_realeas);

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
        for (ToggleButton b : gameBtns) {
            b.setOnTouchListener(btn_animation);
            b.setTag("num");
            b.setOnClickListener(this);
            TiltEffectAttacher.attach(b);
        }
        for (Button b : operators) {
            b.setOnTouchListener(btn_animation);
            b.setTag("op");
            b.setOnClickListener(this);
        }


        final Game game = new Game(12);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ToggleButton tB : gameBtns)
                    tB.setChecked(false);
                startStop();
                theNumber.setText(String.valueOf(game.gameGenerator(gameBtns,0,100)) + "  " + game.getHint());

            }
        });

    }


    private void startStop() {
        if (timerRunning) stopTimer();
        else startTimer();
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


    @Override
    public void onClick(View v) {
//        ((ToggleButton)v).setTextOff(((ToggleButton)v).getText());
//        ((ToggleButton)v).setTextOn(((ToggleButton)v).getText());
    }

}



