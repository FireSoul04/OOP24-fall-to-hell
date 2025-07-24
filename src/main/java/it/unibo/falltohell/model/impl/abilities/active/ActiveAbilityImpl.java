package it.unibo.falltohell.model.impl.abilities.active;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbility;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.abilities.active.OptionalCollision;
import it.unibo.falltohell.model.api.abilities.active.PhysicalActiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of {@link ActiveAbility}.
 * Represents an active ability that can move, deal damage, and react to
 * collisions.
 *
 * @author Sara Visani
 */
public class ActiveAbilityImpl extends MovableImpl implements PhysicalActiveAbility {
    private final double damage;
    private final ActiveAbilityUpdate attack;
    private final Level level;
    private final Optional<OptionalCollision> collided;

    /**
     * Constructs an ActiveAbilityImpl instance.
     * <p>
     *
     * @param level    the {@link Level} where this ability exists
     * @param position the initial position of the ability
     * @param damage   the damage dealt by this ability
     * @param collider the {@link Collider} used for collision detection
     * @param velocity the initial {@link Vector2} velocity (x and y components)
     * @param attack   the lambda implementing the behavior for movement and attack,
     *                 receives velocity and delta time parameters
     * @param collided an optional lambda for custom collision handling; if empty,
     *                 default collision logic is used
     */
    public ActiveAbilityImpl(final Level level, final Vector2 position, final double damage, final Collider collider,
            final Vector2 velocity, final ActiveAbilityUpdate attack, final Optional<OptionalCollision> collided) {
        super(level, position, velocity.x(), velocity.y(), collider);
        this.damage = damage;
        this.attack = attack;
        this.level = level;
        this.collided = collided;
    }

    /**
     * Called when this ability collides with another {@link GameObject}.
     * <p>
     * Default behavior:
     * <ul>
     * <li>If the collided object is an {@link Enemy}, apply damage and remove this
     * ability from the level.</li>
     * <li>If the collided object is neither a {@link Character} nor a
     * {@link Projectile}, remove this ability.</li>
     * </ul>
     * If a custom collision handler lambda is present, it will be invoked instead.
     * </p>
     *
     * @param other the other {@link GameObject} this ability collided with
     */
    @Override
    public void onCollision(final GameObject other) {
        if (!this.collided.isPresent()) {
            if (other instanceof Enemy) {
                ((Enemy) other).setDamagedLife(this.damage);
                this.level.removeGameObject(this);
            }
            if (!(other instanceof Character && other instanceof Projectile)) {
                this.level.removeGameObject(this);
            }
        } else {
            this.collided.get().collided(other);
        }
    }

    /**
     * Updates this ability's state.
     * Delegates to the {@link ActiveAbilityUpdate} lambda passed during
     * construction.
     * <p>
     *
     * @param deltaTime the time elapsed since the last update, in seconds
     */
    @Override
    public void update(final double deltaTime) {
        this.attack.attack(new Vector2(super.getSpeedX(), super.getSpeedY()), deltaTime);
    }

}
