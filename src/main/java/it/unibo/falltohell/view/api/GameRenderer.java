package it.unibo.falltohell.view.api;

/**
 * Interface of the renderer for the game.
 *
 * @author Davide Mancini
 */
public interface GameRenderer {

    /**
     * Show all the images/sprites and background for the game.
     */
    void render();

    /**
     * Clear the screen.
     */
    void clear();
}
