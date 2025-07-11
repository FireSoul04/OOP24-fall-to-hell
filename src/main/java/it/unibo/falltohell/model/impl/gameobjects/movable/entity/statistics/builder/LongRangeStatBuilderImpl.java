package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.LongRangeEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.LongRangedEnemyStatisticsImpl;
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

    protected double projectileAttack;
    protected Vector2 projectileVelocity;
    protected Dimensions projectileDimensions;
    protected int timeAttack;

    /**
     * {@inheritDoc}
     */
    @Override
    public T withProjectileAttack(double projectileAttack) {
        this.projectileAttack = projectileAttack;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withProjectileVelocity(Vector2 projectileVelocity) {
        this.projectileVelocity = projectileVelocity;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withProjectileDimensions(Dimensions projectileDimensions) {
        this.projectileDimensions = projectileDimensions;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withTimeAttack(int timeAttack) {
        this.timeAttack = timeAttack;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LongRangeEnemyStatistics build() {
        return new LongRangedEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen,
                senseDistance, points, projectileAttack, projectileVelocity, projectileDimensions, timeAttack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}
