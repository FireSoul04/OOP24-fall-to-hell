package it.unibo.falltohell.model.impl.abilities.active;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityBuilder;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.abilities.active.OptionalCollision;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

/**
 * Builder class for {@link ActiveAbilityImpl}.
 * <p>
 * Usage example:
 *
 * <pre>{@code
 * ActiveAbility ability = new ActiveAbilityImplBuilder()
 *     .setLevel(level)
 *     .setPosition(new Vector2(10, 10))
 *     .setDamage(20)
 *     .setVelocity(new Vector2(5, 0))
 *     .setCollider(collider)
 *     .setAttack((velocity, dt) -> { ... })
 *     .setCollision(Optional.of(obj -> { ... }))
 *     .build();
 * }</pre>
 *
 * @author Sara Visani
 * @see ActiveAbilityImpl
 */
public class ActiveAbilityImplBuilder implements ActiveAbilityBuilder {

    private Level level;
    private Vector2 position;
    private double damage;
    private Collider collider;
    private Vector2 velocity;
    private ActiveAbilityUpdate attack;
    private Optional<OptionalCollision> collided = Optional.empty();

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder setLevel(final Level level) {
        this.level = level;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder setPosition(final Vector2 position) {
        this.position = position;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder setDamage(final double damage) {
        this.damage = damage;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder setCollider(final Collider collider) {
        this.collider = collider;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder setVelocity(final Vector2 velocity) {
        this.velocity = velocity;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder setAttack(final ActiveAbilityUpdate attack) {
        this.attack = attack;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder setCollision(final Optional<OptionalCollision> collided) {
        this.collided = collided;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImpl build() {
        if (level == null || position == null || collider == null || velocity == null || attack == null) {
            throw new IllegalStateException("Missing required fields in ActiveAbilityImplBuilder");
        }

        return new ActiveAbilityImpl(level, position, damage, collider, velocity, attack, collided);
    }
}
