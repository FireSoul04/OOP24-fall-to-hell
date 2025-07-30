package it.unibo.falltohell.test.util.debug.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.listener.EnemyRespawnListener;
import it.unibo.falltohell.model.api.listener.EnterSafeZoneListener;
import it.unibo.falltohell.model.api.listener.ExitSafeZoneListener;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;

/**
 * A debug implementation of {@link SafeZoneManager} for handling safe zones
 * during testing.
 * <p>
 * This class manages entrances, enemies, and callbacks when a character enters
 * or exits
 * a safe zone. On entering, all enemies are removed and respawned; on exiting,
 * enemies
 * are re-added and their sprites linked.
 * </p>
 *
 * @author Sara Visani
 */
public class SafeZoneManagerDebug implements SafeZoneManager {

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
    public void resetEnemy() {
        this.enemyCallbacks.forEach(EnemyRespawnListener::respawn);
    }

    /**
     * <p>
     * Removes all registered enemies from the game world (safe zone entry).
     * </p>
     */
    public void handleSafeZoneEnter() {
        this.enemies.forEach(enemy -> enemy.getLevel().removeGameObject(enemy));
        this.resetEnemy();
    }

    /**
     * <p>
     * Re-adds all registered enemies to the game world (safe zone exit).
     * </p>
     */
    public void handleSafeZoneExit() {
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

    /**
     * Returns the set of entrances associated with this object.
     *
     * @return a {@link Set} of {@link BaseEntrance} instances
     */
    public Set<BaseEntrance> getEntrances() {
        return  Set.copyOf(entrances);
    }

    /**
     * Returns the set of enemies currently managed.
     *
     * @return a {@link Set} of {@link Enemy} instances
     */
    public Set<Enemy> getEnemies() {
        return Set.copyOf(enemies);
    }

    /**
     * Returns the list of registered enemy respawn callbacks.
     *
     * @return a {@link List} of {@link EnemyRespawnListener} instances
     */
    public List<EnemyRespawnListener> getEnemyCallbacks() {
        return List.copyOf(enemyCallbacks);
    }

    /**
     * Returns the mapping of enemy classes to their corresponding file names.
     *
     * @return a {@link Map} where keys are enemy classes and values are file names
     */
    public Map<Class<? extends Enemy>, String> getFileNames() {
        return Map.copyOf(fileNames);
    }

    /**
     * Checks whether the first entrance event has already occurred.
     *
     * @return {@code true} if the first entrance has happened; {@code false}
     *         otherwise
     */
    public boolean isHasFirstEntered() {
        return hasFirstEntered;
    }

}
