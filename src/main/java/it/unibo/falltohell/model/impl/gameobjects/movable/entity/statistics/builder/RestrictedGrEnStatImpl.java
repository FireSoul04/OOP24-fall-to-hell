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
    public RestrictedGroundEnemyStatBuilder withDistance(final double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedBaseEnemyStatistics build() {
        final var life = super.getLife();
        final var attack = super.getAttack();
        final var speed = super.getSpeed();
        final var dimension = super.getDimensions();
        final var position = super.getInitialPos();
        final var noAggro = super.getNoAggro();
        final var character = super.getCharacter();
        final var regen = super.getRegen();
        final var senseDistance = super.getSenseDistance();
        final var points = super.getPoints();
        final var buff = super.getBuffMap();

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
