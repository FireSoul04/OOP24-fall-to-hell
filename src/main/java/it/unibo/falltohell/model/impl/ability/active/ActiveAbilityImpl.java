package it.unibo.falltohell.model.impl.ability.active;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.ability.active.ActiveAbility;
import it.unibo.falltohell.model.api.ability.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.ability.active.OptionalCollision;
import it.unibo.falltohell.model.api.ability.active.PhysicalActiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;
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
        super(level, position, velocity, collider);
        this.damage = damage;
        this.attack = attack;
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
     * @param direction where it collides
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (this.collided.isEmpty()) {
            if (other instanceof Enemy) {
                ((Enemy) other).setDamagedLife(this.damage);
                super.getLevel().removeGameObject(this);
            }
            if (!(other instanceof Character && other instanceof Projectile)) {
                super.getLevel().removeGameObject(this);
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
        this.attack.attack(super.getSpeed(), deltaTime);
    }

}
