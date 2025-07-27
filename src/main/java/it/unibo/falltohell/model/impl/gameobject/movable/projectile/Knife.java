package it.unibo.falltohell.model.impl.gameobject.movable.projectile;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.Set;

/**
 * Class representing a throwable knife.
 */
public class Knife extends ProjectileImpl {

    private static final double DAMAGE = 5.0;
    private static final Dimensions DIMENSIONS = new Dimensions(5, 2);

    private final Set<Class<? extends GameObject>> detectCollisionsObjects = Set.of(
        Enemy.class,
        BaseCollidableBlock.class
    );

    /**
     * Creates a knife with a certain velocity.
     * @param level where knife is
     * @param position where knife will be thrown
     * @param velocity is the direction and speed of the knife
     */
    public Knife(final Level level, final Vector2 position, final Vector2 velocity) {
        super(level, position, velocity, new BoxCollider(DIMENSIONS), "knife.png");
    }

    /**
     *  {@inheritDoc}
     *  Check if the collided object is collidable or not.
     *  If it is, the knife will destroy itself, otherwise it will pass through.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        final boolean isOtherCollidable = detectCollisionsObjects.stream()
            .anyMatch(t -> t.isInstance(other));
        if (isOtherCollidable && !this.isHit()) {
            this.setHit(true);
            this.onProjectileHit(other);
        }
    }

    /**
     * If knife takes an enemy, enemy will take damage.
     * @param other the game object that was hit
     */
    @Override
    protected void onProjectileHit(final GameObject other) {
        if (other instanceof Enemy e) {
            e.setDamagedLife(DAMAGE);
        }
    }
}
