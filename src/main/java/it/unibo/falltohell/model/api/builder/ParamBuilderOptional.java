package it.unibo.falltohell.model.api.builder;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;

/**
 * Interface for building optional parameters of a enemy's statistics.
 * <p>
 * This builder follows the builder pattern for setting optional values such as
 * {@code noAggro}, {@code regen}, and {@code senseDistance}.
 * </p>
 *
 * @see java.util.Optional
 * @author Sara Visani
 */
public interface ParamBuilderOptional {

    /**
     * Sets the optional noAggro parameter, which may define how long a character
     * stays out of aggression state.
     *
     * @param noAggro the duration of no aggression in ticks (optional)
     * @return this builder instance for chaining
     */
    ParamBuilderOptional withNoAggro(Integer noAggro);

    /**
     * Sets the optional regeneration value.
     *
     * @param regen the amount of health regenerated per tick (optional)
     * @return this builder instance for chaining
     */
    ParamBuilderOptional withRegen(Double regen);

    /**
     * Sets the optional sense distance, i.e., how far the character can detect
     * other objects.
     *
     * @param senseDistance the sensing range in game units (optional)
     * @return this builder instance for chaining
     */
    ParamBuilderOptional withSenseDistance(Double senseDistance);

    /**
     * <p>
     * Sets optional buffs applied to the enemy's statistics.
     * </p>
     *
     * @param buff a map containing buff types and their corresponding multipliers
     * @return this builder instance for method chaining
     *
     * @see BuffNames
     */
    ParamBuilderOptional withBuff(Map<BuffNames, Double> buff);

    /**
     * @return an {@link Optional} containing the noAggro value if present
     */
    Optional<Integer> getNoAggro();

    /**
     * @return an {@link Optional} containing the regeneration value if present
     */
    Optional<Double> getRegen();

    /**
     * @return an {@link Optional} containing the sensing distance if present
     */
    Optional<Double> getSenseDistance();

    /**
     * <p>
     * Returns the optional map of buffs applied to the enemy.
     * </p>
     *
     * @return an {@link Optional} containing the buff map, if present
     *
     * @see BuffNames
     */
    Optional<Map<BuffNames, Double>> getBuffMap();
}
