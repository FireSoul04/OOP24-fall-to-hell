package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.GroundEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.BaseEnemyStatisticsImpl;
import it.unibo.falltohell.util.Vector2;

public class GroundEnemyStatBuilderImpl<T extends GroundEnemyStatBuilderImpl<T>> extends StatisticBuilderImpl<T> implements GroundEnemyStatBuilder {

    protected Vector2 position;
    protected Optional<Integer> noAggro = Optional.empty();
    protected Optional<Double> regen = Optional.empty();
    protected Optional<Double> senseDistance = Optional.empty();
    protected Character character;
    protected long points;

    @Override
    public T withPosition(Vector2 position) {
        this.position = position;
        return self();
    }

    @Override
    public T withNoAggro(Integer noAggro) {
        this.noAggro = Optional.ofNullable(noAggro);
        return self();
    }

    @Override
    public T withRegen(Double regen) {
        this.regen = Optional.ofNullable(regen);
        return self();
    }

    @Override
    public T withSenseDistance(Double senseDistance) {
        this.senseDistance = Optional.ofNullable(senseDistance);
        return self();
    }

    @Override
    public T withCharacter(Character character) {
        this.character = character;
        return self();
    }

    @Override
    public T withPoints(long points) {
        this.points = points;
        return self();
    }

    @Override
    public BaseEnemyStatistics build() {
        return new BaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen, senseDistance, points);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}
