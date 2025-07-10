package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.RestrictedGroundEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.RestrictedBaseEnemyStatisticsImpl;

public class RestrictedGrEnStatImpl extends GroundEnemyStatBuilderImpl<RestrictedGrEnStatImpl> implements RestrictedGroundEnemyStatBuilder{
    private double distance;

    @Override
    public RestrictedGroundEnemyStatBuilder withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public RestrictedBaseEnemyStatistics build() {
        return new RestrictedBaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen, senseDistance, points, distance);
    }

    @Override
    protected RestrictedGrEnStatImpl self() {
        return (RestrictedGrEnStatImpl) this;
    }
}
