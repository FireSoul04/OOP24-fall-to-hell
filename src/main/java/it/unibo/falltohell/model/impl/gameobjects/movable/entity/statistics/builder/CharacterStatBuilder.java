package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.CharacterStatisticBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.CharacterStatisticsImpl;

public class CharacterStatBuilder extends StatisticBuilderImpl<CharacterStatBuilder> implements CharacterStatisticBuilder{
    private double mana;
    private double attackSpeed;

    public CharacterStatBuilder withMana(double mana) {
        this.mana = mana;
        return this;
    }

    public CharacterStatBuilder withAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    public CharacterStatistics build() {
        return new CharacterStatisticsImpl(life, attack, speed, dimension, mana, attackSpeed);
    }
}
