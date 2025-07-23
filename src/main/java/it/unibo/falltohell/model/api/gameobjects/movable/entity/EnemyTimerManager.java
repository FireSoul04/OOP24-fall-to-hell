package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;

/**
 * Interface for managing enemy timer counters and their unique timer names.
 * <p>
 * Responsible for generating unique timer names for enemies and
 * managing the removal of timers when enemies are removed from the level.
 * </p>
 *
 * @author Sara Visani
 */
public interface EnemyTimerManager {

    /**
     * Creates and registers a "NoAggro" timer for the specified enemy within the given level.
     * <p>
     * The timer is used to handle regeneration or related behaviors when the enemy is not aggressive.
     * </p>
     *
     * @param level    the level to which the enemy belongs, used to access the TimerManager
     * @param enemy    the enemy instance for which the timer is created
     * @param duration the duration in milliseconds of the timer
     */
    void createNoAggroTimer(Level level, Enemy enemy, long duration);

    /**
     * Generates and registers a unique name for an enemy's "Attack" timer.
     *
     * @param enemy the enemy instance
     * @return a unique name for the "Attack" timer
     */
    String getNextAttackName(Enemy enemy);

    /**
     * Removes all timers associated with the given enemy from the level's TimerManager.
     *
     * @param enemy the enemy whose timers should be removed
     * @param level the level to access the TimerManager
     */
    void removeTimersFor(Enemy enemy, Level level);

    /**
     * <p>
     * Restarts a specific type of timer for an enemy.
     * </p>
     *
     * <p>
     * Useful for resetting cooldowns (e.g., "ATTACK") or reapplying behavioral delays (e.g., "NO_AGGRO").
     * </p>
     *
     * @param level the {@link Level} to access the enemy's {@code TimerManager}
     * @param enemy the {@link Enemy} whose timer should be restarted
     * @param type  the {@link BaseEnemy.TimerType} indicating which timer to restart
     *
     * @see BaseEnemy.TimerType
     */
    void restartEnemyTimer(Level level, Enemy enemy, BaseEnemy.TimerType type);
}
