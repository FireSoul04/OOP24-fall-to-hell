package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

/**
 * Interface for all enemies that have restricted movement
 *
 * @author Sara Visani
 */
public interface RestrictedBaseEnemyStatistics extends BaseEnemyStatistics{

    /**
     * @return the maximum distance that the enemy can do
     */
    public double getDistance();
}
