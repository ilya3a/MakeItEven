package com.yoyo.makeiteven;

public class GameFactory {
    public static AbstractGame getGame(String gameType, int difficulty) {

        switch (gameType) {
            case ArcadeGameMode.TYPE:
                return new AbstractGame(difficulty) {
                };
            case StageGameMode.TYPE:
                return new StageGameMode(difficulty);
            default:
                return new StageGameMode(difficulty);
        }
    }
}
