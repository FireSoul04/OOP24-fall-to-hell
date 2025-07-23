package it.unibo.falltohell.model.impl.gameobjects.movable.projectile;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.ProjectileImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Represents a basic enemy projectile with vertical movement and damage
 * capability.
 * <p>
 * This class extends {@link ProjectileImpl} and provides base logic for
 * projectile motion
 * and interaction with other {@link GameObject}s, specifically
 * {@link Character}s.
 *
 * @author Sara Visani
 * @see ProjectileImpl
 * @see Character
 * @see Vector2
 */
public class BaseEnemyProjectile extends ProjectileImpl {

    private final double damage;

    /**
     * Constructs a new {@code BaseEnemyProjectile}.
     *
     * @param level    the game level this projectile belongs to
     * @param position the initial position of the projectile
     * @param speedX   the initial horizontal speed
     * @param speedY   the initial vertical speed
     * @param collider the collider used for collision detection
     * @param damage   the amount of damage inflicted on hit
     *
     * @see Level
     * @see Vector2
     * @see Collider
     */
    public BaseEnemyProjectile(final Level level, final Vector2 position, final double speedX, final double speedY,
            final Collider collider, final double damage) {
        super(level, position, speedX, speedY, collider);
        this.damage = damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        if (!super.isHit()) {
            this.onUpdate(deltaTime);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onUpdate(final double deltaTime) {
        super.setPosition(getPosition().add(new Vector2(0, super.getSpeedY() * deltaTime)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onProjectileHit(final GameObject other) {
        if (other instanceof Character) {
            ((Character) other).setDamagedLife(damage);
        }
        super.getLevel().removeGameObject(this);
    }
}
