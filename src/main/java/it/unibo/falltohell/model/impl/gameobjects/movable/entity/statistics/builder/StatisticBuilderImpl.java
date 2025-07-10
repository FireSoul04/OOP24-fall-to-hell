package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.StatisticBuilder;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class StatisticBuilderImpl<T extends StatisticBuilderImpl<T>> implements StatisticBuilder{

    protected double life;
    protected double attack;
    protected Vector2 speed;
    protected Dimensions dimension;

    @Override
    public T withLife(double life) {
        this.life = life;
        return self();
    }

    @Override
    public T withAttack(double attack) {
        this.attack = attack;
        return self();
    }

    @Override
    public T withSpeed(Vector2 speed) {
        this.speed = speed;
        return self();
    }

    @Override
    public T withDimensions(Dimensions dimensions) {
        this.dimension = dimensions;
        return self();
    }

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}
