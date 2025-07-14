package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.GroundEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.BaseEnemyStatisticsImpl;
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

    protected Vector2 position;
    protected Optional<Integer> noAggro = Optional.empty();
    protected Optional<Double> regen = Optional.empty();
    protected Optional<Double> senseDistance = Optional.empty();
    protected Optional<Map<BuffNames, Double>> buff= Optional.empty();
    protected Character character;
    protected long points;

    /**
     * {@inheritDoc}
     */
    @Override
    public T withPosition(Vector2 position) {
        this.position = position;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withNoAggro(Integer noAggro) {
        this.noAggro = Optional.ofNullable(noAggro);
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withRegen(Double regen) {
        this.regen = Optional.ofNullable(regen);
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withSenseDistance(Double senseDistance) {
        this.senseDistance = Optional.ofNullable(senseDistance);
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withCharacter(Character character) {
        this.character = character;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withPoints(long points) {
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
        return new BaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen,
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
}
