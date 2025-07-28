package it.unibo.falltohell.model.api.builder;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.util.Vector2;

/**
 * Builder interface for creating {@link BaseEnemyStatistics} instances
 * for ground-based enemies. This builder allows step-by-step configuration
 * of various statistical attributes before constructing the final object.
 * <p>
 *
 * @author Sara Visani
 */
public interface GroundEnemyStatBuilder {

    /**
     * Sets the position of the enemy.
     * <p>
     *
     * @param position the {@link Vector2} representing the enemy's initial position
     * @return this builder instance for method chaining
     */
    GroundEnemyStatBuilder withPosition(Vector2 position);

    /**
     * Sets the time in seconds the enemy does not detect or engage the player (no
     * aggro).
     * <p>
     *
     * @param noAggro the number of seconds without aggro
     * @return this builder instance for method chaining
     */
    GroundEnemyStatBuilder withNoAggro(Integer noAggro);

    /**
     * Sets the health regeneration rate for the enemy.
     * <p>
     *
     * @param regen the regeneration rate (e.g., HP per second)
     * @return this builder instance for method chaining
     */
    GroundEnemyStatBuilder withRegen(Double regen);

    /**
     * Sets the maximum distance at which the enemy can sense a player.
     * <p>
     *
     * @param senseDistance the distance (in world units)
     * @return this builder instance for method chaining
     */
    GroundEnemyStatBuilder withSenseDistance(Double senseDistance);

    /**
     * Sets the number of points awarded for defeating this enemy.
     * <p>
     *
     * @param points the score value
     * @return this builder instance for method chaining
     */
    GroundEnemyStatBuilder withPoints(long points);

    /**
     * Sets the schematic of the buffs the enemy drop at death.
     * <p>
     *
     * @param buff the buff table
     * @return this builder instance for method chaining
     */
    GroundEnemyStatBuilder withBuff(Map<BuffNames, Double> buff);

    /**
     * Builds and returns the {@link BaseEnemyStatistics} instance with the
     * configured attributes.
     * <p>
     *
     * @return a fully initialized {@link BaseEnemyStatistics} object
     */
    BaseEnemyStatistics build();

    /**
     * Gets the enemy's initial spawn coordinates.
     * <p>
     *
     * @return the initial {@link Vector2} position
     */
    Vector2 getInitialPos();

    /**
     * Gets the duration (in ticks or seconds) of the no-aggro state.
     * <p>
     *
     * @return the no-aggro time duration
     */
    Optional<Integer> getNoAggro();

    /**
     * Returns the regeneration rate of the enemy (if applicable).
     * <p>
     *
     * @return the regeneration value
     */
    Optional<Double> getRegen();

    /**
     * Gets the enemy's sensing distance (e.g., detection range).
     * <p>
     *
     * @return the sense distance
     */
    Optional<Double> getSenseDistance();

    /**
     * Gets the enemy's points after a kill.
     * <p>
     *
     * @return the points of a enemy
     */
    long getPoints();

    /**
     * @return the map with key type of buff and value the percentage
     */
    Optional<Map<BuffNames, Double>> getBuffMap();

}
