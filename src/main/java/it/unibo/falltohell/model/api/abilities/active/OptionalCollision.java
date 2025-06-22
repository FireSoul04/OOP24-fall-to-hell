package it.unibo.falltohell.model.api.abilities.active;

import it.unibo.falltohell.model.api.GameObject;

/**
 * Interface for lambda of the collision of Active ability
 * @author Sara Visani
 */
public interface OptionalCollision {

    /**
     * lambda for the pattern of collision
     * @param other gameobjects collided with
     */
    public void collided(final GameObject other);
}
