package it.unibo.falltohell.model.api.builder;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Builder interface for constructing statistic-related components of an entity.
 * This follows the Builder design pattern, allowing method chaining for
 * configuration.
 * <p>
 *
 * @author Sara Visani
 */
public interface StatisticBuilder {

    /**
     * Sets the life (health points) for the entity.
     * <p>
     *
     * @param life the life value to assign
     * @return this builder instance for method chaining
     */
    StatisticBuilder withLife(double life);

    /**
     * Sets the attack power for the entity.
     * <p>
     *
     * @param attack the attack value to assign
     * @return this builder instance for method chaining
     */
    StatisticBuilder withAttack(double attack);

    /**
     * Sets the movement speed of the entity.
     * <p>
     *
     * @param speed a {@link Vector2} representing the entity's movement speed on X
     *              and Y axes
     * @return this builder instance for method chaining
     */
    StatisticBuilder withSpeed(Vector2 speed);

    /**
     * Sets the dimensions (size) of the entity.
     * <p>
     *
     * @param dimensions a {@link Dimensions} object specifying width and height
     * @return this builder instance for method chaining
     */
    StatisticBuilder withDimensions(Dimensions dimensions);

    /**
     * Returns the current life points of the entity.
     * <p>
     *
     * @return the entity's current life
     */
    double getLife();

    /**
     * Returns the current attack power of the entity.
     * <p>
     *
     * @return the attack value
     */
    double getAttack();

    /**
     * Returns the current movement speed of the entity.
     * <p>
     *
     * @return the speed as a {@link Vector2}
     */
    Vector2 getSpeed();

    /**
     * Returns the physical size of the entity.
     * <p>
     *
     * @return the entity's {@link Dimensions}
     */
    Dimensions getDimensions();
}
