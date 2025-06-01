package it.unibo.falltohell.model.api.gameobjects.movable;

import it.unibo.falltohell.model.api.gameobjects.Movable;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */

public interface Entity extends Movable{
    /**
     * Called from other entities to notify that the attack has hitted
     * @param damage
     */
    public void setDamadLife(final double damage);

    public double getLife();
    /**
     * @return if an Entity is dead
     */
    public boolean isDead();
}
