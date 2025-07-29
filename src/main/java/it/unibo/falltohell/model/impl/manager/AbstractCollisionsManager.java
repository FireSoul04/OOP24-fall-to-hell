package it.unibo.falltohell.model.impl.manager;

import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.manager.CollisionsManager;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.impl.physics.Collision;
import it.unibo.falltohell.util.Dimensions;
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
     * the tile size of the GameObject's interface. When a movable collide with
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
            // Checks collisions only for objects closer than twice the major dimension of the object's collider
            final Dimensions c1 = g1.getCollider().orElseThrow().size();
            final double range = Math.max(c1.width(), c1.height());
            final List<GameObject> closeGameObjects = collidableObjects.stream()
                .filter(g2 -> !g1.equals(g2))
                .filter(g2 -> g1.getPosition().distance(g2.getPosition()) < range * 2)
                .toList();
            for (final GameObject g2 : closeGameObjects) {
                this.checkCollision(g1, g2);
            }
        }
    }

    private void checkCollision(final GameObject g1, final GameObject g2) {
        final Optional<Collision> collision = this.determineCollision(g1, g2);

        if (collision.isPresent()) {
            g1.onCollision(g2, collision.get().direction());
            if (!(g2 instanceof Movable)) { // Prevents redundant calls in the cycle
                // The direction will be inverted because the non-movable objects collide in the opposite direction
                g2.onCollision(g1, collision.get().direction().invert());
            }
            this.lastFrameCollisions.put(Pair.of(g1, g2), collision.get());
        } else if (this.lastFrameCollisions.containsKey(Pair.of(g1, g2))) {
            // If there is not a collision, but in the last frame was a collision
            g1.onCollisionExit(g2, this.lastFrameCollisions.get(Pair.of(g1, g2)).direction());
            if (!(g2 instanceof Movable)) { // Prevents redundant calls in the cycle
                // The direction will be inverted because the non-movable objects collide in the opposite direction
                g2.onCollisionExit(g1, this.lastFrameCollisions.get(Pair.of(g1, g2)).direction().invert());
            }
            this.lastFrameCollisions.remove(Pair.of(g1, g2));
        }
    }

    /**
     * @param g1 first game object
     * @param g2 second game object
     * @return the collision between g1 and g2 colliders if they collided
     */
    abstract Optional<Collision> determineCollision(GameObject g1, GameObject g2);
}
