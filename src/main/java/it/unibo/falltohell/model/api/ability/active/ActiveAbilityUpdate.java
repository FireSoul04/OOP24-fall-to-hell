package it.unibo.falltohell.model.api.ability.active;

import it.unibo.falltohell.util.Vector2;

/**
 * Functional interface representing the update logic of an active ability.
 * Defines how the ability should behave during each update cycle
 * This interface is typically implemented using a lambda expression.
 *
 * @author Sara Visani
 */
@FunctionalInterface
public interface ActiveAbilityUpdate {

    /**
     * Defines the behavior of the active ability during an update.
     * <p>
     * 
     * @param velocity  the current velocity of the ability, see {@link Vector2}
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    void attack(Vector2 velocity, double deltaTime);
}
