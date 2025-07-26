package it.unibo.falltohell.model.impl.builder;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.builder.ParamBuilderOptional;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;

/**
 * Implementation of {@link ParamBuilderOptional} interface,
 * providing optional parameters for character statistics building.
 * <p>
 * This class stores optional values like noAggro, regen, and senseDistance
 * wrapped in {@link Optional} to indicate their possible absence.
 * </p>
 *
 * @author Sara Visani
 */
public class ParamBuilderOptionalImpl implements ParamBuilderOptional {

    private Optional<Integer> noAggro = Optional.empty();
    private Optional<Double> regen = Optional.empty();
    private Optional<Double> senseDistance = Optional.empty();
    private Optional<Map<BuffNames, Double>> buff = Optional.empty();

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamBuilderOptional withNoAggro(final Integer noAggro) {
        this.noAggro = Optional.ofNullable(noAggro);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamBuilderOptional withRegen(final Double regen) {
        this.regen = Optional.ofNullable(regen);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamBuilderOptional withSenseDistance(final Double senseDistance) {
        this.senseDistance = Optional.ofNullable(senseDistance);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParamBuilderOptional withBuff(final Map<BuffNames, Double> buff) {
        this.buff = Optional.ofNullable(buff);
        return this;
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
    public Optional<Map<BuffNames, Double>> getBuffMap() {
        return this.buff;
    }
}
