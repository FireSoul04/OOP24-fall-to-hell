package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;

public interface RestrictedLongRangeEnemyStatBuilder extends LongRangeEnemyStatBuilder<RestrictedLongRangeEnemyStatBuilder>{
    public RestrictedLongRangeEnemyStatBuilder withDistance(double distance);

    @Override
    public RestrictedLongRangeEnemyStatistics build();
}
