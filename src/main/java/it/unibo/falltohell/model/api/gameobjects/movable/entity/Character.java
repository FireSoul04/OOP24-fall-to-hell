package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;

public interface Character extends Entity {
    
    /**
     * @param interactable object to be interacted with
     */
    void interact(Interactable interactable);
}
