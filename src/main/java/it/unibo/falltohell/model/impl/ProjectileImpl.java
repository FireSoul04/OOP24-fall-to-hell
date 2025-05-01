package it.unibo.falltohell.model.impl;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
public class ProjectileImpl extends MovableImpl implements Projectile{
    private boolean hit;
    
    public ProjectileImpl(Vector2 vector2, double width, double height, double speedX, double speedY) {
        super(vector2, width, height, speedX, speedY);
        this.hit = false;
    }
    
    public boolean isHit() {
        return hit;
    }
    
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    
    @Override
    public void move(double deltaTime) {
        super.move(deltaTime);
        // da finire
    }

}
