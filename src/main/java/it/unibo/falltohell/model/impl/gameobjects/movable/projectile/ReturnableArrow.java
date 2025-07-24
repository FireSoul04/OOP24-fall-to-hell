package it.unibo.falltohell.model.impl.gameobjects.movable.projectile;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Archer;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.Level;

/**
 * A special projectile that can return to the archer after being fired.
 * Behavior:
 * - Initially behaves like a normal arrow: it can hit solid objects and
 * enemies.
 * - When the return is activated via {@code startReturn()}, the arrow becomes
 * non-solid,
 * flies back toward the owner, and can hit enemies again during its return.
 * - Once it reaches the archer, it is removed from the level and ammo is
 * restored.
 */
public class ReturnableArrow extends ProjectileImpl {

    private boolean returning;
    private final Archer owner;
    private final double originalSpeed;

    /**
     * Creates a new ReturnableArrow instance.
     *
     * @param level    the level the arrow belongs to
     * @param position the initial position of the arrow
     * @param speed    the initial speed
     * @param collider the collider used for collisions
     * @param owner    the archer who fired the arrow
     * @param fileName is the name of the image file associated to the returnable arrow
     */
    public ReturnableArrow(final Level level, final Vector2 position, final Vector2 speed, final Collider collider,
                           final Archer owner, final String fileName) {
        super(level, position, speed, collider, fileName);
        this.owner = owner;
        this.originalSpeed = speed.magnitude();
    }

    /**
     * Starts the return phase of the arrow.
     * The arrow becomes non-solid, resets hit state, and begins flying toward the
     * owner.
     */
    public void startReturn() {
        this.returning = true;
        this.setSolid(false);
        this.setHit(false);
    }

    /**
     * @return true if the arrow is currently returning to the owner
     */
    public boolean isReturning() {
        return returning;
    }

    /**
     * Updates the arrow's state and position.
     * During the return phase, it flies manually toward the owner.
     * If close enough to the owner, it is destroyed and ammo is restored.
     *
     * @param deltaTime time since the last update
     */
    @Override
    public void update(final double deltaTime) {
        if (this.isReturning()) {
            final Vector2 direction = owner.getPosition().subtract(this.getPosition()).normalize();
            this.setSpeed(direction.multiply(originalSpeed));
            final Vector2 displacement = this.getSpeed().multiply(deltaTime);
            this.setPosition(this.getPosition().add(displacement));
            if (this.getPosition().distance(owner.getPosition()) < 0.5) {
                owner.returnArrow(this);
                this.destroy();
            }
        } else if (!isHit()) {
            super.update(deltaTime);
        }
    }

    /**
     * Handles collisions based on arrow state.
     * - While going forward: stops on any solid object and calls base logic.
     * - While returning: only hits enemies (and does not stop).
     *
     * @param other the object the arrow collided with
     */
    @Override
    public void onCollision(final GameObject other) {
        if (!this.returning && other != this && other.isSolid()) {
            super.onCollision(other);
            this.setHit(true);
        } else if (this.returning && isEnemy(other)) {
            this.onProjectileHit(other);
        }
    }

    /**
     * Checks if the given object is a valid enemy.
     *
     * @param obj the object to check
     * @return true if the object is an enemy and not the owner
     */
    private boolean isEnemy(final GameObject obj) {
        return obj instanceof Enemy && obj != owner;
    }

    /**
     * Removes the arrow from the level.
     */
    public void destroy() {
        this.getLevel().removeGameObject(this);
    }
}
