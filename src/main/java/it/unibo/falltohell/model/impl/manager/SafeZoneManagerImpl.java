package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.falltohell.model.api.listener.EnemyRespawnListener;
import it.unibo.falltohell.model.api.listener.EnterSafeZoneListener;
import it.unibo.falltohell.model.api.listener.ExitSafeZoneListener;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;

/**
 * <p>
 * Implementation of the {@link SafeZoneManager} interface.
 * </p>
 *
 * <p>
 * This class manages the logic related to safe zones in the game. It
 * coordinates
 * engagement behavior for {@link Enemy} instances in response to triggers
 * from {@link BaseEntrance} objects.
 * </p>
 *
 * <p>
 * Functionality includes:
 * <ul>
 * <li>Registering entrances and assigning shared {@link EnterSafeZoneListener}
 * and
 * {@link ExitSafeZoneListener} instances</li>
 * <li>Removing enemies from the game when the player enters a safe zone</li>
 * <li>Re-adding and respawning enemies when the player exits the safe zone</li>
 * </ul>
 * </p>
 *
 * <p>
 * The manager also maintains a mapping between enemy classes and their sprite
 * file names, used to correctly re-link sprites after respawn.
 * </p>
 *
 * @author Sara Visani
 * @see SafeZoneManager
 * @see Enemy
 * @see BaseEntrance
 * @see EnemyRespawnListener
 */
public class SafeZoneManagerImpl implements SafeZoneManager {

    private final Set<BaseEntrance> entrances = new HashSet<>();
    private final Set<Enemy> enemies = new HashSet<>();
    private final List<EnemyRespawnListener> enemyCallbacks = new ArrayList<>();
    private final Map<Class<? extends Enemy>, String> fileNames = new HashMap<>();
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
     * {@inheritDoc}
     */
    @Override
    public void addEntrance(final BaseEntrance entrance) {
        this.entrances.add(entrance);
        entrance.setListenerEnter(entranceListener);
        entrance.setListenerExit(exitListener);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEnemy(final Enemy enemy, final String filename) {
        this.enemies.add(enemy);
        this.fileNames.putIfAbsent(enemy.getClass(), filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntrance(final BaseEntrance entrance) {
        this.entrances.remove(entrance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEnemy(final Enemy enemy) {
        this.enemies.remove(enemy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEnemyCall(final EnemyRespawnListener call) {
        this.enemyCallbacks.add(call);
    }

    /**
     * <p>
     * Triggers the respawn logic by calling {@link EnemyRespawnListener#respawn()}.
     * </p>
     */
    private void resetEnemy() {
        this.enemyCallbacks.forEach(EnemyRespawnListener::respawn);
    }

    /**
     * <p>
     * Removes all registered enemies from the game world (safe zone entry).
     * </p>
     */
    private void handleSafeZoneEnter() {
        this.enemies.forEach(enemy -> enemy.getLevel().removeGameObject(enemy));
        this.resetEnemy();
    }

    /**
     * <p>
     * Re-adds all registered enemies to the game world (safe zone exit).
     * </p>
     */
    private void handleSafeZoneExit() {
        for (final Enemy enemy : enemies) {
            enemy.getLevel().addGameObject(enemy);

            final String spriteFile = fileNames.get(enemy.getClass());
            if (spriteFile == null) {
                throw new IllegalStateException(
                        "No sprite file registered for enemy class: " + enemy.getClass().getSimpleName());
            }

            enemy.getLevel()
                    .getDrawableRenderableHandler()
                    .linkSprite(enemy.getDrawable().orElseThrow(), spriteFile);
        }
    }
}
