package com.yoyo.makeiteven;

import java.util.Comparator;

public class ScoreBoard implements Comparator<ScoreBoard> {
    private String mNickName;
    private int mFinalScore;


    public ScoreBoard(String nickName, int finalScore) {
        mNickName = nickName;
        mFinalScore = finalScore;

    }

    public String getmNickName() {
        return mNickName;
    }

    public int getmFinalScore() {
        return mFinalScore;
    }

    @Override
    public int compare(ScoreBoard o1, ScoreBoard o2) {
        return o2.getmFinalScore() - o1.getmFinalScore();
    }
}
