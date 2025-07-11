package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.CharacterStatisticBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.CharacterStatisticsImpl;

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
    public CharacterStatBuilder withMana(double mana) {
        this.mana = mana;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterStatBuilder withAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterStatistics build() {
        return new CharacterStatisticsImpl(life, attack, speed, dimension, mana, attackSpeed);
    }
}
