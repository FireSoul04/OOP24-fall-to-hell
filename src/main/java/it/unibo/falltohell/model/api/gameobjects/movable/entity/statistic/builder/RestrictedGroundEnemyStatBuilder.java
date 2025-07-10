package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;

public interface RestrictedGroundEnemyStatBuilder extends GroundEnemyStatBuilder<RestrictedGroundEnemyStatBuilder>{
    public RestrictedGroundEnemyStatBuilder withDistance(double distance);

    @Override
    public RestrictedBaseEnemyStatistics build();
}
