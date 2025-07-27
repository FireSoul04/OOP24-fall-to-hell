package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.falltohell.model.api.listener.EnemyRespawnListener;
import it.unibo.falltohell.model.api.listener.EnterSafeZoneListener;
import it.unibo.falltohell.model.api.listener.ExitSafeZoneListener;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
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
public class SafeZoneManager {

    private final Set<BaseEntrance> entrances = new HashSet<>();
    private final Set<Enemy> enemies = new HashSet<>();
    private final List<EnemyRespawnListener> enemyCallbacks = new ArrayList<>();
    private boolean hasFirstEntered;

    /**
     * <p>
     * The shared {@link EnterSafeZoneListener}.
     * </p>
     * <p>
     * When invoked:
     * /p>
     * Removes all registered enemies from the
     * game
     */
    private final EnterSafeZoneListener entranceListener = () -> {
        this.handleSafeZoneEnter();
        this.hasFirstEntered = true;
    };

    /**
     * <p>
     * The shared {@link ExitSafeZoneListener}.
     * </p>
     * <p>
     * When invoked:
     * </p>
     * re-adds enemies and triggers their respawn
     */
    private final ExitSafeZoneListener exitListener = () -> {
        if (this.hasFirstEntered) {
            this.handleSafeZoneExit();
        }
    };

    /**
     * Registers a new {@link BaseEntrance}
     * to be assigned to the entrance.
     *
     * @param entrance the entrance to be added
     */
    public void addEntrance(final BaseEntrance entrance) {
        this.entrances.add(entrance);
        entrance.setListenerEnter(entranceListener);
        entrance.setListenerExit(exitListener);

    }

    /**
     * Adds an {@link Enemy} to be affected by the listener.
     *
     * @param enemy the enemy to be added
     */
    public void addEnemy(final Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Removes a {@link BaseEntrance} from the manager.
     *
     * @param entrance the entrance to remove
     */
    public void removeEntrance(final BaseEntrance entrance) {
        this.entrances.remove(entrance);
    }

    /**
     * Removes an {@link Enemy} from the manager.
     *
     * @param enemy the enemy to remove
     */
    public void removeEnemy(final Enemy enemy) {
        this.enemies.remove(enemy);
    }

    /**
     * <p>
     * Add a callback to be invoked when enemies are reactivated upon exiting
     * a safe zone.
     * </p>
     *
     * @param call the {@link EnemyRespawnListener} to assign
     */
    public void addEnemyCall(final EnemyRespawnListener call) {
        this.enemyCallbacks.add(call);
    }

    /**
     * <p>
     * Triggers the respawn logic by calling {@link EnemyRespawnListener#respawn()}.
     * </p>
     */
    public void resetEnemy() {
        this.enemyCallbacks.forEach(EnemyRespawnListener::respawn);
    }

    /**
     * <p>
     * Removes all registered enemies from the game world (safe zone entry).
     * </p>
     */
    private void handleSafeZoneEnter() {
        this.enemies.forEach(enemy -> enemy.getLevel().removeGameObject(enemy));
    }

    /**
     * <p>
     * Re-adds all registered enemies to the game world (safe zone exit).
     * </p>
     */
    private void handleSafeZoneExit() {
        this.enemies.forEach(enemy -> enemy.getLevel().addGameObject(enemy));
    }
}
