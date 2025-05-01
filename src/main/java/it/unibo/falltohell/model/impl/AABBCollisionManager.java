package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.CollisionManager;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Collision manager that uses AABB (Aligned Axis Bounded Boxes) algorithm.
 */
public class AABBCollisionManager extends AbstractCollisionManager {

    @Override
    public boolean determineCollision(final GameObject g1, final GameObject g2) {
        final Vector2 p1 = g1.getPosition();
        final Vector2 p2 = g2.getPosition();

        // TODO Move the dependecy of width and height to the collider
        return p1.x() + g1.getWidth() > p2.x()
            && p1.x() < p2.x() + g2.getWidth()
            && p1.y() + g1.getHeight() > p2.y()
            && p1.y() < p2.y() + g2.getHeight();
    }
}
