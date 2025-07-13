package it.unibo.falltohell.model.impl.physics.colliders;

import it.unibo.falltohell.model.api.physics.CollisionsManager;
import it.unibo.falltohell.model.api.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Base class for any type of collision manager.
 *
 * @author Davide Mancini
 */
public abstract class AbstractCollisionsManager implements CollisionsManager {

    /**
     * Every frame this map saves if a game object is colliding so the next frame
     * the collisions manager knows if this game object left a collision
     */
    private final Map<GameObject, Collision> lastFrameCollisions = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkCollisions(final List<GameObject> gameObjects) {
        for (final GameObject g1 : gameObjects) {
            for (final GameObject g2 : gameObjects) {
                if (!g1.equals(g2)) {
                    final Optional<Collision> collision = this.determineCollision(g1, g2);

                    if (collision.isPresent()) {
                        // Notifies for both onCollision with direction and without
                        g1.onCollision(g2);
                        g1.onCollision(g2, collision.get().direction());
                        this.lastFrameCollisions.put(g1, collision.get());
                    } else if (this.lastFrameCollisions.containsKey(g1)) {
                        // If there is not a collision, but in the last frame was a collision
                        g1.onCollisionExit(g2, this.lastFrameCollisions.get(g1).direction());
                        this.lastFrameCollisions.remove(g1);
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
