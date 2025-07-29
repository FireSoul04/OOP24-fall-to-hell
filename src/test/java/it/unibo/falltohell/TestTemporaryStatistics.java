package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.BaseCharacter;
import it.unibo.falltohell.model.impl.statistics.CharacterStatisticsImpl;
import it.unibo.falltohell.model.impl.buff.LifeBuff;
import it.unibo.falltohell.model.impl.buff.ManaBuff;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test to check if temporary life and mana buffs are used before their fixed counterparts.
 *
 * @author Davide Mancini
 */
class TestTemporaryStatistics {

    private static final double LIFE = 20;
    private static final double MANA = 10;
    private static final double ATTACK = 1;
    private static final double ATTACK_SPEED = 1;
    private static final double TEMPORARY_LIFE_MULTIPLIER = 0.5;
    private static final double TEMPORARY_MANA_MULTIPLIER = 0.5;
    private static final double TEMPORARY_LIFE = LIFE * TEMPORARY_LIFE_MULTIPLIER;
    private static final double TEMPORARY_MANA = MANA * TEMPORARY_MANA_MULTIPLIER;
    private static final long BUFF_DURATION = 3000;

    private Character character;
    private CharacterStatistics stats;

    /**
     * Initialize the character and its statistics, adding the character a temporary life and temporary mana buff.
     */
    @BeforeEach
    void initialize() {
        this.stats = new CharacterStatisticsImpl(
            LIFE,
            ATTACK,
            Vector2.zero(),
            new Dimensions(0, 0),
            MANA,
            ATTACK_SPEED
        );
        this.character = new BaseCharacter(new LevelTest(), Vector2.zero(), this.stats, "test.png") {
            @Override
            public CharacterID getCharacterID() {
                return CharacterID.ROGUE;
            }
        };
        this.character.getBuffManager().addBuff(new LifeBuff(stats, TEMPORARY_LIFE_MULTIPLIER), BUFF_DURATION, "buff1");
        this.character.getBuffManager().addBuff(new ManaBuff(stats, TEMPORARY_MANA_MULTIPLIER), BUFF_DURATION, "buff2");
    }

    /**
     * Test to check if the damage taken respects the life and temporary life remaining expected.
     * @param damage to take
     * @param lifeExpected remaining
     * @param temporaryLifeExpected remaining
     */
    void genericTakeDamageTest(final double damage, final double lifeExpected, final double temporaryLifeExpected) {
        this.character.setDamagedLife(damage);
        Assertions.assertEquals(lifeExpected, this.stats.getLife());
        Assertions.assertEquals(temporaryLifeExpected, this.stats.getTemporaryLife());
    }

    /**
     * Test if the character taking an amount of damage less than its current temporary life is calculated correctly.
     */
    @Test
    void takeAmountOfDamageLessThanTemporaryLife() {
        final double damage = TEMPORARY_LIFE / 2;
        this.genericTakeDamageTest(damage, this.stats.getFullLife(), TEMPORARY_LIFE - damage);
    }

    /**
     * Test if the character taking an amount of damage greater than its current temporary life is calculated correctly.
     */
    @Test
    void takeAmountOfDamageGreaterThanTemporaryLife() {
        this.genericTakeDamageTest(
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
        this.genericTakeDamageTest(
            TEMPORARY_LIFE,
            this.stats.getFullLife(),
            0
        );
    }

    /**
     * Test to check if the mana used respects the mana and temporary mana remaining expected.
     * @param mana to use
     * @param manaExpected remaining
     * @param temporaryManaExpected remaining
     */
    void genericUseManaTest(final double mana, final double manaExpected, final double temporaryManaExpected) {
        this.character.subManaIfEnough(mana);
        Assertions.assertEquals(manaExpected, this.stats.getMana());
        Assertions.assertEquals(temporaryManaExpected, this.stats.getTemporaryMana());
    }

    /**
     * Test if the character using an amount of mana less than its current temporary mana is calculated correctly.
     */
    @Test
    void useManaLessThanTemporaryMana() {
        final double mana = TEMPORARY_MANA / 2;
        this.genericUseManaTest(mana, this.stats.getInitialMana(), TEMPORARY_MANA - mana);
    }

    /**
     * Test if the character using an amount of mana greater than its current temporary mana is calculated correctly.
     */
    @Test
    void useManaGreaterThanTemporaryMana() {
        this.genericUseManaTest(
            TEMPORARY_MANA * 2,
            this.stats.getInitialMana() - TEMPORARY_MANA,
            0
        );
    }

    /**
     * Test if the character using an amount of mana equal its current temporary mana is calculated correctly.
     */
    @Test
    void useManaEqualThanTemporaryMana() {
        this.genericUseManaTest(
            TEMPORARY_MANA,
            this.stats.getInitialMana(),
            0
        );
    }
}
