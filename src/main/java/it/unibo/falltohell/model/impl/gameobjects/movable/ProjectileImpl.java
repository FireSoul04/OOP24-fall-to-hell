package it.unibo.falltohell.model.impl.gameobjects.movable;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
public class ProjectileImpl extends MovableImpl implements Projectile{
    private boolean hit;

    public ProjectileImpl(Level level, Vector2 position, double width, double height, double speedX, double speedY, Collider collider) {
        super(level, position, width, height, speedX, speedY, collider);
        this.hit = false;
    }
    
    public boolean isHit() {
        return hit;
    }
    
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    
    @Override
    public void update(double deltaTime) {
        if (!hit) {
            super.update(deltaTime);
            this.onUpdate(deltaTime);
        }
    }
    protected void onUpdate(double deltaTime) {
        // Default: do nothing
    }
    @Override
    public void onCollision(GameObject other) {
        if (other != this && other.isSolid() && !hit) {
            this.hit = true;
            this.onProjectileHit(other);
        }
    }
    protected void onProjectileHit(GameObject other) {
        // Default: do nothing
    }

}
