package it.unibo.falltohell.model.api.gameobjects.interactable;

import it.unibo.falltohell.model.api.gameobjects.Interactable;

/**
 * Game object that the player can interact with to save the
 * current state of the game or to change the current character.
 * @author Martina Malagoli
 */
public interface SavePoint extends Interactable {

    /**
     * Method to save the game data.
     */
    void save();


}
