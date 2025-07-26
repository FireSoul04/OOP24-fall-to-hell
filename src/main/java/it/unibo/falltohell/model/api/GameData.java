package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.util.Vector2;

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

    /**
     * @return the last saved position of the character
     */
    Vector2 getLastSavedPosition();
}
