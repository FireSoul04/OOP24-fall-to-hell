package it.unibo.bubbleboop.model;

public interface Game {

    /**
     * Initialize the main variables for the game.
     */
    void init();

    /**
     * @return true when the game is over, false otherwise
     */
    boolean isOver();

    /**
     * @return true when the game is running, false otherwise
     */
    boolean isRunning();

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
