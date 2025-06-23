package it.unibo.falltohell.model.api.gameobjects.movable;

import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */

public interface Entity extends Movable{

    /**
     * @return statistics of an Entity
     */
    public Statistics getStats();

    /**
     * Called from other entities to notify that the attack has hitted
     * @param damage
     */
    public void setDamagedLife(final double damage);

    /**
     * @return if an Entity is dead
     */
    public boolean isDead();
}
