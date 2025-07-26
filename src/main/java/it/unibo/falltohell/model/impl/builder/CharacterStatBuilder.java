package it.unibo.falltohell.model.impl.builder;

import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.builder.CharacterStatisticBuilder;
import it.unibo.falltohell.model.impl.statistics.CharacterStatisticsImpl;

/**
 * Builder implementation for {@link CharacterStatistics}.
 * Extends {@link StatisticBuilderImpl} parameterized with this builder type.
 * Implements {@link CharacterStatisticBuilder}.
 * <p>
 * This class adds specific attributes like mana and attack speed to the base
 * statistics.
 *
 * @author Sara Visani
 */
public class CharacterStatBuilder extends StatisticBuilderImpl<CharacterStatBuilder>
        implements CharacterStatisticBuilder {
    private double mana;
    private double attackSpeed;

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterStatBuilder withMana(final double mana) {
        this.mana = mana;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterStatBuilder withAttackSpeed(final double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterStatistics build() {
        final var life = super.getLife();
        final var attack = super.getAttack();
        final var speed = super.getSpeed();
        final var dimension = super.getDimensions();

        return new CharacterStatisticsImpl(life, attack, speed, dimension, mana, attackSpeed);
    }
}
