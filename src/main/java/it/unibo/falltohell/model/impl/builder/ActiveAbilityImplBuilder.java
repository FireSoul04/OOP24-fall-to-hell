package it.unibo.falltohell.model.impl.builder;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.builder.ActiveAbilityBuilder;
import it.unibo.falltohell.model.api.ability.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.ability.active.OptionalCollision;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.ability.active.ActiveAbilityImpl;
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
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
    justification = "Level is managed externally and is safe to store by reference")
    public ActiveAbilityImplBuilder withLevel(final Level level) {
        this.level = level;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder withPosition(final Vector2 position) {
        this.position = position;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder withDamage(final double damage) {
        this.damage = damage;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder withCollider(final Collider collider) {
        this.collider = collider;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder withVelocity(final Vector2 velocity) {
        this.velocity = velocity;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder withAttack(final ActiveAbilityUpdate attack) {
        this.attack = attack;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityImplBuilder withCollision(final Optional<OptionalCollision> collided) {
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
