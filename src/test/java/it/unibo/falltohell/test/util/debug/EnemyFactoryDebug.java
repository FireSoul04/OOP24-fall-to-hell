package it.unibo.falltohell.test.util.debug;

import java.util.HashMap;
import java.util.Map;

import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;
import it.unibo.falltohell.model.impl.factory.EnemyFactoryImpl;
import it.unibo.falltohell.test.util.debug.enemy.CentaurDebug;
import it.unibo.falltohell.test.util.debug.enemy.ImpDebug;
import it.unibo.falltohell.test.util.debug.enemy.LotawiecDebug;
import it.unibo.falltohell.test.util.debug.enemy.TenguDebug;
import it.unibo.falltohell.test.util.debug.manager.EnemyTimerManagerDebug;
import it.unibo.falltohell.test.util.debug.manager.SafeZoneManagerDebug;
import it.unibo.falltohell.util.Vector2;

/**
 * A debug factory that creates specialized enemy instances for testing.
 * <p>
 * Overrides the standard {@link EnemyFactoryImpl} methods to return
 * debug versions of enemies (e.g., {@link CentaurDebug}, {@link TenguDebug},
 * {@link ImpDebug}, {@link LotawiecDebug}) which integrate with
 * {@link EnemyTimerManagerDebug} and {@link SafeZoneManagerDebug}.
 * </p>
 * <p>
 * Enemies created within the same {@link Level} share a single timer manager
 * and safe-zone manager, managed by the nested {@link ManagerHolder} class.
 * </p>
 *
 * @author Sara Visani
 */
public class EnemyFactoryDebug extends EnemyFactoryImpl {

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createCentaur(final Level level, final Vector2 initialCords) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        final SafeZoneManager safeZoneManager = ManagerHolder.getSafeZoneManagerFor(level);
        return new CentaurDebug(level, initialCords, manager, safeZoneManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createTengu(final Level level, final Vector2 initialCords) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        final SafeZoneManager safeZoneManager = ManagerHolder.getSafeZoneManagerFor(level);
        return new TenguDebug(level, initialCords, manager, safeZoneManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createImp(final Level level, final Vector2 initialCords) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        final SafeZoneManager safeZoneManager = ManagerHolder.getSafeZoneManagerFor(level);
        return new ImpDebug(level, initialCords, manager, safeZoneManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enemy createLotawiec(final Level level, final Vector2 initialCords) {
        final EnemyTimerManager manager = ManagerHolder.getManagerTimerFor(level);
        final SafeZoneManager safeZoneManager = ManagerHolder.getSafeZoneManagerFor(level);
        return new LotawiecDebug(level, initialCords, manager, safeZoneManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SafeZoneManager askManager(final Level level) {
        return ManagerHolder.getSafeZoneManagerFor(level);
    }

    /**
     * Static nested utility class that manages shared instances of
     * {@link EnemyTimerManager} and {@link SafeZoneManager} per {@link Level}.
     * <p>
     * Ensures that all enemies within the same level share the same timer manager
     * and aggro manager. This approach avoids redundant object creation and
     * simplifies management and cleanup of timers and aggro states when the level
     * ends.
     * </p>
     */
    private static final class ManagerHolder {
        private static final Map<Level, EnemyTimerManager> MANAGER_TIMER = new HashMap<>();
        private static final Map<Level, SafeZoneManager> MANAGER_SAFE_ZONE = new HashMap<>();

        /**
         * Returns the {@link EnemyTimerManager} associated with the given level,
         * creating one if necessary.
         *
         * @param level the level for which to retrieve the timer manager
         * @return the shared timer manager for the level
         */
        static EnemyTimerManager getManagerTimerFor(final Level level) {
            return MANAGER_TIMER.computeIfAbsent(level, l -> new EnemyTimerManagerDebug());
        }

        /**
         * Retrieves the {@link SafeZoneManager} associated with the specified level,
         * creating a new one if it does not already exist.
         *
         * @param level the level for which to retrieve the aggro manager
         * @return the shared {@link SafeZoneManager} instance for the level
         */
        static SafeZoneManager getSafeZoneManagerFor(final Level level) {
            return MANAGER_SAFE_ZONE.computeIfAbsent(level, l -> new SafeZoneManagerDebug());
        }
    }
}
