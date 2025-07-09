package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.impl.GameImpl;
import it.unibo.falltohell.view.api.GameWindow;
import it.unibo.falltohell.view.impl.GameWindowImpl;

import java.util.logging.Logger;

public class GameControllerImpl implements GameController {

    private static final double MAX_UPDATES = 60.0;

    private final Logger logger;
    
    private enum GameState {
        RUNNING,
        START,
        OVER //,
        // PAUSE
    }

    private final GameWindow view;
    private final Game model;
    private GameState state;

    public GameControllerImpl() {
        this.model = new GameImpl();
        this.view = new GameWindowImpl(240, 240);
        this.state = GameState.START;
        this.logger = Logger.getLogger("GameLogger");
    }

    @Override
    public void run() {
        final double ns = 1.0E9 / MAX_UPDATES;
        double deltaTime = 0.0;
        int frames = 0;
        long lastTime = System.nanoTime();
        while (!this.isOver()) {
            try {
                Thread.sleep(frames - (long)(frames - 100 / 60));
            } catch (InterruptedException e) {
                this.logger.severe("Sleep interrupted: " + e);
            }
            final long now = System.nanoTime();
            deltaTime = deltaTime + ((now - lastTime) / ns);
            lastTime = now;
            while (deltaTime >= 1.0) {
                this.update(deltaTime);
                deltaTime--;
            }
            this.render();
            frames++;
        }
    }

    @Override
    public boolean isOver() {
        return this.state == GameState.OVER;
    }

    @Override
    public boolean isRunning() {
        return this.state == GameState.RUNNING;
    }

    @Override
    public void update(final double deltaTime) {
        this.model.getLevel().update(deltaTime);
    }

    @Override
    public void render() {
        this.view.render();
    }
}
