package com.yoyo.makeiteven;

public class ArcadeGame extends AbstractGame {

    public static final String TYPE = "Arcade_game";
    private String mName;
    private int mScore;

    public ArcadeGame(int difficulty) {
        super( difficulty );
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmScore() {
        return mScore;
    }

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }


}
