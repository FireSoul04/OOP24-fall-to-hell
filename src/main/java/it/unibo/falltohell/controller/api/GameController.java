package it.unibo.falltohell.controller.api;

/**
 * Interface for a MVC controller dedicated to a game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public interface GameController {
    
    /**
     * Game loop, runs at MAX_UPDATES per seconds and handles the rendering and lets the game work on multiple platforms at the same speed.
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
     * Update all the game objects inside the current level/scene.
     * @param deltaTime time passed between the last frame and the current frame
     */
    void update(double deltaTime);

    /**
     * Tell the controller to update the game window (view).
     */
    void render();
}
