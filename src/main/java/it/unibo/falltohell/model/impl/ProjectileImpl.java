package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Projectile;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
public class ProjectileImpl extends MovableImpl implements Projectile{
    private boolean hit;
    
    public ProjectileImpl(Vector2 position, double width, double height, double speedX, double speedY, Collider collider) {
        super(position, width, height, speedX, speedY,collider);
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
        super.update(deltaTime);
        // da finire
    }

}
