package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import java.util.Optional;

public interface ParamBuilderOptional {
    ParamBuilderOptional withNoAggro(Integer noAggro);
    ParamBuilderOptional withRegen(Double regen);
    ParamBuilderOptional withSenseDistance(Double senseDistance);

    Optional<Integer> getNoAggro();
    Optional<Double> getRegen();
    Optional<Double> getSenseDistance();
}
