package it.unibo.falltohell.model.api.physics;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class for a generic collider.
 *
 * @author Davide Mancini
 */
public interface Collider {

    /**
     * @return offset relative to the game object
     */
    Vector2 offset();

    /**
     * @return size of this collider
     */
    Dimensions size();
}
