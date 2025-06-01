package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.Entity;

/**
 * Abstract class for all Enemies, set the base enemy
 * @author Sara Visani
 */

public interface Enemy extends Entity{
    /**
     * Called to change the Player to follow
     * @param character
     */
    public void setCharacter(final Character character);
}
