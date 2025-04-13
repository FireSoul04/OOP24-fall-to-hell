package main.java.it.unibo.bubbleboop.model.api;

public interface Player extends Movable {
    
    /**
     * @param interactable object to be interacted with
     */
    void interact(Interactable interactable);
}
