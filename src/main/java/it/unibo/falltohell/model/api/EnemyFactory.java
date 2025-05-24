package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface to ask creations of a specific type of enemy
 * @author Sara Visani
 */

public interface EnemyFactory {
    /**
     * @param initialCords where the monster is created
     * @return the monster type 1
     */
    Enemy CreateMonster1(final Vector2 initialCords);
    /**
     * @param initialCords where the monster is created
     * @return the monster type 2
     */
    Enemy CreateMonster2(final Vector2 initialCords);
}
