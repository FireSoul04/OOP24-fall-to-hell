package it.unibo.falltohell.model.api.gameobjects.movable;

import it.unibo.falltohell.model.api.gameobjects.Movable;

public interface Entity extends Movable{
    /**
     * Called from other entities to notify that the attack has hitted
     * @param damage
     */
    public void setDamadLife(final double damage);

    public double getLife();
}
