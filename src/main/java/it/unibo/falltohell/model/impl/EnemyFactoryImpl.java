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
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Concrete implementation of the {@link EnemyFactory} interface.
 * <p>
 * Responsible for creating specific enemy instances ({@link Centaur},
 * {@link Imp},
 * {@link Lotawiec}, {@link Tengu}) tied to a given level and player character.
 * <p>
 * Each {@link Level} is associated with a single shared
 * {@link EnemyTimerManager}
 * and a {@link ManagerIngage} to manage timers and enemy aggro state
 * respectively.
 * This sharing avoids duplication and eases management of enemy-related
 * resources.
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
        final ManagerIngage ingage = ManagerHolder.getManagerIngageFor(level);
        return new Centaur(level, initialCords, character, manager, ingage, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createTengu(final Level level, final Vector2 initialCords, final Character character) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        final ManagerIngage ingage = ManagerHolder.getManagerIngageFor(level);
        return new Tengu(level, initialCords, character, manager, ingage, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createImp(final Level level, final Vector2 initialCords, final Character character) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        final ManagerIngage ingage = ManagerHolder.getManagerIngageFor(level);
        return new Imp(level, initialCords, character, manager, ingage, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createLotawiec(final Level level, final Vector2 initialCords, final Character character) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        final ManagerIngage ingage = ManagerHolder.getManagerIngageFor(level);
        return new Lotawiec(level, initialCords, character, manager, ingage, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ManagerIngage askManager(final Level level) {
        return ManagerHolder.getManagerIngageFor(level);
    }

    /**
     * Static nested utility class that manages shared instances of
     * {@link EnemyTimerManager} and {@link ManagerIngage} per {@link Level}.
     * <p>
     * Ensures that all enemies within the same level share the same timer manager
     * and aggro manager. This approach avoids redundant object creation and
     * simplifies management and cleanup of timers and aggro states when the level
     * ends.
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

        /**
         * Retrieves the {@link ManagerIngage} associated with the specified level,
         * creating a new one if it does not already exist.
         *
         * @param level the level for which to retrieve the aggro manager
         * @return the shared {@link ManagerIngage} instance for the level
         */
        static ManagerIngage getManagerIngageFor(final Level level) {
            return MANAGER_AGGRO.computeIfAbsent(level, l -> new ManagerIngage());
        }
    }
}
