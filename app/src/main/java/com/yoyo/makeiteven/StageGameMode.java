package com.yoyo.makeiteven;

import java.util.List;

public class StageGameMode extends AbstractGame {

    public static final String TYPE = "Stage_game";
    private List<Level> finishedLevels;
    boolean passedLevel = true;

    public StageGameMode(int difficulty) {
        super( difficulty );
    }

    public List<Level> getFinishedLevels() {
        return finishedLevels;
    }

    public void setFinishedLevels(List<Level> finishedLevels) {
        this.finishedLevels = finishedLevels;
    }
}
