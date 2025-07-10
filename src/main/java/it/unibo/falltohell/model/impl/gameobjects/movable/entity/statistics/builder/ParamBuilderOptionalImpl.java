package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.ParamBuilderOptional;

public class ParamBuilderOptionalImpl implements ParamBuilderOptional{

    private Optional<Integer> noAggro = Optional.empty();
    private Optional<Double> regen = Optional.empty();
    private Optional<Double> senseDistance = Optional.empty();


    @Override
    public ParamBuilderOptional withNoAggro(Integer noAggro) {
        this.noAggro = Optional.ofNullable(noAggro);
        return this;
    }

    @Override
    public ParamBuilderOptional withRegen(Double regen) {
        this.regen = Optional.ofNullable(regen);
        return this;
    }

    @Override
    public ParamBuilderOptional withSenseDistance(Double senseDistance) {
        this.senseDistance = Optional.ofNullable(senseDistance);
        return this;
    }

    @Override
    public Optional<Integer> getNoAggro() {
        return this.noAggro;
    }

    @Override
    public Optional<Double> getRegen() {
        return this.regen;
    }

    @Override
    public Optional<Double> getSenseDistance() {
        return this.senseDistance;
    }

}
