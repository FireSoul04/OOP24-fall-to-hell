package it.unibo.falltohell.model.impl.physics.colliders;

import it.unibo.falltohell.model.api.physics.CollisionsManager;
import it.unibo.falltohell.model.api.GameObject;

import java.util.List;
import java.util.Optional;

/**
 * Base class for any type of collision manager.
 *
 * @author Davide Mancini
 */
public abstract class AbstractCollisionsManager implements CollisionsManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkCollisions(final List<GameObject> gameObjects) {
        for (final GameObject g1 : gameObjects) {
            for (final GameObject g2 : gameObjects) {
                if (!g1.equals(g2) && g1.isSolid() && g2.isSolid()) {
                    final Optional<Collision> collision = this.determineCollision(g1, g2);

                    if (collision.isPresent()) {
                        // Notifies for both onCollision with direction and without
                        g1.onCollision(g2);
                        g1.onCollision(g2, collision.get().direction());
                    }
                }
            }
        }
    }

    /**
     * @param g1 first game object
     * @param g2 second game object
     * @return the collision between g1 and g2 colliders if they collided
     */
    abstract Optional<Collision> determineCollision(GameObject g1, GameObject g2);
}
