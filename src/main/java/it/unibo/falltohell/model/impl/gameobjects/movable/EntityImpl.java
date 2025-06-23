package it.unibo.falltohell.model.impl.gameobjects.movable;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */

public class EntityImpl extends MovableImpl implements Entity {

    private Statistics stats;

    public EntityImpl(final Level level,final Vector2 position, final Collider collider, final Statistics stats) {
        super(level, position, stats.getDimensions().width(), stats.getDimensions().height(), stats.getSpeed().x(), stats.getSpeed().y(), collider);
        this.stats = stats;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Statistics getStats() {
        return this.stats;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public void setDamagedLife(final double damage){
        this.stats.subLife(damage);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public boolean isDead(){
        if(this.stats.getLife() <= 0){
            return true;
        }
        return false;
    }
    
}
