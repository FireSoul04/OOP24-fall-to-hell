package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.EnemyTimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.CustomTimerImpl;

public class EnemyTimeManagerImpl implements EnemyTimerManager {

    private long countNoAggro = 0;
    private long countAttack = 0;

    private final Map<Enemy, List<String>> enemyTimers = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNoAggroTimer(final Level level, final Enemy enemy, final long duration) {
        final BaseEnemy health = (BaseEnemy) enemy;
        final String name = getNextNoAggroName(enemy);
        final CustomTimerImpl timer = new CustomTimerImpl(duration, () -> {
            if (health.isFull()) {
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
        registerTimer(enemy, name);
    }

    /**
     * Generates and registers a unique name for an enemy's "NoAggro" timer.
     *
     * @param enemy the enemy instance
     * @return a unique name for the "NoAggro" timer
     */
    private String getNextNoAggroName(final Enemy enemy) {
        String name = "NoAggro_" + enemy.getClass().getSimpleName() + "_" + countNoAggro++;
        registerTimer(enemy, name);
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNextAttackName(final Enemy enemy) {
        String name = "Attack_" + enemy.getClass().getSimpleName() + "_" + countAttack++;
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
            for (String timer : timers) {
                level.getTimerManager().removeTimer(timer);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNoAggroTimerName(final Enemy enemy) {
        List<String> timers = enemyTimers.get(enemy);
        if (timers != null) {
            for (String timerName : timers) {
                if (timerName.startsWith("NoAggro_")) {
                    return timerName;
                }
            }
        }
        throw new IllegalStateException("No NoAggro timer found for enemy: " + enemy);
    }

}
