package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobjects.movable.Enemy;
import it.unibo.falltohell.model.util.Vector2;

public interface EnemyFactory {
    /**
     * @param initialCords where the monster is created
     * @return the monster type 1
     */
    Enemy CreateMonster1(Vector2 initialCords);
    /**
     * @param initialCords where the monster is created
     * @return the monster type 2
     */
    Enemy CreateMonster2(Vector2 initialCords);
}
