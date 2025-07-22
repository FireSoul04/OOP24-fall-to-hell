package it.unibo.falltohell.model.impl.physics.colliders;

import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class for a collider with a boxed shape.
 *
 * @author Davide Mancini
 *
 * @param offset where the collider is placed relative to the game object attached to
 * @param size of the box
 */
public record BoxCollider(Vector2 offset, Dimensions size) implements Collider {

    public BoxCollider(final Vector2 offset, final Dimensions size) {
        this.offset = offset.subtract(new Vector2(size.width(), size.height()).divide(2));
        this.size = size;
    }
}
