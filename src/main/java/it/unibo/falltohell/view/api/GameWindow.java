package it.unibo.falltohell.view.api;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface of the main window for the game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public interface GameWindow {

    /**
     * Show all the images/sprites and background for the game.
     */
    void render();

    /**
     * Clear the screen.
     */
    void clear();

    /**
     * @return the width of the window
     */
    int getWidth();

    /**
     * @return the height of the window
     */
    int getHeight();

    /**
     * @return the dimensions of the window
     */
    Dimensions getDimensions();

    /**
     * @return percentage of current width and height
     */
    Vector2 getScale();
}
