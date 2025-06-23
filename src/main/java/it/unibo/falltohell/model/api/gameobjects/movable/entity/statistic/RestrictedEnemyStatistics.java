package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;

/**
 * Interface for all enemies that have restricted movement
 *
 * @author Sara Visani
 */
public interface RestrictedEnemyStatistics{
    
    /**
     * @return the maximum distance that the enemy can do
     */
    public double getDistance();

    /**
     * @return the reference of which type of enemy statistic on needs
     */
    public Statistics getRefer();
}
