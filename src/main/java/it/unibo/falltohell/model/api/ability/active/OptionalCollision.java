package it.unibo.falltohell.model.api.ability.active;

import it.unibo.falltohell.model.api.gameobject.GameObject;

/**
 * Functional interface representing the collision behavior of an active
 * ability.
 * Used to define custom behavior when the ability collides with another
 * {@link GameObject}.
 * Typically implemented using a lambda expression.
 *
 * @author Sara Visani
 */
@FunctionalInterface
public interface OptionalCollision {

    /**
     * Defines the behavior to execute when a collision occurs.
     * <p>
     * 
     * @param other the {@link GameObject} that the ability has collided with
     */
    void collided(GameObject other);
}
