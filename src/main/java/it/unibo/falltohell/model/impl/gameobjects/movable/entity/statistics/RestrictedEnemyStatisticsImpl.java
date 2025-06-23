package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedEnemyStatistics;

/**
 * Class implementations for restricted enemy
 *
 * @author Sara Visani
 */
public class RestrictedEnemyStatisticsImpl implements RestrictedEnemyStatistics{

    final double distance;
    final Statistics refer;

    /**
     * Create new statistics with the parameters specified.
     * @param refer
     * @param distance
     */
    public RestrictedEnemyStatisticsImpl(final Statistics refer, final double distance){
        this.distance = distance;
        this.refer = refer;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public double getDistance() {
        return this.distance;
    }
    
    /*
     * {@inheritDoc}
     */
    @Override
    public Statistics getRefer() {
        return this.refer;
    }
}
