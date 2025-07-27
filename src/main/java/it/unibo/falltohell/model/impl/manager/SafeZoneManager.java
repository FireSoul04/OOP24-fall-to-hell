package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.falltohell.model.api.listener.AggroListener;
import it.unibo.falltohell.model.api.listener.EnemyRespawnListener;
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
 * It provides a shared {@link AggroListener} that can be assigned to entrances.
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
    private boolean isEnteringSafeZone = true;
    private final List<EnemyRespawnListener> enemyCallbacks = new ArrayList<>();

    /**
     * <p>
     * The shared {@link AggroListener} used to toggle the safe zone state.
     * </p>
     * <p>
     * When invoked:
     * <ul>
     * <li>If entering the safe zone: removes all registered enemies from the
     * game</li>
     * <li>If exiting the safe zone: re-adds enemies and triggers their respawn</li>
     * </ul>
     * </p>
     */
    final private AggroListener listener = () -> {
        if (this.isEnteringSafeZone) {
            this.handleSafeZoneEnter();
        } else {
            this.handleSafeZoneExit();
            this.resetEnemy();
        }
        this.toggleSafeZoneState();

    };

    /**
     * Registers a new {@link BaseEntrance} and returns the shared
     * {@link AggroListener}
     * to be assigned to the entrance.
     *
     * @param entrance the entrance to be added
     * @return the shared {@link AggroListener} that toggles all enemy engagement
     *         states
     */
    public AggroListener addEntrance(final BaseEntrance entrance) {
        this.entrances.add(entrance);
        return this.listener;
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
     * Triggers the respawn logic by calling {@link EnemyRespawnListener#respawn()}
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

    /**
     * <p>
     * Toggles the current safe zone state.
     * </p>
     * <p>
     * If the current state is "entering", it will become "exiting", and vice versa.
     * </p>
     */
    private void toggleSafeZoneState() {
        this.isEnteringSafeZone = !this.isEnteringSafeZone;
    }
}
