package it.unibo.falltohell.model.api.gameobject.interactable;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * Game object that a character can interact with.
 * @author Martina Malagoli
 */
public interface Interactable extends GameObject {

    /**
     * Method to permit the interaction between a character and this object.
     * @param character that interacts with the interactable object
     */
    void interact(Character character);
}
