package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.listener.AttackListener;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy;

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
     * @param enemy    the enemy instance for which the timer is created
     */
    void createNoAggroTimer(Enemy enemy);

    /**
     * Creates and registers a "Attack" timer for the specified enemy within the given level.
     * <p>
     * The timer is used to handle when to shoot.
     * </p>
     *
     * @param enemy    the enemy instance for which the timer is created
     * @param listener needed to inform the enemy
     */
    void createAttackTimer(Enemy enemy, Optional<AttackListener> listener);

    /**
     * Removes all timers associated with the given enemy from the level's TimerManager.
     *
     * @param enemy the enemy whose timers should be removed
     */
    void removeTimersFor(Enemy enemy);

    /**
     * <p>
     * Restarts a specific type of timer for an enemy.
     * </p>
     *
     * <p>
     * Useful for resetting cooldowns (e.g., "ATTACK") or reapplying behavioral delays (e.g., "NO_AGGRO").
     * </p>
     *
     * @param enemy the {@link Enemy} whose timer should be restarted
     * @param type  the {@link BaseEnemy.TimerType} indicating which timer to restart
     *
     * @see BaseEnemy.TimerType
     */
    void restartEnemyTimer(Enemy enemy, BaseEnemy.TimerType type);
}
