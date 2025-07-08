package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Factory interface for creating different types of {@link Enemy} instances in
 * a level.
 * This interface abstracts the logic for instantiating specific enemy types
 * tied to
 * a given {@link Level} and a {@link Character}.
 * <p>
 * It helps decouple the creation logic of enemies from the game engine.
 *
 * @author Sara Visani
 */
public interface EnemyFactory {

    /**
     * Creates an instance of monster type 1.
     * <p>
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @param character    the {@link Character} the enemy will interact with
     * @return a new instance of monster type 1 as an {@link Enemy}
     */
    Enemy createCentaur(Level level, Vector2 initialCords, Character character);

    /**
     * Creates an instance of monster type 2.
     * <p>
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @param character    the {@link Character} the enemy will interact with
     * @return a new instance of monster type 2 as an {@link Enemy}
     */
    Enemy createTengu(Level level, Vector2 initialCords, Character character);

    Enemy createImp(Level level, Vector2 initialCords, Character character);

    Enemy createLotawiec(Level level, Vector2 initialCords, Character character);
}
