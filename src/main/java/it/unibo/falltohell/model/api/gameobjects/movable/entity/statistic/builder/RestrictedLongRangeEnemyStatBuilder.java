package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;

/**
 * Builder interface for creating {@link RestrictedLongRangeEnemyStatistics}
 * objects
 * with customizable parameters such as distance.
 * <p>
 * This interface allows step-by-step construction of restricted long-range
 * enemy statistics.
 *
 * @author Sara Visani
 */
public interface RestrictedLongRangeEnemyStatBuilder {

    /**
     * Sets the attack or detection distance for the enemy.
     *
     * @param distance the maximum allowed distance for the enemy to engage or react
     * @return this builder instance for method chaining
     */
    public RestrictedLongRangeEnemyStatBuilder withDistance(double distance);

    /**
     * Builds the {@link RestrictedLongRangeEnemyStatistics} object using the
     * current configuration.
     *
     * @return a new instance of {@link RestrictedLongRangeEnemyStatistics}
     */
    public RestrictedLongRangeEnemyStatistics build();
}
