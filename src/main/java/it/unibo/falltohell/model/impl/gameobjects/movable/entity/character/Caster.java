package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Caster extends BaseCharacter {

    private static final double LIFE = 0;
    private static final double ATTACK = 0;
    private static final double ATTACK_SPEED = 0;
    private static final Vector2 SPEED = Vector2.zero();
    private static final double MANA = 0;
    private static final CharacterStatistics STATISTICS = new StatisticFactoryImpl()
            .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(0,0), MANA, ATTACK_SPEED);

    /**
     * Base constructor for a new caster character.
     *
     * @param level where the caster actually is
     * @param position of the caster in the level
     * @param fileName is the name of the image file associated to the caster
     */
    public Caster(final Level level, final Vector2 position, final String fileName) {
        super(level, position, STATISTICS, fileName);
    }

    @Override
    public CharacterID getCharacterID() {
        return CharacterID.CASTER;
    }
}
