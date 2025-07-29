package it.unibo.falltohell.model.api.factory;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;
import it.unibo.falltohell.util.Vector2;

/**
 * Factory interface for creating different types of {@link Enemy} instances in
 * a level.
 * This interface abstracts the logic for instantiating specific enemy types
 * tied to
 * a given {@link Level} and a {@link Vector2}.
 * <p>
 * It helps decouple the creation logic of enemies from the game engine.
 *
 * @see Enemy
 * @see Level
 * @see SafeZoneManager
 * @author Sara Visani
 */
public interface EnemyFactory {

    /**
     * Creates an instance of monster type Centaur.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @return a new instance of Centaur as an {@link Enemy}
     */
    Enemy createCentaur(Level level, Vector2 initialCords);

    /**
     * Creates an instance of monster type Tengu.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @return a new instance of Tengu as an {@link Enemy}
     */
    Enemy createTengu(Level level, Vector2 initialCords);

    /**
     * Creates an instance of monster Imp.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @return a new instance of Imp as an {@link Enemy}
     */
    Enemy createImp(Level level, Vector2 initialCords);

    /**
     * Creates an instance of monster type Lotawiec.
     *
     * @param level        the {@link Level} in which the enemy is spawned
     * @param initialCords the {@link Vector2} position where the enemy is created
     * @return a new instance of Lotawiec as an {@link Enemy}
     */
    Enemy createLotawiec(Level level, Vector2 initialCords);

    /**
     * Retrieves or creates a shared instance of {@link SafeZoneManager} for the
     * specified {@link Level}.
     * <p>
     * This manager coordinates the behavior of enemies when the player enters or
     * exits a safe zone,
     * allowing grouped reset and visibility logic to be applied consistently.
     * </p>
     *
     * @param level the current game level
     * @return a {@link SafeZoneManager} instance associated with the given level
     */
    SafeZoneManager askManager(Level level);
}
