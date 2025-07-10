package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;

public interface CharacterStatisticBuilder extends StatisticBuilder<CharacterStatisticBuilder>{
    public CharacterStatisticBuilder withMana(double mana);

    public CharacterStatisticBuilder withAttackSpeed(double attackSpeed);

    public CharacterStatistics build();
}
