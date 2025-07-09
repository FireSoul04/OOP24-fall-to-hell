package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface to ask creations of a specific type of enemy
 * @author Sara Visani
 */

public interface EnemyFactory {
    /**
     * @param initialCords where the monster is created
     * @param character the player charater in the level
     * @return the monster type 1
     */
    Enemy CreateMonster1(final Vector2 initialCords, final Character character);
    /**
     * @param initialCords where the monster is created
     * @param character the player charater in the level
     * @return the monster type 2
     */
    Enemy CreateMonster2(final Vector2 initialCords, final Character character);
}
