package it.unibo.falltohell.model.api.builder;

import it.unibo.falltohell.model.api.statistic.CharacterStatistics;

/**
 * Builder interface for constructing instances of {@link CharacterStatistics}.
 * <p>
 * Provides a fluent API to set optional or configurable fields like mana and
 * attack speed.
 *
 * @author Sara Visani
 */
public interface CharacterStatisticBuilder {

    /**
     * Sets the mana value for the {@link CharacterStatistics} being built.
     * <p>
     *
     * @param mana the mana value
     * @return this builder instance for chaining
     */
    CharacterStatisticBuilder withMana(double mana);

    /**
     * Sets the attack speed for the {@link CharacterStatistics} being built.
     * <p>
     *
     * @param attackSpeed the attack speed value
     * @return this builder instance for chaining
     */
    CharacterStatisticBuilder withAttackSpeed(double attackSpeed);

    /**
     * Builds and returns a new instance of {@link CharacterStatistics} using the
     * configured values.
     * <p>
     *
     * @return a fully constructed {@link CharacterStatistics} instance
     */
    CharacterStatistics build();
}
