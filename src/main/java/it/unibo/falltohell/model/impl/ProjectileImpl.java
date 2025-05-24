package it.unibo.falltohell.model.impl;
import it.unibo.falltohell.model.api.Position;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
public class ProjectileImpl extends MovableImpl implements Projectile{
    private boolean hit;
    
    public ProjectileImpl(Position position, double width, double height, double speedX, double speedY) {
        super(position, width, height, speedX, speedY);
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
