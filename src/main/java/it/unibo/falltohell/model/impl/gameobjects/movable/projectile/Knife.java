package it.unibo.falltohell.model.impl.gameobjects.movable.projectile;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.Set;

/**
 * Class representing a throwable knife.
 */
public class Knife extends ProjectileImpl {

    private static final double DAMAGE = 5.0;

    private final Set<Class<? extends GameObject>> ignoreCollisionsObjects = Set.of(
        Character.class,
        Knife.class
    );

    /**
     * Creates a knife with a certain velocity.
     * @param level where knife is
     * @param position where knife will be thrown
     * @param velocity is the direction and speed of the knife
     */
    public Knife(final Level level, final Vector2 position, final Vector2 velocity) {
        super(level, position, velocity, new BoxCollider(Vector2.zero(), new Dimensions(5, 2)));
    }

    /**
     *  {@inheritDoc}
     *  Check if the collided object is collidable or not.
     *  If it is, the knife will destroy itself, otherwise it will pass through.
     */
    @Override
    public void onCollision(final GameObject other) {
        final boolean isOtherCollidable = ignoreCollisionsObjects.stream()
            .noneMatch(t -> t.isInstance(other));
        if (isOtherCollidable && other.isSolid() && !this.isHit()) {
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
