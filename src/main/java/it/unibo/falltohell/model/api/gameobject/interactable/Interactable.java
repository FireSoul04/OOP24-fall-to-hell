package it.unibo.falltohell.model.api.gameobject.interactable;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * Game object that the player can interact with.
 * @author Martina Malagoli
 */
public interface Interactable extends GameObject {

    /**
     * Method to permit the interaction between the player and this object.
     */
    void interact(Character character);
}
