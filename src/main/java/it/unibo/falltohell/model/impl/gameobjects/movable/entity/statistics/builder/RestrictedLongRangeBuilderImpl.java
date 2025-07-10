package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.RestrictedLongRangeEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.RestrictedLongRangeEnemyStatisticsImpl;

public class RestrictedLongRangeBuilderImpl extends LongRangeStatBuilderImpl<RestrictedLongRangeBuilderImpl> implements RestrictedLongRangeEnemyStatBuilder {
    private double distance;

    public RestrictedLongRangeBuilderImpl withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public RestrictedLongRangeEnemyStatisticsImpl build() {
        return new RestrictedLongRangeEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen, senseDistance, points, projectileAttack, projectileVelocity, projectileDimensions, distance, timeAttack);
    }

    @Override
    protected RestrictedLongRangeBuilderImpl self() {
        return (RestrictedLongRangeBuilderImpl) this;
    }
}
