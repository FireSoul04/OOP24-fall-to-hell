package it.unibo.falltohell.model.impl.gameobjects.movable.entity.character;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.Knife;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.List;

/**
 * Character representing a rogue.
 * It is fast, can attack at short range and has not too much defense.
 * It has the ability to throw knifes.
 */
public class Rogue extends BaseCharacter {

    private static final double LIFE = 10;
    private static final double ATTACK = 10;
    private static final double MANA = 10;
    private static final double ATTACK_SPEED = 10;
    private static final Vector2 SPEED = new Vector2(2.0, 2.0);
    private static final CharacterStatistics STATS = new StatisticFactoryImpl()
        .createCharacterStatistic(LIFE, ATTACK, SPEED, new Dimensions(20, 25), MANA, ATTACK_SPEED);
    private static final List<Vector2> KNIFES_VELOCITIES = List.of(
        new Vector2(3.0, 0.0),
        new Vector2(2.0, 1.0),
        new Vector2(2.0, -1.0)
    );

    /**
     * Creates a rogue.
     *
     * @param level where it belongs
     * @param position where is it in the level
     */
    public Rogue(final Level level, final Vector2 position) {
        super(level, position, STATS, "rogue.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        if (this.getLevel().getGameEventManager().checkCondition("ActiveAbility")) {
            this.throwKnifes();
        }
    }

    private void throwKnifes() {
        for (final Vector2 v : KNIFES_VELOCITIES) {
            new Knife(this.getLevel(), this.getPosition(), v);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterID getCharacterID() {
        return CharacterID.ROGUE;
    }
}
