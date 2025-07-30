package it.unibo.falltohell.model.impl.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.listener.AttackListener;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
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

    /**
     * Represents the prefix used for different types of enemy timers.
     * <p>
     * Each enum constant stores a string prefix used to generate unique timer names
     * for actions like attack cycles or no-aggro periods.
     */
    private enum TimerPrefix {

        /** Prefix for "No Aggro" timers, used when enemies are temporarily passive. */
        NO_AGGRO("NoAggro_"),

        /** Prefix for "Attack" timers, used to schedule repeated enemy attacks. */
        ATTACK("Attack_");

        private final String prefix;

        /**
         * Creates a {@code TimerPrefix} with the given string prefix.
         *
         * @param prefix the string used as a prefix for timer names
         */
        TimerPrefix(final String prefix) {
            this.prefix = prefix;
        }

        /**
         * Returns the string prefix associated with this timer type.
         *
         * @return the string prefix
         */
        public String getPrefix() {
            return prefix;
        }
    }

    private long countNoAggro;
    private long countAttack;

    private final Map<Enemy, AttackListener> adjourn = new HashMap<>();
    private final Map<Enemy, List<String>> enemyTimers = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNoAggroTimer(final Enemy enemy) {
        final Level lv = enemy.getLevel();
        final long duration = ((BaseEnemyStatistics) enemy.getStats()).getNoAggro();
        final String name = getNextNoAggroName(enemy);
        final CustomTimerImpl timer = new CustomTimerImpl(duration, () -> {
            if (enemy.getStats().getLife() < enemy.getStats().getFullLife()) {
                final var stats = (BaseEnemyStatistics) enemy.getStats();
                final double life = stats.getLife();
                final double regenLife = life * stats.getRegen();
                final double fullLife = stats.getFullLife();
                if (life + regenLife > fullLife) {
                    stats.setLife(fullLife);
                } else if (life > 0) {
                    stats.addLife(regenLife);
                }
            }
            lv.getTimerManager().restartTimer(name);
        });

        lv.getTimerManager().addTimer(name, timer);
        this.registerTimer(enemy, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAttackTimer(final Enemy enemy, final Optional<AttackListener> listener) {
        listener.ifPresent(l -> this.adjourn.putIfAbsent(enemy, l));
        final Level lv = enemy.getLevel();
        final long duration = ((LongRangeEnemyStatistics) enemy.getStats()).getTimeAttack();
        final String name = getNextAttackName(enemy);
        final CustomTimerImpl timer = new CustomTimerImpl(duration, () -> {
            final AttackListener l = this.adjourn.get(enemy);
            if (l != null) {
                l.attack();
            }
            lv.getTimerManager().restartTimer(name);
        });

        lv.getTimerManager().addTimer(name, timer);
        this.registerTimer(enemy, name);
    }

    /**
     * Generates and registers a unique name for an enemy's "NoAggro" timer.
     *
     * @param enemy the enemy instance
     * @return a unique name for the "NoAggro" timer
     */
    private String getNextNoAggroName(final Enemy enemy) {
        return TimerPrefix.NO_AGGRO.getPrefix() + enemy.getClass().getSimpleName() + "_" + countNoAggro++;
    }

    /**
     * Generates and registers a unique name for an enemy's "Attack" timer.
     *
     * @param enemy the enemy instance
     * @return a unique name for the "Attack" timer
     */
    private String getNextAttackName(final Enemy enemy) {
        final String name = TimerPrefix.ATTACK.getPrefix() + enemy.getClass().getSimpleName() + "_" + countAttack++;
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
    public void removeTimersFor(final Enemy enemy) {
        final Level lv = enemy.getLevel();
        final List<String> timers = enemyTimers.remove(enemy);
        if (timers != null) {
            for (final String timer : timers) {
                if (lv.getTimerManager().searchTimer(timer)) {
                    lv.getTimerManager().removeTimer(timer);
                }
            }
        }
    }

    /**
     * Returns the prefix timer name associated with the given enemy.
     * If none is found, it creates the timer and retrieves it again.
     *
     * @param enemy the enemy instance to query
     * @param prefix to identify type of timer
     * @return the unique timer name for the no-aggro timer
     * @throws IllegalStateException if the timer could not be found or created
     */
    private String getTimerName(final Enemy enemy, final TimerPrefix prefix) {
        List<String> timers = enemyTimers.get(enemy);

        String timerName = findTimer(timers, prefix);
        if (timerName != null) {
            return timerName;
        }

        // Attempt to create the timer
        if (prefix.equals(TimerPrefix.NO_AGGRO)) {
            createNoAggroTimer(enemy);
        } else if (prefix.equals(TimerPrefix.ATTACK)) {
            createAttackTimer(enemy, Optional.empty());
        } else {
            throw new IllegalStateException("Enemy stats are not of type BaseEnemyStatistics or LongRangeStatistics");
        }

        timers = enemyTimers.get(enemy);
        timerName = findTimer(timers, prefix);
        if (timerName != null) {
            return timerName;
        }

        throw new IllegalStateException("NoAggro timer could not be found or created for enemy: " + enemy);
    }

    /**
     * Searches a list of timer names for one starting with a certain prefix.
     *
     * @param timers the list of timer names
     * @param prefix to find
     * @return the matching timer name, or null if not found
     */
    private String findTimer(final List<String> timers, final TimerPrefix prefix) {
        if (timers == null) {
            return null;
        }
        for (final String timer : timers) {
            if (timer.startsWith(prefix.getPrefix())) {
                return timer;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartEnemyTimer(final Enemy enemy, final BaseEnemy.TimerType type) {
        final Level lv = enemy.getLevel();
        final String name = switch (type) {
            case ATTACK -> getTimerName(enemy, TimerPrefix.ATTACK);
            case NO_AGGRO -> getTimerName(enemy, TimerPrefix.NO_AGGRO);
        };
        lv.getTimerManager().stopTimer(name);
        lv.getTimerManager().restartTimer(name);
    }
}
