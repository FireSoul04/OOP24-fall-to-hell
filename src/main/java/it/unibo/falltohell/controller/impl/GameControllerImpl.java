package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.impl.GameBuilderImpl;
import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.view.api.GameWindow;
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
    private static final int WIDTH = 320;
    private static final int HEIGHT = WIDTH * 9 / 16;

    private final Logger logger;

    /**
     * State machine for the game.
     * It can represent running state, starting state and game over state.
     */
    private enum GameState {
        RUNNING,
        START,
        OVER //,
        // PAUSE
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
        final GameEventManager<String> eventManager = this.addEvents(inputListener);
        // Testing a camera with level width and height based on the virtual screen width and height
        final GameCamera camera = new GameCameraImpl(Vector2.zero(), WIDTH, HEIGHT, 1.0, WIDTH * 2, HEIGHT * 2);
        this.model = new GameBuilderImpl()
            .attachGameEventManager(eventManager)
            .attachDrawableRenderableHandlerToLevel(drh)
            .attachCamera(camera)
            .createLevel()
            .loadGameData()
            .linkGameDataToLevel()
            .loadCharacters()
            .build();
        this.view = new GameWindowImpl(WIDTH, HEIGHT, inputListener.getKeyListener(), drh);
        this.state = GameState.START;
        this.logger = Logger.getLogger("GameLogger");
    }

    /**
     * Add events based on keyboard input.
     * @param inputListener to check for keyboard input
     * @return new event manager with events for the player based on keyboard input
     */
    private GameEventManager<String> addEvents(final InputListener inputListener) {
        final GameEventManager<String> eventManager = new GameEventManager<>();
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
            () -> inputListener.isKeyPressed(KeyEvent.VK_W) || inputListener.isKeyPressed(KeyEvent.VK_DOWN)
        );
        eventManager.addCondition(
            "ActiveAbility",
            () -> inputListener.isKeyPressedOnce(KeyEvent.VK_SHIFT)
        );
        eventManager.addCondition(
            "Reload",
            () -> inputListener.isKeyPressedOnce(KeyEvent.VK_R) || inputListener.isKeyPressedOnce(KeyEvent.VK_X)
        );
        eventManager.addCondition(
            "Interact",
            () -> inputListener.isKeyPressedOnce(KeyEvent.VK_E) || inputListener.isKeyPressedOnce(KeyEvent.VK_Z)
        );
        eventManager.addCondition("Jump", () -> inputListener.isKeyPressed(KeyEvent.VK_SPACE));
        eventManager.addCondition(
            "NormalAttack",
            () -> inputListener.isKeyPressed(KeyEvent.VK_F)
            );
        eventManager.addCondition(
            "SpecialAbility",
            () -> inputListener.isKeyPressed(KeyEvent.VK_Q)
            );
        eventManager.addCondition(
            "SpAttackLeft",
            () -> inputListener.isKeyPressed(KeyEvent.VK_A) && inputListener.isKeyPressed(KeyEvent.VK_C)
        );
        eventManager.addCondition(
            "SpAttackRight",
            () -> inputListener.isKeyPressed(KeyEvent.VK_D) && inputListener.isKeyPressed(KeyEvent.VK_C)
        );
        eventManager.addCondition(
            "SpAttackUp",
            () -> inputListener.isKeyPressed(KeyEvent.VK_W) && inputListener.isKeyPressed(KeyEvent.VK_C)
        );
        eventManager.addCondition(
            "SpAttackDown",
            () -> inputListener.isKeyPressed(KeyEvent.VK_W) && inputListener.isKeyPressed(KeyEvent.VK_C)
        );
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
        final double ns = 1.0E9 / MAX_FRAMES;
        final double sleepTime = 100 / MAX_FRAMES;
        double deltaTime = 0.0;
        int frames = 0;
        long lastTime = System.nanoTime();
        while (!this.isOver()) {
            try {
                Thread.sleep(frames - (long) (frames - sleepTime));
            } catch (final InterruptedException e) {
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
}
