package it.unibo.falltohell.test.util;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy;
import it.unibo.falltohell.model.impl.manager.EnemyTimeManagerImpl;
import it.unibo.falltohell.model.impl.manager.SafeZoneManager;
import it.unibo.falltohell.util.Vector2;

public class DummyEnemyTest extends BaseEnemy {

    /**
     * Creates a dummy that does nothing.
     * @param level where it belongs
     * @param stats of the enemy
     */
    public DummyEnemyTest(final Level level, final Vector2 position, final BaseEnemyStatistics stats) {
        super(level, stats, new EnemyTimeManagerImpl(), new SafeZoneManager(), "test.png");
        this.setPosition(position);
    }

    /**
     * Does nothing.
     */
    @Override
    public void update(final double deltaTime) {

    }

    /**
     * {@inheritDoc}
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
     */
    @Override
    protected void move(final double deltaTime) {

    }

    @Override
    protected void patrol(Vector2 currentPos, Vector2 speed) {

    }

    @Override
    protected void chase(Vector2 charaPos, Vector2 currentPos, Vector2 speed) {
       
    }
}
