package it.unibo.falltohell;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.factory.StatisticsFactory;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.gameobject.weapons.Dagger;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.test.util.DummyEnemyTest;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class TestMeleeWeapon {

    private Level level;
    private Weapon dagger;
    private Rogue rogue;

    @BeforeEach
    void initialization() {
        this.level = new LevelTest();
        this.rogue = new Rogue(this.level, Vector2.zero());
        this.dagger = new Dagger(this.rogue);
    }

    @Test
    void testDamageOnEnemy() {
        final StatisticsFactory sf = new StatisticFactoryImpl();
        final BaseEnemyStatistics dummyStats = sf.createBaseEnemyStatistic(
            10, 0, Vector2.zero(), new Dimensions(20, 20),
            Vector2.zero(), this.rogue, 0, sf.createOptional()
        );
        final Enemy dummy = new DummyEnemyTest(this.level, Vector2.zero(), dummyStats);
        final double initialLife = dummy.getStats().getLife();
        this.dagger.attack();
        this.level.update(1.0);
        Assertions.assertTrue(dummy.getStats().getLife() < initialLife, "The enemy should be hit and take damage");
    }
}
