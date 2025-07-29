package it.unibo.falltohell.model.impl.physics;

import it.unibo.falltohell.model.api.gameobject.GameObject;
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

    /**
     * Constructor for a collider with an offset declaring how the collider's position is moved
     * relative to the object attached with.
     * @param offset where the collider is placed relative to the game object attached to
     * @param size of the box
     */
    public BoxCollider(final Vector2 offset, final Dimensions size) {
        this.offset = offset.subtract(new Vector2(size.width(), size.height()).divide(2));
        this.size = size;
    }

    /**
     * Constructor with default offset as null vector.
     * @param size of the box
     */
    public BoxCollider(final Dimensions size) {
        this(Vector2.zero(), size);
    }

    /**
     * Constructor with default offset as null vector and as square shaped with game object's
     * TILE_SIZE as the square's side.
     */
    public BoxCollider() {
        this(new Dimensions(GameObject.TILE_SIZE, GameObject.TILE_SIZE));
    }
}
