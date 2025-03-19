package it.unibo.bubbleboop.model.impl;

import it.unibo.bubbleboop.model.Game;

public class GameImpl implements Game {

    private static final double MAX_UPDATES = 60.0;
    
    private enum GameState {
        RUNNING,
        START,
        OVER //,
        // PAUSE
    }

    public GameImpl() {
        
    }

    public void init() {

    }

    public boolean isOver() {

    }

    public boolean isRunning() {

    }

    public void update(final double deltaTime) {

    }

    public void render() {

    }
}
