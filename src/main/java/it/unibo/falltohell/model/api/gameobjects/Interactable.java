package it.unibo.falltohell.model.api.gameobjects;

import it.unibo.falltohell.model.api.GameObject;

/**
 * Game object that the player can interact with.
 * @author Martina Malagoli
 */
public interface Interactable extends GameObject {

    /**
     * Method to permit the interaction between the player and this object.
     */
    void interact();
}
