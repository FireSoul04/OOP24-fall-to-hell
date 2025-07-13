package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import java.util.Map;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
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
    public GroundEnemyStatBuilder withPosition(Vector2 position);

    /**
     * Sets the time in seconds the enemy does not detect or engage the player (no
     * aggro).
     * <p>
     *
     * @param noAggro the number of seconds without aggro
     * @return this builder instance for method chaining
     */
    public GroundEnemyStatBuilder withNoAggro(Integer noAggro);

    /**
     * Sets the health regeneration rate for the enemy.
     * <p>
     *
     * @param regen the regeneration rate (e.g., HP per second)
     * @return this builder instance for method chaining
     */
    public GroundEnemyStatBuilder withRegen(Double regen);

    /**
     * Sets the maximum distance at which the enemy can sense a player.
     * <p>
     *
     * @param senseDistance the distance (in world units)
     * @return this builder instance for method chaining
     */
    public GroundEnemyStatBuilder withSenseDistance(Double senseDistance);

    /**
     * Sets the {@link Character} reference associated with this enemy.
     * <p>
     *
     * @param character the enemy's {@link Character} instance
     * @return this builder instance for method chaining
     */
    public GroundEnemyStatBuilder withCharacter(Character character);

    /**
     * Sets the number of points awarded for defeating this enemy.
     * <p>
     *
     * @param points the score value
     * @return this builder instance for method chaining
     */
    public GroundEnemyStatBuilder withPoints(long points);

    GroundEnemyStatBuilder withBuff(Map<String, Double> buff);

    /**
     * Builds and returns the {@link BaseEnemyStatistics} instance with the
     * configured attributes.
     * <p>
     *
     * @return a fully initialized {@link BaseEnemyStatistics} object
     */
    public BaseEnemyStatistics build();

}
