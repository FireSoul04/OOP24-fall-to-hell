package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public interface StatisticBuilder<T extends StatisticBuilder<T>> {
    public T withLife(double life);

    public T withAttack(double attack);

    public T withSpeed(Vector2 speed);

    public T withDimensions(Dimensions dimensions);
}
