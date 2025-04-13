package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.impl.GameImpl;
import it.unibo.falltohell.view.api.GameWindow;
import it.unibo.falltohell.view.impl.GameWindowImpl;

public class GameControllerImpl implements GameController {

    private static final double MAX_UPDATES = 60.0;
    
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
    }

    @Override
    public void run() {
        final double ns = 1.0E9 / MAX_UPDATES;
        double deltaTime = 0.0;
        int updates = 0;
        int frames = 0;
        long lastTime = System.nanoTime();
        long frameRateStartTime = System.nanoTime();
        while (!this.isOver()) {
            try {
                Thread.sleep(frames - (long)(frames - 100 / 60));
            } catch (InterruptedException e) {}
            final long now = System.nanoTime();
            deltaTime = deltaTime + ((now - lastTime) / ns);
            lastTime = now;
            while (deltaTime >= 1.0) {
                this.update(deltaTime);
                updates++;
                deltaTime--;
            }
            this.render();
            frames++;
            final long frameRateEndTime = System.nanoTime() - frameRateStartTime;
            if (frameRateEndTime > 1.0) {
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
                frameRateStartTime = System.nanoTime();
            }
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

    }

    @Override
    public void render() {

    }
}
