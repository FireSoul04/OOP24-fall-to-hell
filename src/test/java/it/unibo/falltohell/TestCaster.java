package it.unibo.falltohell;

import it.unibo.falltohell.model.api.factory.StatisticsFactory;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.gameobject.weapons.Staff;
import it.unibo.falltohell.model.impl.gameobject.weapons.Tome;
import it.unibo.falltohell.test.util.DummyEnemyTest;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.TimerManagerTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test the caster character.
 *
 * @author Martina Malagoli
 */
class TestCaster {

    private static final String TIMER_NAME = "mana_recharge";
    private static final long TIMEOUT = 5500;
    private static final double STAT = 60;

    private Caster caster;
    private boolean useActiveAbility;
    private boolean useSpecialAbility;
    private TimerManagerTest timerManager;
    private CharacterStatistics statistics;

    /**
     * Initialization of variables used in the tests.
     */
    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        level.addCondition("ActiveAbility", () -> this.useActiveAbility);
        level.addCondition("SpecialAbility", () -> this.useSpecialAbility);
        this.timerManager = (TimerManagerTest) level.getTimerManager();
        this.caster = new Caster(level, Vector2.zero());
        this.statistics = (CharacterStatistics) this.caster.getStats();
        this.useActiveAbility = false;
        this.useSpecialAbility = false;
    }

    /**
     * Tests if the caster passive ability to refill mana gradually works as
     * expected.
     */
    @Test
    void testPassiveAbility() {
        this.caster.subMana(this.statistics.getInitialMana() / 2);
        double currentMana = this.statistics.getMana();
        this.timerManager.waitForTimer(TIMER_NAME, TIMEOUT);
        Assertions.assertTrue(this.statistics.getMana() > currentMana, "Mana should have been added");
        currentMana = this.statistics.getMana();
        this.caster.disable();
        this.timerManager.waitUntilTimeout(TIMEOUT);
        Assertions.assertEquals(0, Double.compare(currentMana, this.statistics.getMana()),
                "Mana shouldn't have been added");
    }

    /**
     * Tests if the caster's active ability to create a powerful blast that damages
     * enemies works as expected.
     */
    @Test
    void testBlastAbility() {
        this.useActiveAbility = true;
        final StatisticsFactory statisticsFactory = new StatisticFactoryImpl();
        final BaseEnemyStatistics enemyStatistics = statisticsFactory
                .createBaseEnemyStatistic(STAT, STAT, Vector2.zero(), new Dimensions(STAT, STAT),
                        Vector2.zero(), 10, statisticsFactory.createOptional());
        final DummyEnemyTest enemy = new DummyEnemyTest(this.caster.getLevel(), enemyStatistics);
        final double enemyInitialLife = enemyStatistics.getLife();
        this.caster.getLevel().update(1.0);
        Assertions.assertTrue(enemyInitialLife > enemy.getStats().getLife(),
                "Enemy must have taken damage");
    }

    /**
     * Tests if the caster's special ability of the caster to heal himself
     * works as expected.
     */
    @Test
    void testHealAbility() {
        this.useSpecialAbility = true;
        this.caster.subLife(this.statistics.getFullLife() / 2);
        final double beforeHealingLife = this.statistics.getLife();
        this.caster.update(1.0);
        Assertions.assertTrue(beforeHealingLife < this.statistics.getLife());
    }

    /**
     * Tests if the caster swaps his weapons correctly depending on the mana amount.
     */
    @Test
    void changeWeapon() {
        this.caster.getEquippedWeapon().ifPresent(w -> Assertions.assertInstanceOf(Tome.class, w));
        this.caster.subMana(this.statistics.getInitialMana());
        this.caster.attack();
        this.caster.getEquippedWeapon().ifPresent(w -> Assertions.assertInstanceOf(Staff.class, w));
        this.caster.addMana(this.statistics.getInitialMana());
        this.caster.attack();
        this.caster.getEquippedWeapon().ifPresent(w -> Assertions.assertInstanceOf(Tome.class, w));
    }
}
