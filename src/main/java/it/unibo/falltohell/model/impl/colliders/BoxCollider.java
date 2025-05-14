package it.unibo.falltohell.model.impl.colliders;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * This class gives a collider with a boxed shape.
 *
 * @author Davide Mancini
 */
public class BoxCollider implements Collider {

    private final Vector2 offset;
    private final Dimensions size;

    /**
     * Create a collider with a form of a box.
     */
    public BoxCollider(final Vector2 offset, final Dimensions size) {
        this.offset = offset;
        this.size = size;
    }

    @Override
    public Vector2 getOffset() {
        return this.offset;
    }

    /**
     * @return size of the box
     */
    public Dimensions getSize() {
        return this.size;
    }
}
