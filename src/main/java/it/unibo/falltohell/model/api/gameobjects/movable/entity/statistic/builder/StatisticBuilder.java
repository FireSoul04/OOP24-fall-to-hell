package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public interface StatisticBuilder{
    public StatisticBuilder withLife(double life);

    public StatisticBuilder withAttack(double attack);

    public StatisticBuilder withSpeed(Vector2 speed);

    public StatisticBuilder withDimensions(Dimensions dimensions);
}
