package it.unibo.falltohell.model.impl.physics.colliders;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Collision manager that uses AABB (Aligned Axis Bounded Boxes) algorithm.
 *
 * @author Davide Mancini
 */
public class AABBCollisionsManager extends AbstractCollisionsManager {

    /**
     * {@inheritDoc}
     * Works only if both g1 and g2 has box colliders.
     */
    @Override
    protected boolean determineCollision(final GameObject g1, final GameObject g2) {
        if (g1.getCollider() instanceof BoxCollider c1 && g2.getCollider() instanceof BoxCollider c2) {
            final Vector2 p1 = g1.getPosition().add(c1.getOffset());
            final Vector2 p2 = g2.getPosition().add(c2.getOffset());
            final Dimensions s1 = c1.getSize();
            final Dimensions s2 = c2.getSize();

            return p1.x() + s1.width() > p2.x()
                && p1.x() < p2.x() + s2.width()
                && p1.y() + s1.height() > p2.y()
                && p1.y() < p2.y() + s2.height();
        } else {
            throw new IllegalArgumentException("This algorithm doesn't support collision for colliders not type of BoxCollider");
        }
    }
}
