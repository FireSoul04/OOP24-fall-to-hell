package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import java.util.Map;
import java.util.Optional;

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

    ParamBuilderOptional withBuff(Map<String, Double> buff);

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

    Optional<Map<String,Double>> getBuffMap();
}
