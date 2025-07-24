package it.unibo.falltohell.model.impl.gameobjects.movable.projectile;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;

/**
 * Implementation of the {@link Projectile} interface.
 * <p>
 * Represents a projectile in the game world that moves and can interact with other objects.
 * The projectile moves according to its speed, can be marked as "hit" when it collides with something solid,
 * and provides hooks for subclasses to customize update and collision behavior.
 * </p>
 */
public class ProjectileImpl extends MovableImpl implements Projectile{
    private boolean hit;

    public ProjectileImpl(Level level, Vector2 position, double speedX, double speedY, Collider collider) {
        super(level, position, speedX, speedY, collider);
        this.hit = false;
    }
    /**
     *  {@inheritDoc}
     */
    public boolean isHit() {
        return hit;
    }
    /**
     *  {@inheritDoc}
     */
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    /**
     *  {@inheritDoc}
     */
    @Override
    public void update(double deltaTime) {
        if (!hit) {
            super.update(deltaTime);
            this.onUpdate(deltaTime);
        }else if(isHit()){
            this.getLevel().removeGameObject(this);
        }
    }
     /**
     * Hook method for subclasses to add custom update logic.
     * Called after the base update if the projectile has not hit anything.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    protected void onUpdate(double deltaTime) {
        // Default: do nothing
    }
    /**
     *  {@inheritDoc}
     */
    @Override
    public void onCollision(GameObject other) {
        if (other != this && other.isSolid() && !hit) {
            this.hit = true;
            this.onProjectileHit(other);
        }
    }
    /**
     * Hook method for subclasses to add custom logic when the projectile hits another object.
     *
     * @param other the game object that was hit
     */
    protected void onProjectileHit(GameObject other) {
        // Default: do nothing
    }

}
