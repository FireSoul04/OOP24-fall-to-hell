package it.unibo.falltohell.model.api.abilities.active;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

/**
 * @author Sara Visani
 */
public interface ActiveAbilityBuilder {

    /**
     * Sets the level.
     *
     * @param level the level where the ability is spawned
     * @return this builder
     */
    ActiveAbilityBuilder setLevel(Level level);

    /**
     * Sets the position.
     *
     * @param position the initial position
     * @return this builder
     */
    ActiveAbilityBuilder setPosition(Vector2 position);

    /**
     * Sets the damage dealt by the ability.
     *
     * @param damage the damage value
     * @return this builder
     */
    ActiveAbilityBuilder setDamage(double damage);

    /**
     * Sets the collider used for collision detection.
     *
     * @param collider the collider
     * @return this builder
     */
    ActiveAbilityBuilder setCollider(Collider collider);

    /**
     * Sets the initial velocity.
     *
     * @param velocity the speed in x and y
     * @return this builder
     */
    ActiveAbilityBuilder setVelocity(Vector2 velocity);

    /**
     * Sets the attack logic.
     *
     * @param attack the lambda defining update behavior
     * @return this builder
     */
    ActiveAbilityBuilder setAttack(ActiveAbilityUpdate attack);

    /**
     * Optionally sets the collision handler.
     *
     * @param collided an optional collision logic lambda
     * @return this builder
     */
    ActiveAbilityBuilder setCollision(Optional<OptionalCollision> collided);

    /**
     * Builds and returns a new {@link ActiveAbilityImpl} instance.
     *
     * @return the constructed ActiveAbilityImpl
     * @throws IllegalStateException if any required field is missing
     */
    ActiveAbility build();
}
