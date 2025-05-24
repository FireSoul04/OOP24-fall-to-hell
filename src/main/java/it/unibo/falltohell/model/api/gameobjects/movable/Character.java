package it.unibo.falltohell.model.api.gameobjects.movable;

import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.Movable;

public interface Character extends Movable {
    
    /**
     * @param interactable object to be interacted with
     */
    void interact(Interactable interactable);
}
