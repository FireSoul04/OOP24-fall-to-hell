package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.RestrictedLongRangeEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.RestrictedLongRangeEnemyStatisticsImpl;

/**
 * Builder implementation for {@link RestrictedLongRangeEnemyStatisticsImpl}.
 * Extends {@link LongRangeStatBuilderImpl} to provide additional configuration
 * for restricted long range enemy statistics.
 * Implements the {@link RestrictedLongRangeEnemyStatBuilder} interface.
 *
 * <p>
 * This builder adds support for setting the distance parameter specific to
 * restricted long range enemies.
 * </p>
 *
 * @author Sara Visani
 */
public class RestrictedLongRangeBuilderImpl extends LongRangeStatBuilderImpl<RestrictedLongRangeBuilderImpl>
        implements RestrictedLongRangeEnemyStatBuilder {
    private double distance;

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedLongRangeBuilderImpl withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedLongRangeEnemyStatisticsImpl build() {
        return new RestrictedLongRangeEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character,
                regen, senseDistance, points, projectileAttack, projectileVelocity, projectileDimensions, distance,
                timeAttack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RestrictedLongRangeBuilderImpl self() {
        return (RestrictedLongRangeBuilderImpl) this;
    }
}
