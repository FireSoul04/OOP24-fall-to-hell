package it.unibo.falltohell.model.api.gameobject.weapon;

import it.unibo.falltohell.model.api.gameobject.GameObject;

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
