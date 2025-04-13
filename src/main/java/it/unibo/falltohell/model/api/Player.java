package it.unibo.falltohell.model.api;

public interface Player extends Movable {
    
    /**
     * @param interactable object to be interacted with
     */
    void interact(Interactable interactable);
}
