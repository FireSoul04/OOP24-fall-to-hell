package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Game;

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
        return false;
    }

    public boolean isRunning() {
        return false;
    }

    public void update(final double deltaTime) {

    }

    public void render() {

    }
}
