package it.unibo.falltohell.model.impl.physics;

import it.unibo.falltohell.util.Vector2;

/**
 * Class that determine a collision.
 *
 * @author Davide Mancini
 *
 * @param direction where the collision happened
 */
public record Collision(Vector2 direction) {
}
