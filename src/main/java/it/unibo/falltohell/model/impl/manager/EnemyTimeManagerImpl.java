package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;

/**
 * Implementation of the enemy timer manager.
 * <p>
 * Responsible for generating unique timer names for enemies and managing the
 * removal of timers when enemies are removed from the level.
 * </p>
 *
 * @see EnemyTimerManager
 * @author Sara Visani
 */
public class EnemyTimeManagerImpl implements EnemyTimerManager {

    private long countNoAggro;
    private long countAttack;

    private final Map<Enemy, List<String>> enemyTimers = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNoAggroTimer(final Level level, final Enemy enemy, final long duration) {
        final String name = getNextNoAggroName(enemy);
        final CustomTimerImpl timer = new CustomTimerImpl(duration, () -> {
            if (enemy.getStats().getLife() < enemy.getStats().getFullLife()) {
                final var stats = (BaseEnemyStatistics) enemy.getStats();
                final double life = stats.getLife();
                final double regenLife = life * stats.getRegen();
                final double fullLife = stats.getFullLife();
                if (life + regenLife > fullLife) {
                    stats.setLife(fullLife);
                } else {
                    stats.addLife(regenLife);
                }
            }
            level.getTimerManager().restartTimer(name);
        });

        level.getTimerManager().addTimer(name, timer);
        this.registerTimer(enemy, name);
    }

    /**
     * Generates and registers a unique name for an enemy's "NoAggro" timer.
     *
     * @param enemy the enemy instance
     * @return a unique name for the "NoAggro" timer
     */
    private String getNextNoAggroName(final Enemy enemy) {
        final String name = "NoAggro_" + enemy.getClass().getSimpleName() + "_" + countNoAggro++;
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNextAttackName(final Enemy enemy) {
        final String name = "Attack_" + enemy.getClass().getSimpleName() + "_" + countAttack++;
        registerTimer(enemy, name);
        return name;
    }

    /**
     * Registers a timer name for a specific enemy instance.
     * <p>
     * This method associates the given timer name with the enemy in a map,
     * ensuring that each enemy can have multiple timers tracked.
     * If the enemy does not already have a list of timers, a new list is created.
     * </p>
     *
     * @param enemy     the enemy instance to associate the timer with
     * @param timerName the unique name of the timer to register
     */
    private void registerTimer(final Enemy enemy, final String timerName) {
        enemyTimers.computeIfAbsent(enemy, e -> new ArrayList<>()).add(timerName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTimersFor(final Enemy enemy, final Level level) {
        final List<String> timers = enemyTimers.remove(enemy);
        if (timers != null) {
            for (final String timer : timers) {
                level.getTimerManager().removeTimer(timer);
            }
        }
    }

    /**
     * Returns the "NoAggro" timer name associated with the given enemy.
     * If none is found, it creates the timer and retrieves it again.
     *
     * @param enemy the enemy instance to query
     * @return the unique timer name for the no-aggro timer
     * @throws IllegalStateException if the timer could not be found or created
     */
    private String getNoAggroTimerName(final Enemy enemy) {
        List<String> timers = enemyTimers.get(enemy);

        String timerName = findNoAggroTimer(timers);
        if (timerName != null) {
            return timerName;
        }

        // Attempt to create the timer
        if (enemy.getStats() instanceof BaseEnemyStatistics stats) {
            createNoAggroTimer(enemy.getLevel(), enemy, stats.getNoAggro());
        } else {
            throw new IllegalStateException("Enemy stats are not of type BaseEnemyStatistics");
        }

        timers = enemyTimers.get(enemy);
        timerName = findNoAggroTimer(timers);
        if (timerName != null) {
            return timerName;
        }

        throw new IllegalStateException("NoAggro timer could not be found or created for enemy: " + enemy);
    }

    /**
     * Searches a list of timer names for one starting with "NoAggro_".
     *
     * @param timers the list of timer names
     * @return the matching timer name, or null if not found
     */
    private String findNoAggroTimer(final List<String> timers) {
        if (timers == null){
            return null;
        }
        for (final String timer : timers) {
            if (timer.startsWith("NoAggro_")) {
                return timer;
            }
        }
        return null;
    }

    /**
     * Returns the "Attack" timer name associated with the given enemy.
     *
     * @param enemy the enemy instance to query
     * @return the unique timer name for the attack timer
     * @throws IllegalStateException if no timer is found for the enemy
     */
    private String getAttackTimerName(final Enemy enemy) {
        final List<String> timers = enemyTimers.get(enemy);
        if (timers != null) {
            for (final String timerName : timers) {
                if (timerName.startsWith("Attack_")) {
                    return timerName;
                }
            }
        }
        throw new IllegalStateException("No attack timer found for enemy: " + enemy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartEnemyTimer(final Level level, final Enemy enemy, final BaseEnemy.TimerType type) {
        final String name;
        switch (type) {
            case ATTACK -> name = getAttackTimerName(enemy);
            case NO_AGGRO -> name = getNoAggroTimerName(enemy);
            default -> throw new IllegalArgumentException("Unknown timer type: " + type);
        }
        level.getTimerManager().stopTimer(name);
        level.getTimerManager().restartTimer(name);
    }
}
