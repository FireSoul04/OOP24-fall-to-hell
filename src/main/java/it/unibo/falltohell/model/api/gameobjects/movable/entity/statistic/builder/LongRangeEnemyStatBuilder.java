package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public interface LongRangeEnemyStatBuilder<T extends  LongRangeEnemyStatBuilder<T>> extends GroundEnemyStatBuilder<T>{
    public T withProjectileVelocity(Vector2 projectileVelocity);

    public T withProjectileDimensions(Dimensions projectileDimensions);

    public T withTimeAttack(int timeAttack);

    @Override
    public LongRangeEnemyStatistics build();
}
