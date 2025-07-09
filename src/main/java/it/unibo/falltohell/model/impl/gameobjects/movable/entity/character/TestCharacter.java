package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.abilities.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

//TODO eliminate this class when character is implemented

public class TestCharacter extends BaseCharacter{

    final private CharacterStatistics stats;
    final private AbilityFactoryImpl factory = new AbilityFactoryImpl();
    final private StatisticPassiveAbility sPa = this.factory.createPassiveAbility(this, null);
    final private MethodPassiveAbility mPa = this.factory.createMethodPassiveAbility(this);

    public TestCharacter(final Level level, final Vector2 position) {
        super(level, position, new StatisticFactoryImpl().createCharacterStatistic(10, 10, new Vector2(10, 10), new Dimensions(10, 10), 10, 10));
        this.stats = (CharacterStatistics)super.getStats();
    }

    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.stats.getDimensions();
        this.sPa.carryOut();
        this.mPa.update(deltaTime);
    }

    @Override
    public CharacterID getCharacterID() {
        return null;
    }
}
