package it.unibo.falltohell.model.api.abilities;

import it.unibo.falltohell.model.api.GameObject;

public interface OptionalCollision {

    /**
     * lambda for the pattern of collision
     * @param other gameobject collided with
     */
    public void collided(final GameObject other);
}
