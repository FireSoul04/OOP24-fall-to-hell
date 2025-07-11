package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.StatisticBuilder;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of the {@link StatisticBuilder} interface providing
 * builder methods to set character statistics such as life, attack, speed,
 * and dimensions.
 * <p>
 * Uses a fluent interface pattern to allow method chaining.
 *
 * @param <T> the concrete builder type extending this class for fluent returns
 *
 * @see StatisticBuilder
 *
 * @author Sara Visani
 */
public class StatisticBuilderImpl<T extends StatisticBuilderImpl<T>> implements StatisticBuilder {

    protected double life;
    protected double attack;
    protected Vector2 speed;
    protected Dimensions dimension;

    /**
     * {@inheritDoc}
     */
    @Override
    public T withLife(double life) {
        this.life = life;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withAttack(double attack) {
        this.attack = attack;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withSpeed(Vector2 speed) {
        this.speed = speed;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withDimensions(Dimensions dimensions) {
        this.dimension = dimensions;
        return self();
    }

    /**
     * Returns this builder instance cast to the concrete type.
     * <p>
     *
     * @return this builder instance of type {@code T}
     */
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}
