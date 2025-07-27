package it.unibo.falltohell.model.impl.manager;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.model.impl.physics.Collision;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

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
    protected Optional<Collision> determineCollision(final GameObject g1, final GameObject g2) {
        if (g1.getCollider().orElseThrow() instanceof BoxCollider c1
            && g2.getCollider().orElseThrow() instanceof BoxCollider c2) {
            final Vector2 p1 = g1.getPosition().add(c1.offset());
            final Vector2 p2 = g2.getPosition().add(c2.offset());
            final Dimensions s1 = c1.size();
            final Dimensions s2 = c2.size();
            final boolean collided = p1.x() + s1.width() > p2.x()
                && p1.x() < p2.x() + s2.width()
                && p1.y() + s1.height() > p2.y()
                && p1.y() < p2.y() + s2.height();
            if (collided) {
                return Optional.of(new Collision(this.getDirection(p1, p2, s1, s2)));
            }
            return Optional.empty();
        } else {
            throw new IllegalArgumentException("This algorithm doesn't support collision for colliders not type of BoxCollider");
        }
    }

    private Vector2 getDirection(final Vector2 p1, final Vector2 p2, final Dimensions s1, final Dimensions s2) {
        final Vector2 offset1 = new Vector2(s1.width(), s1.height()).divide(2);
        final Vector2 offset2 = new Vector2(s2.width(), s2.height()).divide(2);
        final Vector2 direction = p2.subtract(offset1).subtract(p1.subtract(offset2));
        if (Math.abs(direction.x()) >= Math.abs(direction.y())) {
            return new Vector2(Math.signum(direction.x()), 0);
        } else {
            return new Vector2(0, Math.signum(direction.y()));
        }
    }
}
