package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.listener.EnemyRespawnListener;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;

/**
 * <p>
 * Manages the logic related to safe zones in the game. This class coordinates
 * engagement behavior for {@link Enemy} instances in response to triggers
 * from {@link BaseEntrance} objects.
 * </p>
 *
 * <p>
 * It provides a shared {@link EnterSafeZoneListener} and
 * {@link ExitSafeZoneListener}
 * that can be assigned to entrances.
 * When triggered, this listener toggles the state of all registered enemies:
 * <ul>
 * <li>Enemies are removed when entering a safe zone</li>
 * <li>Enemies are re-added and respawned when exiting</li>
 * </ul>
 * </p>
 *
 * @author Sara Visani
 * @see Enemy
 * @see BaseEntrance
 * @see AggroListener
 * @see EnemyRespawnListener
 */
public interface SafeZoneManager {

    /**
     * Registers a new {@link BaseEntrance}
     * to be assigned to the entrance.
     *
     * @param entrance the entrance to be added
     */
    void addEntrance(BaseEntrance entrance);

    /**
     * Adds an {@link Enemy} to be affected by the listener.
     *
     * @param enemy    the enemy to be added
     * @param filename the name of the file representing the enemy's sprite
     */
    void addEnemy(Enemy enemy, String filename);

    /**
     * Removes a {@link BaseEntrance} from the manager.
     *
     * @param entrance the entrance to remove
     */
    void removeEntrance(BaseEntrance entrance);

    /**
     * Removes an {@link Enemy} from the manager.
     *
     * @param enemy the enemy to remove
     */
    void removeEnemy(Enemy enemy);

    /**
     * <p>
     * Add a callback to be invoked when enemies are reactivated upon exiting
     * a safe zone.
     * </p>
     *
     * @param call the {@link EnemyRespawnListener} to assign
     */
    void addEnemyCall(EnemyRespawnListener call);

}
