package it.unibo.falltohell.model.api.statistic;

/**
 * Interface representing statistics for enemies that have restricted movement.
 * <p>
 * This interface extends {@link LongRangeEnemyStatistics} and adds a constraint
 * on the movement range of an enemy.
 *
 * @author Sara Visani
 */
public interface RestrictedLongRangeEnemyStatistics extends LongRangeEnemyStatistics {

    /**
     * Returns the maximum distance the enemy is allowed to move.
     * <p>
     * 
     * @return the maximum movement distance
     */
    double getDistance();
}
