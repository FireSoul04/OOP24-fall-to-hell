package it.unibo.bubbleboop.controller.impl;

import it.unibo.bubbleboop.model.Game;
import it.unibo.bubbleboop.model.impl.GameImpl;
import it.unibo.bubbleboop.view.GameWindow;
import it.unibo.bubbleboop.view.impl.GameWindowImpl;

public class GameControllerImpl {

    private final GameWindow view;
    private final Game model;

    public GameControllerImpl() {
        this.model = new GameImpl();
        this.view = new GameWindowImpl(240, 240);
    }

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
            frameRateEndTime = System.nanoTime() - frameRateStartTime;
            if (frameRateEndTime > 1.0) {
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
                frameRateStartTime = System.nanoTime();
            }
        }
    }
}
