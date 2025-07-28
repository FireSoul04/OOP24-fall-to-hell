package it.unibo.falltohell.model.impl.builder;

import it.unibo.falltohell.model.api.builder.RestrictedLongRangeEnemyStatBuilder;
import it.unibo.falltohell.model.impl.statistics.RestrictedLongRangeEnemyStatisticsImpl;

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
    public RestrictedLongRangeBuilderImpl withDistance(final double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedLongRangeEnemyStatisticsImpl build() {
        final var life = super.getLife();
        final var attack = super.getAttack();
        final var speed = super.getSpeed();
        final var dimension = super.getDimensions();
        final var position = super.getInitialPos();
        final var noAggro = super.getNoAggro();
        final var regen = super.getRegen();
        final var senseDistance = super.getSenseDistance();
        final var points = super.getPoints();
        final var buff = super.getBuffMap();
        final var projectileAttack = super.getProjectileAttack();
        final var projectileVelocity = super.getProjectileSpeed();
        final var projectileDimensions = super.getProjectileDimensions();
        final var timeAttack = super.getTimeAttack();

        return new RestrictedLongRangeEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro,
                regen, senseDistance, points, projectileAttack, projectileVelocity, projectileDimensions, distance,
                timeAttack, buff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RestrictedLongRangeBuilderImpl self() {
        return this;
    }
}
