package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.factory.StatisticsFactory;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobject.weapons.BaseMeleeWeapon;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.DummyEnemyTest;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.logging.Logger;

class TestMeleeWeapon {

    private static final Vector2 POSITION = Vector2.zero();
    private static final double LIFE = 0;
    private static final double MANA = 0;
    private static final Vector2 SPEED = Vector2.zero();
    private static final double ATTACK = 1;
    private static final double ATTACK_SPEED = 1;
    private static final double DAMAGE_MULTIPLIER = 1;
    private static final Dimensions SIZE = new Dimensions(0, 0);
    private static final long COOLDOWN = 200;
    private static final CharacterStatistics STATS = new StatisticFactoryImpl().createCharacterStatistic(
        LIFE, ATTACK, SPEED, SIZE, MANA, ATTACK_SPEED
    );

    private Level level;
    private Weapon sword;
    private Enemy dummy;

    /**
     * Initiate the level, character, weapon and dummy.
     * Equips the weapon to the character.
     */
    @BeforeEach
    void initialization() {
        this.level = new LevelTest();
        final Character character = new BaseCharacter(this.level, POSITION, STATS, "test.png") {
            @Override
            public CharacterID getCharacterID() {
                return null;
            }
        };
        this.sword = new BaseMeleeWeapon(character, new BoxCollider(), DAMAGE_MULTIPLIER, COOLDOWN, "test.png") {
        };
        character.equipWeapon(this.sword);
        final StatisticsFactory sf = new StatisticFactoryImpl();
        final BaseEnemyStatistics dummyStats = sf.createBaseEnemyStatistic(
            10, 0, Vector2.zero(), new Dimensions(20, 20),
            Vector2.zero(), 0, sf.createOptional()
        );
        this.dummy = new DummyEnemyTest(this.level, Vector2.zero(), dummyStats);
    }

    /**
     * Test if the weapon deals the correct amount of damage to the dummy.
     */
    @Test
    void testDamageOnEnemy() {
        final double initialLife = this.dummy.getStats().getLife();
        this.sword.attack();
        this.level.update(1.0);
        Assertions.assertEquals(initialLife - ATTACK * DAMAGE_MULTIPLIER, this.dummy.getStats().getLife(), "The enemy should be hit and take damage");
    }

    /**
     * Test if the cooldown is working correctly.
     */
    @Test
    void testCooldown() {
        final double initialLife = this.dummy.getStats().getLife();
        this.sword.attack();
        this.level.update(1.0);
        this.sword.attack();
        this.level.update(1.0);
        Assertions.assertEquals(initialLife - ATTACK * DAMAGE_MULTIPLIER, this.dummy.getStats().getLife(),
            "The enemy should get hit just once");
        this.sword.attack();
        this.level.update(1.0);
        try {
            Thread.sleep(COOLDOWN);
        } catch (InterruptedException e) {
            Logger.getLogger("TestMeleeWeaponLogger").severe("Thread interrupted: " + e);
        }
        this.dummy.getStats().setLife(initialLife);
        this.sword.attack();
        this.level.update(1.0);
        Assertions.assertEquals(initialLife - ATTACK * DAMAGE_MULTIPLIER * 2, this.dummy.getStats().getLife(),
            "The enemy should get hit twice");
    }
}
