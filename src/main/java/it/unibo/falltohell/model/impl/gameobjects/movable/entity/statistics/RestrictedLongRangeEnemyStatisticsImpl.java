package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Class implementations for restricted enemy
 *
 * @author Sara Visani
 */
public class RestrictedLongRangeEnemyStatisticsImpl extends LongRangedEnemyStatisticsImpl implements RestrictedLongRangeEnemyStatistics{

    final double distance;

    /**
     * 
     * @param life
     * @param attack
     * @param speed
     * @param dimension
     * @param position
     * @param noAggro
     * @param character
     * @param projectileAttack
     * @param projectileVelocity
     * @param projectileDimensions
     * @param distance
     */
    public RestrictedLongRangeEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimension, final Vector2 position, final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions, final double distance){
        super(life, attack, speed, dimension, position, noAggro, character, projectileAttack, projectileVelocity, projectileDimensions);
        this.distance = distance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDistance() {
        return this.distance;
    }
}
