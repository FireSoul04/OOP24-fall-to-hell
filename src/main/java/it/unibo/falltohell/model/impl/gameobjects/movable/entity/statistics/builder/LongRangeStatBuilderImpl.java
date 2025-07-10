package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.LongRangeEnemyStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.LongRangedEnemyStatisticsImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class LongRangeStatBuilderImpl<T extends LongRangeStatBuilderImpl<T>> extends GroundEnemyStatBuilderImpl<T> implements LongRangeEnemyStatBuilder {

    protected double projectileAttack;
    protected Vector2 projectileVelocity;
    protected Dimensions projectileDimensions;
    protected int timeAttack;

    public T withProjectileAttack(double projectileAttack) {
        this.projectileAttack = projectileAttack;
        return self();
    }

    public T withProjectileVelocity(Vector2 projectileVelocity) {
        this.projectileVelocity = projectileVelocity;
        return self();
    }

    public T withProjectileDimensions(Dimensions projectileDimensions) {
        this.projectileDimensions = projectileDimensions;
        return self();
    }

    public T withTimeAttack(int timeAttack) {
        this.timeAttack = timeAttack;
        return self();
    }

    @Override
    public LongRangeEnemyStatistics build() {
        return new LongRangedEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen, senseDistance, points, projectileAttack, projectileVelocity, projectileDimensions, timeAttack);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }
}
