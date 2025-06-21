package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface to ask creations of a specific type of enemy
 * @author Sara Visani
 */

public interface EnemyFactory {
    
    /**
     * @param level level where the enemy is
     * @param initialCords where the monster is created
     * @param character the player character in the level
     * @return the monster type 1
     */
    Enemy CreateMonster1(final Level level, final Vector2 initialCords, final Character character);

    /**
     * @param level level where the enemy is
     * @param initialCords where the monster is created
     * @param character the player character in the level
     * @return the monster type 2
     */
    Enemy CreateMonster2(final Level level, final Vector2 initialCords, final Character character);
}
