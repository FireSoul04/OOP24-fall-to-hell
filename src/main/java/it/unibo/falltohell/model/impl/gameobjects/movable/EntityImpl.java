package it.unibo.falltohell.model.impl.gameobjects.movable;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */

public class EntityImpl extends MovableImpl implements Entity {

    private double life;

    public EntityImpl(final Level level,final Vector2 position,final double width,final double height,final double speedX,final double speedY,final Collider collider,final double life) {
        super(level, position, width, height, speedX, speedY, collider);
        this.life = life;
    }

    @Override
    public double getLife() {
        return this.life;
    }

    @Override
    public void setDamagedLife(final double damage){
        this.life -= damage;
    }

    protected void setLife(final double life) {
        this.life = life;
    }

    protected void addLife(final double life) {
        this.life = this.life + life;
    }

    @Override
    public boolean isDead(){
        if(this.life <= 0){
            return true;
        }
        return false;
    }
    
}
