package it.unibo.falltohell.model.impl.physics.colliders;

import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Class for a collider with a boxed shape.
 *
 * @author Davide Mancini
 *
 * @param offset where the collider is placed relative to the game object attached to
 * @param size of the box
 */
public record BoxCollider(Vector2 offset, Dimensions size) implements Collider {
}
