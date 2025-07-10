package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public interface LongRangeEnemyStatBuilder{
    public LongRangeEnemyStatBuilder withProjectileVelocity(Vector2 projectileVelocity);

    public LongRangeEnemyStatBuilder withProjectileDimensions(Dimensions projectileDimensions);

    public LongRangeEnemyStatBuilder withTimeAttack(int timeAttack);

    public LongRangeEnemyStatistics build();
}
