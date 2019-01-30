package com.yoyo.makeiteven;

import java.io.Serializable;


class Level_item {
    private int levelNum;
    public int y;

    public Level_item(int level) {
        levelNum = level;

    }

    public int getLevelNum() {
        return levelNum;
    }
}