package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.impl.builder.GameBuilderImpl;
import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.view.api.GameWindow;
import it.unibo.falltohell.view.impl.AudioManager;
import it.unibo.falltohell.view.impl.GameWindowImpl;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;

/**
 * Main controller for the game.
 * It manages the flow of the game using a state machine and handles the communication between view and model.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public class GameControllerImpl implements GameController {

    /**
     * How many frames per seconds the game will run.
     */
    private static final double MAX_FRAMES = 60.0;

    /**
     * Frequency of every frame in milliseconds.
     */
    private static final double PERIOD = 1000 / MAX_FRAMES;
    private static final int WIDTH = 320;
    private static final int HEIGHT = WIDTH * 9 / 16;

    private final Logger logger;

    /**
     * State machine for the game.
     * It can represent running state, starting state and game over state.
     */
    private enum GameState {
        RUNNING,
        OVER,
        PAUSE
    }

    private final GameWindow view;
    private final Game model;
    private GameState state;

    /**
     * Creates the controller with a new model and view, setting the state to start.
     */
    public GameControllerImpl() {
        final InputListener inputListener = new InputListener();
        final DrawableRenderableHandler drh = new DrawableRenderableHandlerImpl();
        final GameEventManagerImpl<String> eventManager = this.addEvents(inputListener);
        // Testing a camera with level width and height based on the virtual screen width and height
        final GameCamera camera = new GameCameraImpl(Vector2.zero(), WIDTH, HEIGHT, 1.0);
        this.model = new GameBuilderImpl()
            .attachGameEventManager(eventManager)
            .attachDrawableRenderableHandlerToLevel(drh)
            .attachCamera(camera)
            .createLevel()
            .loadCharacters()
            .loadGameData()
            .linkGameDataToLevel()
            .build();
        this.view = new GameWindowImpl(WIDTH, HEIGHT, inputListener.getKeyListener(), drh);
        this.state = GameState.RUNNING;
        this.logger = Logger.getLogger("GameLogger");
        AudioManager.getInstance().play("Music");
    }

    /**
     * Add events based on keyboard input.
     * @param inputListener to check for keyboard input
     * @return new event manager with events for the player based on keyboard input
     */
    private GameEventManagerImpl<String> addEvents(final InputListener inputListener) {
        final GameEventManagerImpl<String> eventManager = new GameEventManagerImpl<>();
        eventManager.addCondition(
            "MoveLeft",
            () -> inputListener.isKeyPressed(KeyEvent.VK_A) || inputListener.isKeyPressed(KeyEvent.VK_LEFT)
        );
        eventManager.addCondition(
            "MoveRight",
            () -> inputListener.isKeyPressed(KeyEvent.VK_D) || inputListener.isKeyPressed(KeyEvent.VK_RIGHT)
        );
        eventManager.addCondition(
            "MoveUp",
            () -> inputListener.isKeyPressed(KeyEvent.VK_W) || inputListener.isKeyPressed(KeyEvent.VK_UP)
        );
        eventManager.addCondition(
            "MoveDown",
            () -> inputListener.isKeyPressed(KeyEvent.VK_S) || inputListener.isKeyPressed(KeyEvent.VK_DOWN)
        );
        eventManager.addCondition("Interact", () -> inputListener.isKeyPressedOnce(KeyEvent.VK_F));
        eventManager.addCondition("Jump", () -> inputListener.isKeyPressed(KeyEvent.VK_SPACE));
        eventManager.addCondition("NormalAttack", () -> inputListener.isKeyPressedOnce(KeyEvent.VK_E));
        eventManager.addCondition("ActiveAbility", () -> inputListener.isKeyPressedOnce(KeyEvent.VK_SHIFT));
        eventManager.addCondition("SpecialAbility", () -> inputListener.isKeyPressedOnce(KeyEvent.VK_Q));
        eventManager.addCondition("SpecialAttack", () -> inputListener.isKeyPressed(KeyEvent.VK_C));
        eventManager.addCondition("PauseGame", () -> inputListener.isKeyPressedOnce(KeyEvent.VK_P));
        eventManager.addCondition("ResumeGame", () -> inputListener.isKeyPressedOnce(KeyEvent.VK_O));

        eventManager.addAction("PauseGame", () -> {
            this.model.getLevel().getTimerManager().pauseAllTimers();
            this.state = GameState.PAUSE;
        });
        eventManager.addAction("ResumeGame", () -> {
            this.model.getLevel().getTimerManager().resumeAllTimers();
            this.state = GameState.RUNNING;
        });

        return eventManager;
    }

    /**
     * Game loop with a capped MAX_FRAMES per second.
     * It uses the difference between the current frame and the past frame to calculate a factor, called deltaTime.
     * This factor is used to make the game run at the same speed regardless of the hardware.
     * To make this possible, every object moving in the game has to use deltaTime.
     */
    @Override
    public void run() {
        int frames = 0;
        long lastTime = System.currentTimeMillis();
        long frameRateStartTime = lastTime;
        double deltaTime;
        while (!this.isOver()) {
            final long now = System.currentTimeMillis();
            deltaTime = (now - lastTime) / PERIOD;
            this.model.getLevel().getGameEventManager().update();
            if (isRunning()) {
                this.update(deltaTime);
            }
            this.render();
            this.waitForNextFrame(deltaTime);
            lastTime = now;
            frames++;
            if (System.currentTimeMillis() - frameRateStartTime >= 1000) {
                this.view.setGameTitle("FTH: " + frames + " fps");
                frames = 0;
                frameRateStartTime = System.currentTimeMillis();
            }
        }
    }

    private void waitForNextFrame(final double deltaTime) {
        if (deltaTime < PERIOD) {
            try {
                Thread.sleep((long) (PERIOD - deltaTime));
            } catch (final InterruptedException e) {
                this.logger.warning("The wait for next frame in game loop interrupted: " + e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.state == GameState.OVER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning() {
        return this.state == GameState.RUNNING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.model.getLevel().update(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.view.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameWindow getView() {
        return this.view;
    }
}
