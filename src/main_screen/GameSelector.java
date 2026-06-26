package main_screen;

import game_engine.GameState;

public class GameSelector {

    private GameState engine;

    public GameSelector(GameState engine) {
        this.engine = engine;
    }

    public void selectGame() {
        engine.onGameFinished();
    }
}