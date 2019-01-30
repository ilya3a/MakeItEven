package com.yoyo.makeiteven;

class Level_item {
    private int levelNum;
    private boolean finished;

    public Level_item(int levelNum, boolean finished) {
        this.levelNum = levelNum;
        this.finished = finished;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}