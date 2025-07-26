package it.unibo.falltohell.model.impl.gameobject.movable.projectile;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.physics.Collider;
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
     * @param speed    the initial speed
     * @param collider the collider used for collision detection
     * @param damage   the amount of damage inflicted on hit
     * @param fileName is the name of the image file associated to the enemy projectile
     *
     * @see Level
     * @see Vector2
     * @see Collider
     */
    public BaseEnemyProjectile(final Level level, final Vector2 position, final Vector2 speed,
            final Collider collider, final double damage, final String fileName) {
        super(level, position, speed, collider, fileName);
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
        super.setPosition(getPosition().add(new Vector2(0, super.getSpeed().y() * deltaTime)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onProjectileHit(final GameObject other) {
        if (other instanceof Character character) {
            character.setDamagedLife(damage);
        }
        super.getLevel().removeGameObject(this);
    }
}
