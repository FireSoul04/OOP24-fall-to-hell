package it.unibo.falltohell.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.falltohell.model.api.EnemyFactory;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.EnemyTimerManager;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.EnemyTimeManagerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Centaur;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Imp;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Lotawiec;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.ManagerIngage;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.Tengu;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.AggroListener;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Concrete implementation of the {@link EnemyFactory} interface.
 * <p>
 * This class is responsible for creating specific {@link Enemy} instances,
 * such as {@link Centaur}, {@link Imp}, {@link Lotawiec} and {@link Tengu}.
 * </p>
 * <p>
 * This factory also ensures that each {@link Level} has exactly one
 * {@link EnemyTimerManager}, reused by all enemies within that level.
 * </p>
 *
 * @see Enemy
 * @see Centaur
 * @see Imp
 * @see Lotawiec
 * @see Tengu
 * @see Level
 * @see Character
 *
 * @author Sara Visani
 */
public class EnemyFactoryImpl implements EnemyFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createCentaur(final Level level, final Vector2 initialCords, final Character character) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        return new Centaur(level, initialCords, character, manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createTengu(final Level level, final Vector2 initialCords, final Character character) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        return new Tengu(level, initialCords, character, manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createImp(final Level level, final Vector2 initialCords, final Character character) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        return new Imp(level, initialCords, character, manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createLotawiec(final Level level, final Vector2 initialCords, final Character character) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        return new Lotawiec(level, initialCords, character, manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ManagerIngage askManager(final Level level){
        return ManagerHolder.getManagerIngageFor(level);
    }

    /**
     * Static nested utility class that holds a single {@link EnemyTimerManager}
     * per {@link Level}.
     * <p>
     * Ensures that all enemies within the same level share the same timer manager.
     * This avoids duplication and simplifies timer cleanup when the level ends.
     * </p>
     */
    private static final class ManagerHolder {
        private static final Map<Level, EnemyTimerManager> MANAGER_TIMER = new HashMap<>();
        private static final Map<Level, ManagerIngage> MANAGER_AGGRO = new HashMap<>();

        /**
         * Returns the {@link EnemyTimerManager} associated with the given level,
         * creating one if necessary.
         *
         * @param level the level for which to retrieve the timer manager
         * @return the shared timer manager for the level
         */
        static EnemyTimerManager getManagerTimerFor(final Level level) {
            return MANAGER_TIMER.computeIfAbsent(level, l -> new EnemyTimeManagerImpl());
        }

        static ManagerIngage getManagerIngageFor(final Level level) {
            return MANAGER_AGGRO.computeIfAbsent(level, l -> new ManagerIngage());
        }
    }
}
