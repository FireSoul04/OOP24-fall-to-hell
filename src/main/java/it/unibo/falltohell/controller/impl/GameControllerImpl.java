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
import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
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
@SuppressFBWarnings(
    value = "AT_UNSAFE_RESOURCE_ACCESS_IN_THREAD",
    justification = "The communication between model and view is handled safely"
)
public final class GameControllerImpl implements GameController {

    private static final int WIDTH = 320;
    private static final int HEIGHT = WIDTH * 9 / 16;

    private static final String MUSIC = "Music";
    private static final long GAME_OVER_LABEL_DURATION = ONE_SECOND * 2;
    private static final Vector2 GAME_OVER_LABEL_OFFSET = new Vector2(20, 10);
    private static final Vector2 GAME_OVER_LABEL_POSITION =
        new Vector2(WIDTH, HEIGHT).divide(2).subtract(GAME_OVER_LABEL_OFFSET);

    private final Logger logger;

    private final GameWindow view;
    private final MainMenuPanel mainMenu;
    private final AudioControllerImpl audioController;
    private final GameEventManager<String> eventManager;
    private final DrawableRenderableHandler drh;
    private Level model;
    private TimerManager timerManager;
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
        this.drh = new DrawableRenderableHandlerImpl();
        this.eventManager = this.addEvents(inputListener);
        this.view = new GameWindowImpl(WIDTH, HEIGHT, inputListener.getKeyListener(), drh);
        this.logger = Logger.getLogger("GameLogger");
        this.audioController = new AudioControllerImpl();
        this.mainMenu = new MainMenuPanel(
            e -> this.goToGame(),
            e -> this.exit()
        );
        this.goToMainMenu();
    }

    private void goToGame() {
        this.view.showGame();
        this.view.requestFocusOnWindow();
        this.state = GameState.RUNNING;
        this.audioController.play(MUSIC);
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
        this.timerManager = this.model.getTimerManager();
        new Thread(this::run).start();
    }

    private void goToMainMenu() {
        this.view.showMenu(this.mainMenu);
        this.state = GameState.START;
        this.audioController.pause(MUSIC);
        this.drh.removeAllLinks();
    }

    private void gameOver() {
        if (!this.timerManager.searchTimer("game_over")) {
            final Label gameOverLabel = new Label("Game Over", GAME_OVER_LABEL_POSITION, true);
            this.drh.linkLabel(gameOverLabel);
            this.timerManager.addTimer(
                "game_over",
                new CustomTimerImpl(GAME_OVER_LABEL_DURATION, () -> this.state = GameState.START)
            );
        }
    }

    private void exit() {
        System.exit(0);
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
                this.audioController.pause(MUSIC);
            }
        });
        eventManager.addAction("ResumeGame", () -> {
            if (!this.isRunning()) {
                this.model.getTimerManager().resumeAllTimers();
                this.state = GameState.RUNNING;
                this.audioController.play(MUSIC);
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
        while (!this.isMenu()) {
            final long now = System.currentTimeMillis();
            // Gives a difference in time using milliseconds
            final double deltaTimeMilliseconds = now - lastTime;
            deltaTime = deltaTimeMilliseconds / PERIOD;
            if (!this.isOver()) {
                this.eventManager.update();
            }
            this.timerManager.updateAllTimers(deltaTimeMilliseconds);
            if (this.isRunning()) {
                this.update(deltaTime);
            } else if (this.isOver()) {
                this.gameOver();
            }
            this.render();
            this.waitForNextFrame(deltaTime);
            lastTime = now;
            frames++;
            if (System.currentTimeMillis() - frameRateStartTime >= ONE_SECOND) {
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
    public boolean isMenu() {
        return this.state == GameState.START;
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
}
