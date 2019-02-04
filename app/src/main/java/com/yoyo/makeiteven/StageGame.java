package com.yoyo.makeiteven;

import java.util.List;

public class StageGame extends AbstractGame {

    public static final String TYPE = "Stage_game";
    private List<Level_item> finishedLevels;

    public StageGame(int difficulty) {
        super( difficulty );
    }

    public List<Level_item> getFinishedLevels() {
        return finishedLevels;
    }

    public void setFinishedLevels(List<Level_item> finishedLevels) {
        this.finishedLevels = finishedLevels;
    }
}
