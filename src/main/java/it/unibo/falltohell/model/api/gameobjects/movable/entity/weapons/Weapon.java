package it.unibo.falltohell.model.api.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.GameObject;

/**
 * Interface for any weapon in the game.
 *
 * @author Davide Mancini
 */
public interface Weapon extends GameObject {

    /**
     * Perform an attack.
     */
    void attack();
}
