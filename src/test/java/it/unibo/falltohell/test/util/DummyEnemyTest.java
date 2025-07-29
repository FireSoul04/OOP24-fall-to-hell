package it.unibo.falltohell.test.util;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy;
import it.unibo.falltohell.model.impl.manager.EnemyTimeManagerImpl;
import it.unibo.falltohell.model.impl.manager.SafeZoneManagerImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Creates an enemy that is used for tests.
 */
public class DummyEnemyTest extends BaseEnemy {

    /**
     * Creates a dummy that does nothing.
     * @param level where it belongs
     * @param stats of the enemy
     */
    public DummyEnemyTest(final Level level, final BaseEnemyStatistics stats) {
        super(level, stats, new EnemyTimeManagerImpl(), new SafeZoneManagerImpl(), "test.png");
    }

    /**
     * Does nothing.
     * @param deltaTime unused
     */
    @Override
    public void update(final double deltaTime) {

    }

    /**
     * Does nothing by default.
     * @param other unused
     * @param direction unused
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {

    }

    /**
     * Does not attack.
     */
    @Override
    protected void attack() {

    }

    /**
     * Does not move.
     * @param deltaTime unused
     */
    @Override
    protected void move(final double deltaTime) {

    }

    /**
     * Does nothing.
     * @param currentPos unused
     * @param speed unused
     */
    @Override
    protected void patrol(final Vector2 currentPos, final Vector2 speed) {

    }

    /**
     * Does nothing.
     * @param charaPos unused
     * @param currentPos unused
     * @param speed unused
     */
    @Override
    protected void chase(final Vector2 charaPos, final Vector2 currentPos, final Vector2 speed) {

    }
}
