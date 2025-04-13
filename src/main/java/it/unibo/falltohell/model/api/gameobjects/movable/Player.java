package it.unibo.falltohell.model.api.gameobjects.movable;

import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.Movable;

public interface Player extends Movable {
    
    /**
     * @param interactable object to be interacted with
     */
    void interact(Interactable interactable);
}
