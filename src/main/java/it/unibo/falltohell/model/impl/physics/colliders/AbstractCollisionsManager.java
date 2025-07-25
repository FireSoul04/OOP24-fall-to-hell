package it.unibo.falltohell.model.impl.physics.colliders;

import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.api.physics.CollisionsManager;
import it.unibo.falltohell.model.api.GameObject;
import org.apache.commons.lang3.tuple.Pair;

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
     * the collisions manager knows if this game object left a collision.
     */
    private final Map<Pair<GameObject, GameObject>, Collision> lastFrameCollisions = new HashMap<>();

    /**
     * {@inheritDoc}
     * Check collisions only for movables game object in a radius of twice
     * the tile size of the GameObject's interface.
     */
    @Override
    public void checkCollisions(final List<GameObject> gameObjects) {
        final List<GameObject> collidableObjects = gameObjects.stream()
            .filter(t -> t.getCollider().isPresent())
            .toList();
        final List<GameObject> movables = collidableObjects.stream()
            .filter(t -> t instanceof Movable)
            .toList();
        for (final GameObject g1 : movables) {
            final List<GameObject> closeGameObjects = collidableObjects.stream()
                .filter(g2 -> g1.getPosition().distance(g2.getPosition()) < GameObject.TILE_SIZE * 2)
                .toList();
            for (final GameObject g2 : closeGameObjects) {
                if (!g1.equals(g2)) {
                    final Optional<Collision> collision = this.determineCollision(g1, g2);

                    if (collision.isPresent()) {
                        // Notifies for both onCollision with direction and without
                        g1.onCollision(g2);
                        g1.onCollision(g2, collision.get().direction());
                        this.lastFrameCollisions.put(Pair.of(g1, g2), collision.get());
                    } else if (this.lastFrameCollisions.containsKey(Pair.of(g1, g2))) {
                        // If there is not a collision, but in the last frame was a collision
                        g1.onCollisionExit(g2, this.lastFrameCollisions.get(Pair.of(g1, g2)).direction());
                        this.lastFrameCollisions.remove(Pair.of(g1, g2));
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
