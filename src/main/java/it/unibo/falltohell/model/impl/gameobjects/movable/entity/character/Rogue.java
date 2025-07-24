package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import java.util.Optional;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Rogue extends BaseCharacter {

    private static final double LIFE = 0;
    private static final double ATTACK = 0;
    private static final double MANA = 0;
    private static final double ATTACK_SPEED = 0;
    private static final Vector2 SPEED = Vector2.zero();
    private static final CharacterStatistics STATS = new StatisticFactoryImpl()
        .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(0, 0), MANA, ATTACK_SPEED);

    /**
     * Creates a rogue.
     *
     * @param level where it belongs
     * @param position where is it in the level
     */
    public Rogue(final Level level, final Vector2 position) {
        super(level, position, STATS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterID getCharacterID() {
        return CharacterID.ROGUE;
    }
}
