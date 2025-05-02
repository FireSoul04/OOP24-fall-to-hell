package it.unibo.falltohell.model.impl.gameobjects.movable.character;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
public class ProjectileImpl extends MovableImpl implements Projectile{
    private boolean hit;
    
    public ProjectileImpl(Vector2 vector2, double width, double height, double speedX, double speedY,Collider collider) {
        super(vector2, width, height, speedX, speedY, collider);
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
