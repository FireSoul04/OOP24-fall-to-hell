package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;

public interface RestrictedLongRangeEnemyStatBuilder{
    public RestrictedLongRangeEnemyStatBuilder withDistance(double distance);

    public RestrictedLongRangeEnemyStatistics build();
}
