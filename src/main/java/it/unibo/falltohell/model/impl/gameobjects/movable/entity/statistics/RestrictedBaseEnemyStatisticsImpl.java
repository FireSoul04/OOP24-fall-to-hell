package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedEnemyStatistics;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

public class RestrictedBaseEnemyStatisticsImpl extends BaseEnemyStatisticsImpl implements RestrictedEnemyStatistics{

    final private double distance;

    public RestrictedBaseEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimension,
            final Vector2 position, final int noAggro, final Character character, final double distance) {
        super(life, attack, speed, dimension, position, noAggro, character);
        this.distance = distance;
    }

    @Override
    public double getDistance() {
        return this.distance;
    }
    
}
