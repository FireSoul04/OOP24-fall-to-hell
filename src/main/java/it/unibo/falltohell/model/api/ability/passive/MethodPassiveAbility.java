package it.unibo.falltohell.model.api.ability.passive;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface for passive abilities that modify or extend the behavior of a
 * {@link Character
 * Character}'s methods.
 * Specifically, it allows overriding the {@link #update(double)} and
 * {@link #onCollision(GameObject, Vector2)} methods.
 *
 * @author Sara Visani
 */
public interface MethodPassiveAbility extends PassiveAbility {

    /**
     * Updates the state of the passive ability.
     * This method is typically called once per frame or tick.
     * <p>
     *
     * @param deltaTime the amount of time (in seconds) since the last update call
     */
    void update(double deltaTime);

    /**
     * Handles collision events involving this passive ability.
     * <p>
     *
     * @param other    the {@link GameObject} this ability has collided with
     * @param position the {@link Vector2} position at which the collision occurred
     */
    void onCollision(GameObject other, Vector2 position);
}
