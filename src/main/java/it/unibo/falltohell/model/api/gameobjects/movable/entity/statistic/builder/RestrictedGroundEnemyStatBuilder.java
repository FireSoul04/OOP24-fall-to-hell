package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;

public interface RestrictedGroundEnemyStatBuilder{
    public RestrictedGroundEnemyStatBuilder withDistance(double distance);

    public RestrictedBaseEnemyStatistics build();
}
