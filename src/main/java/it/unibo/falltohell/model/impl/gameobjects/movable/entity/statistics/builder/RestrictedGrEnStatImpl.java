package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.RestrictedGroundEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.RestrictedBaseEnemyStatisticsImpl;

/**
 * Implementation of the {@link RestrictedGroundEnemyStatBuilder} interface,
 * extending {@link GroundEnemyStatBuilderImpl} with additional property
 * {@code distance}.
 * Builds {@link RestrictedBaseEnemyStatistics} instances with restricted ground
 * enemy stats.
 *
 * @author Sara Visani
 */
public class RestrictedGrEnStatImpl extends GroundEnemyStatBuilderImpl<RestrictedGrEnStatImpl>
        implements RestrictedGroundEnemyStatBuilder {
    private double distance;

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedGroundEnemyStatBuilder withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedBaseEnemyStatistics build() {
        return new RestrictedBaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character,
                regen, senseDistance, points, distance, buff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RestrictedGrEnStatImpl self() {
        return (RestrictedGrEnStatImpl) this;
    }
}
