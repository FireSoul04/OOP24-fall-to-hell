package it.unibo.falltohell.controller.api;

/**
 * Interface for an MVC controller dedicated to a game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public interface GameController {

    /**
     * How many frames per seconds the game will run.
     */
    double MAX_FRAMES = 60.0;

    /**
     * One second in milliseconds for timing calculations.
     */
    long ONE_SECOND = 1000;

    /**
     * Frequency of every frame in milliseconds.
     */
    double PERIOD = ONE_SECOND / MAX_FRAMES;

    /**
     * State machine for the game.
     * It can represent running state, starting state and game over state.
     */
    enum GameState {
        /**
         * Main menu, the start of the application.
         */
        START,
        /**
         * The main game when it is started.
         */
        RUNNING,
        /**
         * When the player dies, go to main menu.
         */
        OVER,
        /**
         * Pause the game.
         */
        PAUSE
    }

    /**
     * Game loop, runs at MAX_UPDATES per seconds and handles the rendering and lets the game work on multiple
     * platforms at the same speed.
     */
    void run();

    /**
     * @return true when the game is over, false otherwise
     */
    boolean isOver();

    /**
     * @return true when the game is running, false otherwise
     */
    boolean isRunning();

    /**
     * @return true when the application is on main menu, false otherwise
     */
    boolean isMenu();

    /**
     * @param state to change
     */
    void changeState(GameState state);

    /**
     * Update all the game objects inside the current level/scene.
     * @param deltaTime time passed between the last frame and the current frame
     */
    void update(double deltaTime);

    /**
     * Tell the controller to update the game window (view).
     */
    void render();
}
