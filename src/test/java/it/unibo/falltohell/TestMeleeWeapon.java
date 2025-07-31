package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.factory.StatisticsFactory;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.BaseCharacter;
import it.unibo.falltohell.model.impl.gameobject.weapons.BaseMeleeWeapon;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.DummyEnemyTest;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.TimerManagerTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

/**
 * Test to check if close ranged weapon works correctly.
 *
 * @author Davide Mancini
 */
class TestMeleeWeapon {

    private static final Vector2 POSITION = Vector2.zero();
    private static final double LIFE = 10;
    private static final double MANA = 1;
    private static final Vector2 SPEED = Vector2.zero();
    private static final double ATTACK = 1;
    private static final double ATTACK_SPEED = 1;
    private static final double DAMAGE_MULTIPLIER = 1;
    private static final Dimensions SIZE = new Dimensions(20, 20);
    private static final long COOLDOWN = 400;
    private static final long TIMEOUT = 1000;

    private Weapon sword;
    private Enemy dummy;
    private TimerManagerTest tm;

    /**
     * Initiate the level, character, weapon and dummy.
     * Equips the weapon to the character.
     */
    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        final CharacterStatistics statistics = new StatisticFactoryImpl().createCharacterStatistic(
            LIFE, ATTACK, SPEED, SIZE, MANA, ATTACK_SPEED
        );
        final Character character = new BaseCharacter(level, POSITION, statistics, "test.png") {
            @Override
            public CharacterID getCharacterID() {
                return null;
            }
        };
        level.linkGameData(new GameDataImpl(
            0,
            CharacterID.ROGUE,
            Map.of(CharacterID.ROGUE, character),
            POSITION
        ));
        this.sword = new BaseMeleeWeapon(character, new BoxCollider(), DAMAGE_MULTIPLIER, COOLDOWN, "test.png") {
        };
        character.equipWeapon(this.sword);
        final StatisticsFactory sf = new StatisticFactoryImpl();
        final BaseEnemyStatistics dummyStats = sf.createBaseEnemyStatistic(
            LIFE, ATTACK, Vector2.zero(), new Dimensions(20, 20),
            Vector2.zero(), 1, sf.createOptional()
        );
        this.dummy = new DummyEnemyTest(level, dummyStats);
        this.dummy.setPosition(Vector2.zero());
        this.tm = (TimerManagerTest) level.getTimerManager();
    }

    /**
     * Test if the weapon deals the correct amount of damage to the dummy.
     */
    @Test
    void testDamageOnEnemy() {
        final double initialLife = this.dummy.getStats().getLife();
        this.sword.attack();
        this.sword.onCollision(this.dummy, Vector2.zero());
        Assertions.assertEquals(
            initialLife - ATTACK * DAMAGE_MULTIPLIER,
            this.dummy.getStats().getLife(),
            "The enemy should be hit and take damage"
        );
    }

    /**
     * Test if the cooldown is working correctly.
     */
    @Test
    void testCannotAttackTooFast() {
        final double initialLife = this.dummy.getStats().getLife();
        this.sword.attack();
        this.sword.onCollision(this.dummy, Vector2.zero());
        Assertions.assertTrue(initialLife > this.dummy.getStats().getLife(),
            "The enemy should take a hit");
        final double lifeAfterAttack = this.dummy.getStats().getLife();
        this.sword.attack();
        this.sword.onCollision(this.dummy, Vector2.zero());
        Assertions.assertEquals(0, Double.compare(lifeAfterAttack, this.dummy.getStats().getLife()),
            "The enemy should get hit just once");
    }

    /**
     * Test if the weapon can attack only after its cooldown.
     */
    @Test
    void testCanAttackOnlyAfterCooldown() {
        final double initialLife = this.dummy.getStats().getLife();
        this.sword.attack();
        this.sword.onCollision(this.dummy, Vector2.zero());
        this.tm.waitForTimer("weapon-cooldown" + this.sword.hashCode(), TIMEOUT);
        this.sword.attack();
        this.sword.onCollision(this.dummy, Vector2.zero());
        Assertions.assertTrue(initialLife > this.dummy.getStats().getLife(),
            "The enemy should get hit at least once");
    }
}
