package org.example.wordlefx.managers;

import org.example.wordlefx.enums.GameState;

public class GameManager {

    private GameManager() {};

    public static GameState restartGame(GameState gameState) {
        gameState = GameState.PLAY;
        return gameState;
    }

    public static GameState quitGame(GameState gameState) {
        gameState = GameState.QUIT;
        return gameState;
    }
}
