package it.unibo.falltohell.model.impl.builder;

import it.unibo.falltohell.model.api.builder.StatisticBuilder;
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

    private double life;
    private double attack;
    private Vector2 speed;
    private Dimensions dimension;

    /**
     * {@inheritDoc}
     */
    @Override
    public T withLife(final double life) {
        this.life = life;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withAttack(final double attack) {
        this.attack = attack;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withSpeed(final Vector2 speed) {
        this.speed = speed;
        return self();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T withDimensions(final Dimensions dimensions) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLife() {
        return this.life;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAttack() {
        return this.attack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getSpeed() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimensions getDimensions() {
        return this.dimension;
    }
}
