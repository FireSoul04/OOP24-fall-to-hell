package it.unibo.falltohell.model.impl.builder;

import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.builder.LongRangeEnemyStatBuilder;
import it.unibo.falltohell.model.impl.statistics.LongRangedEnemyStatisticsImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of the {@link LongRangeEnemyStatBuilder} for building
 * long-range enemy statistics.
 * Extends {@link GroundEnemyStatBuilderImpl} to inherit common enemy statistic
 * builder properties.
 *
 * @param <T> the concrete builder type for fluent interface support
 *
 * @author Sara Visani
 */
public class LongRangeStatBuilderImpl<T extends LongRangeStatBuilderImpl<T>> extends GroundEnemyStatBuilderImpl<T>
        implements LongRangeEnemyStatBuilder {

    private double projectileAttack;
    private Vector2 projectileVelocity;
    private Dimensions projectileDimensions;
    private int timeAttack;

    /**
     * {@inheritDoc}
     */
    @Override
    public T withProjectileAttack(final double projectileAttack) {
        this.projectileAttack = projectileAttack;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withProjectileVelocity(final Vector2 projectileVelocity) {
        this.projectileVelocity = projectileVelocity;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withProjectileDimensions(final Dimensions projectileDimensions) {
        this.projectileDimensions = projectileDimensions;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withTimeAttack(final int timeAttack) {
        this.timeAttack = timeAttack;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LongRangeEnemyStatistics build() {
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

        return new LongRangedEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, regen,
                senseDistance, points, projectileAttack, projectileVelocity, projectileDimensions, timeAttack, buff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProjectileAttack() {
        return this.projectileAttack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getProjectileSpeed() {
        return this.projectileVelocity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimensions getProjectileDimensions() {
        return this.projectileDimensions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeAttack() {
        return this.timeAttack;
    }
}
