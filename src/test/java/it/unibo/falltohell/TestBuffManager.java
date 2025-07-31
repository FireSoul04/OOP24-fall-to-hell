package it.unibo.falltohell;

import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.BuffManager;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.buff.LifeBuff;
import it.unibo.falltohell.model.impl.buff.ManaBuff;
import it.unibo.falltohell.model.impl.buff.AttackBuff;
import it.unibo.falltohell.model.impl.buff.AttackSpeedBuff;
import it.unibo.falltohell.model.impl.buff.SpeedBuff;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.TimerManagerTest;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class to test if buffs work as expected.
 * @author Martina Malagoli
 */
class TestBuffManager {

    private static final double MULTIPLIER = 0.7;
    private static final long DURATION = 50;
    private CharacterStatistics statistics;
    private BuffManager buffManager;
    private Map<String, Buff> buffs;
    private TimerManagerTest timerManager;

    /**
     * Initialization of the variables used in each test.
     */
    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        final Character character = new Caster(level, Vector2.zero());
        this.statistics = (CharacterStatistics) character.getStats();
        this.buffManager = character.getBuffManager();
        this.timerManager = (TimerManagerTest) level.getTimerManager();
        this.buffs = new HashMap<>();
        this.buffs.put("life", new LifeBuff(statistics, MULTIPLIER));
        this.buffs.put("mana", new ManaBuff(statistics, MULTIPLIER));
        this.buffs.put("attack", new AttackBuff(statistics, MULTIPLIER));
        this.buffs.put("attsp", new AttackSpeedBuff(statistics, MULTIPLIER));
        this.buffs.put("speed", new SpeedBuff(statistics, MULTIPLIER));
    }

    /**
     * Tests if each type of time-limited buff works correctly:
     * if they are handled by the buff manager in the right way
     * and if they are applied to the character as expected.
     */
    @Test
    void testLimitedTimeBuffs() {
        this.buffs.forEach((key, value) -> this.buffManager.addBuff(value, DURATION, key));
        Assertions.assertEquals(statistics.getFullLife() * MULTIPLIER,
                statistics.getTemporaryLife(),
                "Temporary life should be its initial value plus the the full life of "
                        + " the character multiplied with the multiplier");
        Assertions.assertEquals(statistics.getInitialMana() * MULTIPLIER,
                statistics.getTemporaryMana(),
                "Temporary mana should be its initial value plus the full mana of"
                        + " the character multiplied with the multiplier");
        Assertions.assertEquals(statistics.getInitialAttack() * (1 + MULTIPLIER),
                statistics.getAttack(),
                "Attack should be the initial attack plus itself multiplied with the multiplier");
        Assertions.assertEquals(statistics.getInitialAttackSpeed() * (1 + MULTIPLIER),
                statistics.getAttackSpeed(),
                "Attack-speed should be the initial attack-speed plus itself multiplied with the multiplier");
        Assertions.assertEquals(Math.round(statistics.getSpeed().magnitude()),
                Math.round(statistics.getInitialSpeed().magnitude() * (1 + MULTIPLIER)),
                "Speed should be the initial speed plus itself multiplied with the multiplier");
        this.buffs.keySet().forEach(i -> Assertions.assertTrue(this.buffManager.searchBuff(i),
                "There should be a buff with this name:" + i));
        this.buffs.forEach(
                (name, buff) -> this.timerManager.waitForTimer(name, DURATION * 2));
        Assertions.assertEquals(0.0, statistics.getTemporaryLife(),
                "Temporary life should be reset to the one it was before the buff");
        Assertions.assertEquals(0.0, statistics.getTemporaryMana(),
                "Temporary mana should be reset to the one it was before the buff");
        Assertions.assertEquals(statistics.getInitialAttack(), statistics.getAttack(),
                "Attack should be reset to the one it was before the buff");
        Assertions.assertEquals(statistics.getInitialAttackSpeed(), statistics.getAttackSpeed(),
                "Attack-speed should be reset to the one it was before the buff");
        Assertions.assertEquals(Math.round(statistics.getInitialSpeed().magnitude()),
                Math.round(statistics.getSpeed().magnitude()),
                "Speed should be reset to the one it was before the buff");
        this.buffs.keySet().forEach(i -> Assertions.assertFalse(this.buffManager.searchBuff(i),
                "There should be no buff with this name: " + i));
    }

    /**
     * Tests if each type of infinite buff works correctly:
     * if they are handled by the buff manager as expected
     * (their application is the same as the time-limited buffs).
     */
    @Test
    void testInfiniteBuffs() {
        this.buffs.forEach((key, value) -> this.buffManager.addInfiniteBuff(value, key));
        this.buffs.keySet().forEach(i -> Assertions.assertTrue(this.buffManager.searchBuff(i),
                "There should be a buff with this name: " + i));
        this.buffs.forEach((key, value) -> this.buffManager.removeInfiniteBuff(key));
        this.buffs.keySet().forEach(i -> Assertions.assertFalse(this.buffManager.searchBuff(i),
                "There should be no buff with this name: " + i));
    }

    /**
     * Tests if duplicated buffs are correctly handled.
     */
    @Test
    void testDuplicatedBuffs() {
        final String buffName = "life";
        this.buffManager.addBuff(this.buffs.get(buffName), DURATION * 3, buffName);
        try {
            this.buffManager.addBuff(this.buffs.get(buffName), DURATION, buffName);
            Assertions.fail("A duplicate buff should not be added");
        } catch (final IllegalArgumentException e) {
            Logger.getLogger("buffLogger").info("The IllegalArgumentException was thrown correctly");
        }
    }
}
