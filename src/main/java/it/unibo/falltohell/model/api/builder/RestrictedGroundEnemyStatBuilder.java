package it.unibo.falltohell.model.api.builder;

import it.unibo.falltohell.model.api.statistic.RestrictedBaseEnemyStatistics;

/**
 * Builder interface for creating {@link RestrictedBaseEnemyStatistics}
 * specifically for ground enemies
 * with customizable parameters like distance.
 *
 * <p>
 * This interface follows the builder pattern to enable step-by-step
 * configuration of restricted ground enemy statistics.
 * </p>
 *
 * @see RestrictedBaseEnemyStatistics
 * @author Sara Visani
 */
public interface RestrictedGroundEnemyStatBuilder {

    /**
     * Sets the attack or detection distance for the enemy.
     *
     * @param distance the maximum allowed distance for the enemy to engage or react
     * @return this builder instance for method chaining
     */
    RestrictedGroundEnemyStatBuilder withDistance(double distance);

    /**
     * Builds and returns the configured {@link RestrictedBaseEnemyStatistics}.
     *
     * @return a configured instance of {@link RestrictedBaseEnemyStatistics}
     */
    RestrictedBaseEnemyStatistics build();
}
