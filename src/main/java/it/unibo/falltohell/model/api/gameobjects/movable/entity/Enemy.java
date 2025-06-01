package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.Entity;

public interface Enemy extends Entity{
    /**
     * Called to change the Player to follow
     * @param character
     */
    public void setCharacter(final Character character);
}
