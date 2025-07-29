package it.unibo.falltohell.model.api.builder;

import java.util.Optional;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.ability.active.ActiveAbility;
import it.unibo.falltohell.model.api.ability.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.ability.active.OptionalCollision;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

/**
 * <p>
 * A builder interface for constructing {@link ActiveAbility} instances
 * with a fluent API.
 * </p>
 *
 * <p>
 * This builder allows configuration of:
 * </p>
 * <ul>
 *   <li>The level in which the ability is spawned</li>
 *   <li>Initial position and velocity</li>
 *   <li>Damage value and collider for collision detection</li>
 *   <li>Custom update logic via {@link ActiveAbilityUpdate}</li>
 *   <li>Optional collision behavior via {@link OptionalCollision}</li>
 * </ul>
 *
 * <p>
 * Usage of this builder ensures that all required properties are set before
 * creating an ability instance.
 * </p>
 *
 * @author Sara Visani
 * @see ActiveAbility
 * @see ActiveAbilityUpdate
 * @see OptionalCollision
 * @see it.unibo.falltohell.model.impl.ability.ActiveAbilityImpl
 */
public interface ActiveAbilityBuilder {

    /**
     * Sets the level.
     *
     * @param level the level where the ability is spawned
     * @return this builder
     */
    ActiveAbilityBuilder withLevel(Level level);

    /**
     * Sets the position.
     *
     * @param position the initial position
     * @return this builder
     */
    ActiveAbilityBuilder withPosition(Vector2 position);

    /**
     * Sets the damage dealt by the ability.
     *
     * @param damage the damage value
     * @return this builder
     */
    ActiveAbilityBuilder withDamage(double damage);

    /**
     * Sets the collider used for collision detection.
     *
     * @param collider the collider
     * @return this builder
     */
    ActiveAbilityBuilder withCollider(Collider collider);

    /**
     * Sets the initial velocity.
     *
     * @param velocity the speed in x and y
     * @return this builder
     */
    ActiveAbilityBuilder withVelocity(Vector2 velocity);

    /**
     * Sets the attack logic.
     *
     * @param attack the lambda defining update behavior
     * @return this builder
     */
    ActiveAbilityBuilder withAttack(ActiveAbilityUpdate attack);

    /**
     * Optionally sets the collision handler.
     *
     * @param collided an optional collision logic lambda
     * @return this builder
     */
    ActiveAbilityBuilder withCollision(Optional<OptionalCollision> collided);

    /**
     * Builds and returns a new {@link ActiveAbilityImpl} instance.
     *
     * @return the constructed ActiveAbilityImpl
     * @throws IllegalStateException if any required field is missing
     */
    ActiveAbility build();
}
