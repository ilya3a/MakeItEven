package com.yoyo.makeiteven;

public class GameFactory {
    public static AbstractGame getGame(String gameType, int difficulty) {

        switch (gameType) {
            case ArcadeGame.TYPE:
                return new AbstractGame(difficulty) {
                };
            case StageGame.TYPE:
                return new StageGame(difficulty);
            default:
                return new StageGame(difficulty);
        }
    }
}
