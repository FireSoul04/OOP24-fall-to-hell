package it.unibo.falltohell.view.api;

/**
 * Swing implementation of the main window for the game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 * @author Daniele Mastroianni
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
}
