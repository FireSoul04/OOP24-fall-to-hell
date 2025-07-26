package it.unibo.falltohell.model.api.statistic;

/**
 * Interface for enemy statistics with restricted movement.
 * <p>
 * Extends {@link BaseEnemyStatistics} by adding support for a maximum movement
 * distance.
 *
 * @author Sara Visani
 * @see BaseEnemyStatistics
 */
public interface RestrictedBaseEnemyStatistics extends BaseEnemyStatistics {

    /**
     * Gets the maximum distance this enemy can move.
     * <p>
     * 
     * @return the maximum allowed movement distance
     */
    double getDistance();
}
