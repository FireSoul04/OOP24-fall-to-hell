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
 * @see Enemy
 * @see Level
 * @see Character
 * @author Sara Visani
 */
public interface EnemyFactory {

    /**
     * Creates an instance of monster type Centaur.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @param character    the {@link Character} the enemy will interact with
     * @return a new instance of Centaur as an {@link Enemy}
     */
    Enemy createCentaur(Level level, Vector2 initialCords, Character character);

    /**
     * Creates an instance of monster type Tengu.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @param character    the {@link Character} the enemy will interact with
     * @return a new instance of Tengu as an {@link Enemy}
     */
    Enemy createTengu(Level level, Vector2 initialCords, Character character);

    /**
     * Creates an instance of monster Imp.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @param character    the {@link Character} the enemy will interact with
     * @return a new instance of Imp as an {@link Enemy}
     */
    Enemy createImp(Level level, Vector2 initialCords, Character character);

    /**
     * Creates an instance of monster type Lotawiec.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @param character    the {@link Character} the enemy will interact with
     * @return a new instance of Lotawiec as an {@link Enemy}
     */
    Enemy createLotawiec(Level level, Vector2 initialCords, Character character);
}
