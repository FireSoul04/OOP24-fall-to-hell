package it.unibo.falltohell.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.GameEventManager;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.impl.builder.LevelBuilderImpl;
import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.view.api.GameWindow;
import it.unibo.falltohell.view.impl.GameWindowImpl;
import it.unibo.falltohell.view.impl.MainMenuPanel;

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

    private static final int WIDTH = 320;
    private static final int HEIGHT = WIDTH * 9 / 16;

    private final Logger logger;

    private final GameWindow view;
    private final MainMenuPanel mainMenu;
    private final AudioControllerImpl audioController;
    private final Level model;
    private final GameEventManager<String> eventManager;
    private final TimerManager timerManager;
    private GameState state;

    /**
     * Creates the controller with a new model and view, setting the state to start.
     */
    @SuppressFBWarnings(
        value = "DM_EXIT",
        justification = "If the exit button is pressed the application must be shut down"
    )
    public GameControllerImpl() {
        final InputListener inputListener = new InputListener();
        final DrawableRenderableHandler drh = new DrawableRenderableHandlerImpl();
        this.eventManager = this.addEvents(inputListener);
        // Testing a camera with level width and height based on the virtual screen width and height
        final GameCamera camera = new GameCameraImpl(Vector2.zero(), WIDTH, HEIGHT, 1.0);
        this.model = new LevelBuilderImpl(this)
            .attachGameEventManager(this.eventManager)
            .attachDrawableRenderableHandlerToLevel(drh)
            .attachCamera(camera)
            .createLevel()
            .loadCharacters()
            .loadGameData()
            .linkGameDataToLevel()
            .build();
        this.view = new GameWindowImpl(WIDTH, HEIGHT, inputListener.getKeyListener(), drh);
        this.timerManager = this.model.getTimerManager();
        this.state = GameState.START;
        this.logger = Logger.getLogger("GameLogger");
        this.audioController = new AudioControllerImpl();
        mainMenu = new MainMenuPanel(
            e -> {
                this.view.showGame();
                this.view.requestFocusOnWindow();
                this.state = GameState.RUNNING;
                this.audioController.play("Music");
                new Thread(this::run).start();
            },
            e -> {
                System.exit(0);
            });
        this.view.showMenu(mainMenu);
    }

    /**
     * Add events based on keyboard input.
     * @param inputListener to check for keyboard input
     * @return new event manager with events for the player based on keyboard input
     */
    private GameEventManager<String> addEvents(final InputListener inputListener) {
        final GameEventManager<String> eventManager = new GameEventManagerImpl<>();
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
            if (this.isRunning()) {
                this.model.getTimerManager().pauseAllTimers();
                this.state = GameState.PAUSE;
                this.audioController.pause("Music");
            }
        });
        eventManager.addAction("ResumeGame", () -> {
            if (!this.isRunning()) {
                this.model.getTimerManager().resumeAllTimers();
                this.state = GameState.RUNNING;
                this.audioController.play("Music");
            }
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
            // Gives a difference in time using milliseconds
            final double deltaTimeMilliseconds = now - lastTime;
            deltaTime = deltaTimeMilliseconds / PERIOD;
            this.eventManager.update();
            this.timerManager.updateAll(deltaTimeMilliseconds);
            if (this.isRunning()) {
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
        this.goToMainMenu();
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

    private void goToMainMenu() {
        this.view.showMenu(mainMenu);
        this.state = GameState.START;
        this.audioController.pause("Music");
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
    public void changeState(final GameState state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.model.update(deltaTime);
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
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "The GameWindow is part of the MVC view layer and is accessed only for rendering purposes"
    )
    @Override
    public GameWindow getView() {
        return this.view;
    }
}
