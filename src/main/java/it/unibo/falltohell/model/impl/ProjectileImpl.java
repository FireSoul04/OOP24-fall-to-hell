package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Projectile;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.MovableImpl;
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
        }
    }
    @Override
    public void onCollision(GameObject other) {
        if (other != this && other.isSolid() && !hit) {
            this.hit = true;
            
        }
    }

}
