package it.unibo.falltohell.model.api.physics;

import it.unibo.falltohell.model.util.Vector2;

public interface Collider {

    /**
     * @return offset relative to the game object.
     */
    Vector2 getOffset();
}
