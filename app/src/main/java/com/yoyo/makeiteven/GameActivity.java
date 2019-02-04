package com.yoyo.makeiteven;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Explode;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;


public class GameActivity extends Activity implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private long timeLefetInMillsecons = 300000;//5:00 mints
    private boolean timerRunning;
    TextView coutDownText;
    TextView score;
    AbstractGame mAbstractGame;

    List<ToggleButton> gameBtns;
    List<ToggleButton> operators;
    Button startBtn;
    boolean isOperatorSelected = false, isNumberSelected = false;
    int selectedOperatorId, selectedNumberId_1, selectedNumberId_2;
    int num1 = Integer.MAX_VALUE, num2 = Integer.MAX_VALUE;
    String operator = "";
    int theDesiredNumber = 0;
    Animation scale_out;
    Animation scale_in;

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

    String mGameType;
    static final String EXTRA_GAME_TYPE = "extra_game_type";
    static final String EXTRA_USER_NAME = "extra_user_nickname";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (Build.VERSION.SDK_INT >= 21) {
            Explode explode = new Explode();
            explode.setDuration( 1000 );
            getWindow().setEnterTransition( explode );
        }

        setContentView( R.layout.activity_game );

        setContentView(R.layout.activity_game);
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


        SingleSelectToggleGroup numberGroup = findViewById(R.id.group_choices_of_numbers);
        numberGroup.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {


        Bundle bundle = getIntent().getExtras();
        mGameType = bundle.getString( EXTRA_GAME_TYPE );
        final String type = bundle.getString( EXTRA_GAME_TYPE );
        final String userName = bundle.getString( EXTRA_USER_NAME );



        final TextView theNumber = findViewById( R.id.the_number );
        score = findViewById( R.id.score_tv );
        coutDownText = findViewById( R.id.timer_txt );
        final ToggleButton btn1 = findViewById( R.id.btn1 );
        final ToggleButton btn2 = findViewById( R.id.btn2 );
        ToggleButton btn3 = findViewById( R.id.btn3 );
        ToggleButton btn4 = findViewById( R.id.btn4 );
        Button startBtn = findViewById( R.id.start_btn );
        Button plusBtn = findViewById( R.id.plus );
        Button minusBtn = findViewById( R.id.minus );
        Button mulBtn = findViewById( R.id.mul );
        Button divButton = findViewById( R.id.div );

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



        final List<ToggleButton> gameBtns = new ArrayList<>();
        gameBtns.add( btn1 );
        gameBtns.add( btn2 );
        gameBtns.add( btn3 );
        gameBtns.add( btn4 );

        final List<Button> operators = new ArrayList<>();
        operators.add( plusBtn );
        operators.add( minusBtn );
        operators.add( mulBtn );
        operators.add( divButton );


        final Animation scale_out = AnimationUtils.loadAnimation( this, R.anim.scale_out );
        final Animation scale_in = AnimationUtils.loadAnimation( this, R.anim.scale_in );
        final Animation btn_press = AnimationUtils.loadAnimation( this, R.anim.btn_pressed );
        final Animation btn_release = AnimationUtils.loadAnimation( this, R.anim.btn_realeas );

        View.OnTouchListener btn_animation = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation( btn_press );
                    btn_press.setFillAfter( true );
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation( btn_release );


                }
                return false;
            }
        };
        for (ToggleButton b : gameBtns) {

            b.setOnTouchListener(btn_animation);
            b.setOnClickListener(this);
            b.setEnabled(false);
            TiltEffectAttacher.attach(b);

        }
        for (Button b : operators) {
            b.setOnTouchListener( btn_animation );
           
            b.setOnClickListener( this );
        }

        final Game game = new Game( 12 );

        startBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ToggleButton tB : gameBtns)
                    tB.setChecked( false );
                startStop();

                theNumber.setText( String.valueOf( game.gameGenerator( gameBtns, 0, 100 ) ) + "  " + game.getHint() );
                theDesiredNumber = game.gameGenerator(gameBtns, 0, 100);
                theDesiredNumberTV.setText(String.valueOf(theDesiredNumber) + "  " + game.getHint());
                startBtn.setEnabled(false);


            }
        } );

    }

    private void createGameModel(final String gameType, final int difficulty) {

        mAbstractGame = GameFactory.getGame( gameType, difficulty );

//        final GameEventPresenter gameEventPresenter = new GameEventPresenter() {}
    }

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
        countDownTimer = new CountDownTimer( timeLefetInMillsecons, 1000 ) {
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
        coutDownText.setText( timeLeftText );
    }


    @Override
    public void onClick(View v) {
//        ((ToggleButton)v).setTextOff(((ToggleButton)v).getText());
//        ((ToggleButton)v).setTextOn(((ToggleButton)v).getText());
    }
}



