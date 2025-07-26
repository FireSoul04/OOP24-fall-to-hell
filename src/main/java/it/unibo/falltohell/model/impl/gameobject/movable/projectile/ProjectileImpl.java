package it.unibo.falltohell.model.impl.gameobject.movable.projectile;

import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;

/**
 * Implementation of the {@link Projectile} interface.
 * <p>
 * Represents a projectile in the game world that moves and can interact with
 * other objects.
 * The projectile moves according to its speed, can be marked as "hit" when it
 * collides with something solid,
 * and provides hooks for subclasses to customize update and collision behavior.
 * </p>
 * @author Casadei Lorenzo
 */
public class ProjectileImpl extends MovableImpl implements Projectile {
    private boolean hit;

    /**
     * Creates a projectile with a certain speed.
     * @param level the game level this projectile belongs to
     * @param position the initial position of the projectile
     * @param speed the initial speed of the projectile
     * @param collider the collider used for collision detection
     * @param fileName is the name of the image file associated to the projectile
     */
    public ProjectileImpl(final Level level, final Vector2 position, final Vector2 speed, final Collider collider,
                          final String fileName) {
        super(level, position, speed, collider);
        this.hit = false;
        this.initDrawable(Priority.MEDIUM, fileName);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * {@inheritDoc}
     */
    public void setHit(final boolean hit) {
        this.hit = hit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        if (!hit) {
            super.update(deltaTime);
            this.onUpdate(deltaTime);
        } else if (isHit()) {
            this.getLevel().removeGameObject(this);
        }
    }

    /**
     * Hook method for subclasses to add custom update logic.
     * Called after the base update if the projectile has not hit anything.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    protected void onUpdate(final double deltaTime) {
        // Default: do nothing
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other != this && other.isSolid() && !hit) {
            this.hit = true;
            this.onProjectileHit(other);
        }
    }

    /**
     * Hook method for subclasses to add custom logic when the projectile hits
     * another object.
     *
     * @param other the game object that was hit
     */
    protected void onProjectileHit(final GameObject other) {
        // Default: do nothing
    }

}
