package it.unibo.falltohell.model.api;

/**
 * Interface for the game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public interface Game {

    /**
     * Initialize the main variables for the game.
     */
    void init();

    /**
     * @return current level playing
     */
    Level getLevel();
}
