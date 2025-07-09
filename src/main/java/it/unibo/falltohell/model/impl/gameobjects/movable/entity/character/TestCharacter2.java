package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.abilities.AbilityFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.util.Vector2;

public class TestCharacter2 extends BaseCharacter{

    final private AbilityFactoryImpl factory = new AbilityFactoryImpl();
    final private MethodPassiveAbility mPa = this.factory.createMethodPassiveAbility(this);

    public TestCharacter2(Level level, Vector2 position, CharacterStatistics stats) {
        super(level, position, stats);
    }

    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.mPa.update(deltaTime);
    }

    @Override
    public CharacterID getCharacterID() {
        return null;
    }
    
}
