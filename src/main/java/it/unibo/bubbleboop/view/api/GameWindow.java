package it.unibo.bubbleboop.view;

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
