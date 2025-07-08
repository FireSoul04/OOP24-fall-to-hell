package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface to ask creations of a specific type of enemy.
 *
 * @author Sara Visani
 */

public interface EnemyFactory {

    /**
     * @param level        level where the enemy is
     * @param initialCords where the monster is created
     * @param character    the player character in the level
     * @return the monster type 1
     */
    Enemy createMonster1(Level level, Vector2 initialCords, Character character);

    /**
     * @param level        level where the enemy is
     * @param initialCords where the monster is created
     * @param character    the player character in the level
     * @return the monster type 2
     */
    Enemy createMonster2(Level level, Vector2 initialCords, Character character);
}
