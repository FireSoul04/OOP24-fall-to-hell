package it.unibo.falltohell.model.impl.builder;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.builder.GroundEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.statistics.BaseEnemyStatisticsImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of {@link GroundEnemyStatBuilder} for building
 * {@link BaseEnemyStatistics} for ground enemies.
 * <p>
 * Provides methods to set position, aggression behavior, regeneration,
 * sensing distance, character reference, and points before building the stats.
 *
 * @param <T> the type of the builder for fluent method chaining
 *
 * @author Sara Visani
 */
public class GroundEnemyStatBuilderImpl<T extends GroundEnemyStatBuilderImpl<T>> extends StatisticBuilderImpl<T>
        implements GroundEnemyStatBuilder {

    private Vector2 position;
    private Optional<Integer> noAggro = Optional.empty();
    private Optional<Double> regen = Optional.empty();
    private Optional<Double> senseDistance = Optional.empty();
    private Optional<Map<BuffNames, Double>> buff = Optional.empty();
    private long points;

    /**
     * {@inheritDoc}
     */
    @Override
    public T withPosition(final Vector2 position) {
        this.position = position;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withNoAggro(final Integer noAggro) {
        this.noAggro = Optional.ofNullable(noAggro);
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withRegen(final Double regen) {
        this.regen = Optional.ofNullable(regen);
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withSenseDistance(final Double senseDistance) {
        this.senseDistance = Optional.ofNullable(senseDistance);
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withPoints(final long points) {
        this.points = points;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withBuff(final Map<BuffNames, Double> buff) {
        this.buff = Optional.ofNullable(buff);
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseEnemyStatistics build() {

        final var life = super.getLife();
        final var attack = super.getAttack();
        final var speed = super.getSpeed();
        final var dimension = super.getDimensions();

        return new BaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, regen,
                senseDistance, points, buff);
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
    public Vector2 getInitialPos() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getNoAggro() {
        return this.noAggro;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Double> getRegen() {
        return this.regen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Double> getSenseDistance() {
        return this.senseDistance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getPoints() {
        return this.points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Map<BuffNames, Double>> getBuffMap() {
        return this.buff;
    }
}
