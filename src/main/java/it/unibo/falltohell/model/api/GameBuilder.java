package it.unibo.falltohell.model.api;

import java.awt.event.KeyListener;

/**
 * Builder for the game (model).
 *
 * @author Davide Mancini
 */
public interface GameBuilder {

    /**
     * Creates a level inside game.
     * @return this builder with the level
     */
    GameBuilder createLevel();

    /**
     * Loads from the save file the game data for the game.
     * @return this builder with the game data
     */
    GameBuilder loadGameData();

    /**
     * Attach the controller's listener of every key press to the game.
     * @param keyListener controller to key presses of the keyboard
     * @return this builder with a key listener
     */
    GameBuilder attachKeyListener(KeyListener keyListener);

    /**
     * Loads the characters into the game.
     * @return this builder with the characters
     */
    GameBuilder loadCharacters();

    /**
     * Links game data to the level for game objects that need a reference to it.
     * @return this builder with game data inside level
     */
    GameBuilder linkGameDataToLevel();

    /**
     * @return build the game
     */
    Game build();
}
