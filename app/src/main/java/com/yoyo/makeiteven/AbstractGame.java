package com.yoyo.makeiteven;

import android.media.audiofx.DynamicsProcessing;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class AbstractGame {

    private int difficulty;
    private String hint;
    private Random randNumber;



    public AbstractGame(int difficulty) {
        this.difficulty = difficulty;
        randNumber = new Random();
    }


    public String getHint() {
        return hint;
    }

    public int gameGenerator(List<ToggleButton> btns, int minSum, int maxSum) {

        int btn1, btn2, btn3, btn4, sum;
        int randIdx;
        hint = new String();
        btn1 = randNumber.nextInt( difficulty ) + 1;
        btn2 = randNumber.nextInt( difficulty ) + 1;
        btn3 = randNumber.nextInt( difficulty ) + 1;
        btn4 = randNumber.nextInt( difficulty ) + 1;

        randIdx = randNumber.nextInt( 4 );
        if (randIdx == 3) {

            if (btn1 % btn2 == 0) {
                sum = btn1 / btn2;
                hint = hint.concat( "(" + btn1 + "/" + btn2 + ")" );
            } else {
                sum = btn1 + btn2;
                hint = hint.concat( "(" + btn1 + "+" + btn2 + ")" );
            }


        } else if (randIdx == 2) {
            sum = btn1 * btn2;
            hint = hint.concat( "(" + btn1 + "*" + btn2 + ")" );
        } else if (randIdx == 1) {
            if (btn1 - btn2 > 0) {
                sum = btn1 - btn2;
                hint = hint.concat( "(" + btn1 + "-" + btn2 + ")" );
            } else {
                sum = btn1 + btn2;
                hint = hint.concat( "(" + btn1 + "+" + btn2 + ")" );
            }
        } else {
            sum = btn1 + btn2;
            hint = hint.concat( "(" + btn1 + "+" + btn2 + ")" );
        }

        randIdx = randNumber.nextInt( 4 );


        if (randIdx == 3) {
            if (sum % btn3 == 0) {
                sum = sum / btn3;
                hint = hint.concat( "/" + btn3 + ")" );
            } else {
                sum = sum + btn3;
                hint = hint.concat( "+" + btn3 + ")" );
            }


        } else if (randIdx == 2) {
            sum = sum * btn3;
            hint = hint.concat( "*" + btn3 + ")" );
        } else if (randIdx == 1) {
            if (sum - btn3 > 0) {
                sum = sum - btn3;
                hint = hint.concat( "-" + btn3 + ")" );
            } else {
                sum = sum + btn3;
                hint = hint.concat( "+" + btn3 + ")" );
            }

        } else {
            sum = sum + btn3;
            hint = hint.concat( "+" + btn3 + ")" );
        }
        randIdx = randNumber.nextInt( 4 );

        if (randIdx == 3) {
            if (sum % btn4 == 0) {
                sum = sum / btn4;
                hint = hint.concat( "/" + btn4 + ")" );
            } else {
                sum = sum + btn4;
                hint = hint.concat( "+" + btn4 + ")" );
            }


        } else if (randIdx == 2) {
            sum = sum * btn4;
            hint = hint.concat( "*" + btn4 + ")" );
        } else if (randIdx == 1) {
            if (sum - btn4 > 0) {
                sum = sum - btn4;
                hint = hint.concat( "-" + btn4 + ")" );
            } else {
                sum = sum + btn4;
                hint = hint.concat( "+" + btn4 + ")" );
            }
        } else {
            sum = sum + btn4;
            hint = hint.concat( "+" + btn4 + ")" );
        }

        Collections.shuffle( btns );
        btns.get( 0 ).setTextOff( String.valueOf( btn1 ) );
        btns.get( 1 ).setTextOff( String.valueOf( btn2 ) );
        btns.get( 2 ).setTextOff( String.valueOf( btn3 ) );
        btns.get( 3 ).setTextOff( String.valueOf( btn4 ) );

        btns.get( 0 ).setTextOn( String.valueOf( btn1 ) );
        btns.get( 1 ).setTextOn( String.valueOf( btn2 ) );
        btns.get( 2 ).setTextOn( String.valueOf( btn3 ) );
        btns.get( 3 ).setTextOn( String.valueOf( btn4 ) );

        btns.get( 0 ).setText( String.valueOf( btn1 ) );
        btns.get( 1 ).setText( String.valueOf( btn2 ) );
        btns.get( 2 ).setText( String.valueOf( btn3 ) );
        btns.get( 3 ).setText( String.valueOf( btn4 ) );

        if (sum > maxSum || sum < minSum)
            sum = gameGenerator( btns, minSum, maxSum );

        return sum;
    }

}
