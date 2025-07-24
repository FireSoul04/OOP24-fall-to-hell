package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.CharacterStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.LifeBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.ManaBuff;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to check if temporary life and mana buffs are used before their fixed counterparts.
 *
 * @author Davide Mancini
 */
class TestTemporaryStatistics {

    private static final double LIFE = 20.0;
    private static final double MANA = 10.0;
    private static final double TEMPORARY_LIFE_MULTIPLIER = 0.5;
    private static final double TEMPORARY_MANA_MULTIPLIER = 0.5;
    private static final double TEMPORARY_LIFE = LIFE * TEMPORARY_LIFE_MULTIPLIER;
    private static final double TEMPORARY_MANA = MANA * TEMPORARY_MANA_MULTIPLIER;

    private Character character;
    private CharacterStatistics stats;

    /**
     * Initialize the character and its statistics, adding the character a temporary life and temporary mana buff.
     */
    @BeforeEach
    void initialize() {
        this.stats = new CharacterStatisticsImpl(
            LIFE,
            0,
            Vector2.zero(),
            new Dimensions(0, 0),
            MANA,
            0
        );
        this.character = new BaseCharacter(new LevelTest(), Vector2.zero(), this.stats) {
            @Override
            public CharacterID getCharacterID() {
                return CharacterID.ROGUE;
            }
        };
        this.character.getBuffManager().addBuff(new LifeBuff(stats, TEMPORARY_LIFE_MULTIPLIER));
        this.character.getBuffManager().addBuff(new ManaBuff(stats, TEMPORARY_MANA_MULTIPLIER));
    }

    /**
     * Test to check if the damage taken respects the life and temporary life remaining expected.
     * @param damage to take
     * @param lifeExpected remaining
     * @param temporaryLifeExpected remaining
     */
    void genericTakeDamageTest(final double damage, final double lifeExpected, final double temporaryLifeExpected) {
        this.character.setDamagedLife(damage);
        assertEquals(lifeExpected, this.stats.getLife());
        assertEquals(temporaryLifeExpected, this.stats.getTemporaryLife());
    }

    /**
     * Test if the character taking an amount of damage less than its current temporary life is calculated correctly.
     */
    @Test
    void takeAmountOfDamageLessThanTemporaryLife() {
        final double damage = TEMPORARY_LIFE / 2;
        genericTakeDamageTest(damage, this.stats.getFullLife(), TEMPORARY_LIFE - damage);
    }

    /**
     * Test if the character taking an amount of damage greater than its current temporary life is calculated correctly.
     */
    @Test
    void takeAmountOfDamageGreaterThanTemporaryLife() {
        genericTakeDamageTest(
            TEMPORARY_LIFE * 2,
            this.stats.getFullLife() - TEMPORARY_LIFE,
            0
        );
    }

    /**
     * Test if the character taking an amount of damage equal its current temporary life is calculated correctly.
     */
    @Test
    void takeAmountOfDamageEqualThanTemporaryLife() {
        genericTakeDamageTest(
            TEMPORARY_LIFE,
            this.stats.getFullLife(),
            0
        );
    }

    // TODO Add tests for temporary mana
}
