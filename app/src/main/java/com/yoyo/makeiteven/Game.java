package com.yoyo.makeiteven;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.Timer;

public class Game {
    private int difficulty;
    private int score;
    private String hint;


    public Game(int difficulty) {
        this.difficulty = difficulty;
        this.score = 0;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getHint() {
        return hint;
    }

    public int gameGenerator(List<Button> btns) {

        int btn1, btn2, btn3, btn4, sum;
        Random randNumber = new Random();
        Time t = new Time(5);
        int randIdx;
        hint = new String();
//        btn1 = randNumber.nextInt(difficulty) + 1;
//        btn2 = randNumber.nextInt(difficulty) + 1;

//        if (randIdx == 3) {
//            while (btn1 % btn2 != 0) {
//                btn2 = randNumber.nextInt(difficulty) + 1;
//            }
//            sum = btn1 / btn2;
//
//        } else if (randIdx == 2) {
//            sum = btn1 * btn2;
//        } else if (randIdx == 1) {
//            while (btn1 - btn2 < 0) {
//                btn2 = randNumber.nextInt(difficulty) + 1;
//            }
//            sum = btn1 - btn2;
//        } else {
//            sum = btn1 + btn2;
//        }
//
//        btn3 = randNumber.nextInt(difficulty) + 1;
//        randIdx = randNumber.nextInt(4);
//        if (randIdx == 3) {
//            while (sum % btn3 != 0) {
//                btn3 = randNumber.nextInt(difficulty) + 1;
//            }
//            sum = sum / btn3;
//
//        } else if (randIdx == 2) {
//            sum = sum * btn3;
//        } else if (randIdx == 1) {
//            while (sum - btn3 < 0) {
//                btn3 = randNumber.nextInt(difficulty) + 1;
//            }
//            sum = sum - btn3;
//        } else {
//            sum = sum + btn3;
//        }
//
//        btn4 = randNumber.nextInt(difficulty) + 1;
//        randIdx = randNumber.nextInt(3);
//        if (randIdx == 3) {
//            while (sum % btn4 != 0) {
//                btn4 = randNumber.nextInt(difficulty) + 1;
//            }
//            sum = sum / btn4;
//
//        } else if (randIdx == 2) {
//            sum = sum * btn4;
//        } else if (randIdx == 1) {
//            while (sum - btn4 < 0) {
//                btn4 = randNumber.nextInt(difficulty) + 1;
//            }
//            sum = sum - btn4;
//        } else {
//            sum = sum + btn4;
//        }
//
//
//

        btn1 = randNumber.nextInt(difficulty) + 1;
        btn2 = randNumber.nextInt(difficulty) + 1;
        btn3 = randNumber.nextInt(difficulty) + 1;
        btn4 = randNumber.nextInt(difficulty) + 1;

        randIdx = randNumber.nextInt(3);
        if (randIdx == 3) {

            if (btn1 % btn2 == 0) {
                sum = btn1 / btn2;
                hint = hint.concat(btn1 + "/" + btn2);
            } else {
                sum = btn1 + btn2;
                hint = hint.concat(btn1 + "+" + btn2);
            }


        } else if (randIdx == 2) {
            sum = btn1 * btn2;
            hint = hint.concat(btn1 + "*" + btn2);
        } else if (randIdx == 1) {
            if (btn1 - btn2 > 0) {
                sum = btn1 - btn2;
                hint = hint.concat(btn1 + "-" + btn2);
            } else {
                sum = btn1 + btn2;
                hint = hint.concat(btn1 + "+" + btn2);
            }
        } else {
            sum = btn1 + btn2;
            hint = hint.concat(btn1 + "+" + btn2);
        }

        randIdx = randNumber.nextInt(3);


        if (randIdx == 3) {
            if (sum % btn3 == 0) {
                sum = sum / btn3;
                hint = hint.concat("/" + btn3);
            } else {
                sum = sum + btn3;
                hint = hint.concat("+" + btn3);
            }


        } else if (randIdx == 2) {
            sum = sum * btn3;
            hint = hint.concat("*" + btn3);
        } else if (randIdx == 1) {
            if (sum - btn3 > 0) {
                sum = sum - btn3;
                hint = hint.concat("-" + btn3);
            } else {
                sum = sum + btn3;
                hint = hint.concat("+" + btn3);
            }

        } else {
            sum = sum + btn3;
            hint = hint.concat("+" + btn3);
        }
        randIdx = randNumber.nextInt(3);

        if (randIdx == 3) {
            if (sum % btn4 == 0) {
                sum = sum / btn4;
                hint = hint.concat("/" + btn4);
            } else {
                sum = sum + btn4;
                hint = hint.concat("+" + btn4);
            }


        } else if (randIdx == 2) {
            sum = sum * btn4;
            hint = hint.concat("*" + btn4);
        } else if (randIdx == 1) {
            if (sum - btn4 > 0) {
                sum = sum - btn4;
                hint = hint.concat("-" + btn4);
            } else {
                sum = sum + btn4;
                hint = hint.concat("+" + btn4);
            }
        } else {
            sum = sum + btn4;
            hint = hint.concat("+" + btn4);
        }

        Collections.shuffle(btns);
        btns.get(0).setText(String.valueOf(btn1));
        btns.get(1).setText(String.valueOf(btn2));
        btns.get(2).setText(String.valueOf(btn3));
        btns.get(3).setText(String.valueOf(btn4));

        if (sum > 100)
            sum = gameGenerator(btns);


        return sum;


    }
}
