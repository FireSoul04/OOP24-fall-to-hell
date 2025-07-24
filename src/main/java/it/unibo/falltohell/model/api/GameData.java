package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Set of data to maintain the current state of the game.
 *
 * @author Martina Malagoli
 */
public interface GameData {

    /**
     * @param amount of points to be added
     */
    void addPoints(long amount);

    /**
     * @param amount of point to be removed
     */
    void removePoints(long amount);

    /**
     * @return the player current points
     */
    long getPoints();

    /**
     * @param newCharacter to be changed into
     */
    void changeCurrentCharacter(Character newCharacter);

    /**
     * @return the current character
     */
    Character getCurrentCharacter();
}
