package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.factory.StatisticsFactory;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.BaseEnemyProjectile;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.Knife;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.DummyEnemyTest;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

/**
 * Class for testing the abilities of the character rogue.
 *
 * @author Davide Mancini
 */
class TestRogueAbilities {

    private static final Vector2 ROGUE_POSITION = Vector2.zero();
    private static final Vector2 DUMMY_POSITION = Vector2.zero();
    private static final double DUMMY_LIFE = 20;
    private static final double DUMMY_ATTACK = 1;
    private static final Vector2 DUMMY_SPEED = Vector2.zero();
    private static final int DUMMY_POINTS = 1;
    private static final Dimensions DUMMY_SIZE = new Dimensions(20, 20);
    private static final StatisticsFactory SF = new StatisticFactoryImpl();

    private Character rogue;
    private Level level;
    private boolean canShoot;
    private BaseEnemyStatistics enemyStats;

    /**
     * Initiate all variables for the test.
     */
    @BeforeEach
    void initialization() {
        this.level = new LevelTest();
        this.level.addCondition("ActiveAbility", () -> this.canShoot);
        this.rogue = new Rogue(this.level, ROGUE_POSITION);
        // Floor for rogue
        new BaseCollidableBlock(
            this.level,
            ROGUE_POSITION.add(Vector2.down().multiply(GameObject.TILE_SIZE)),
            new BoxCollider(),
            "test.png"
        );
        this.level.linkGameData(new GameDataImpl(Map.of(this.rogue.getCharacterID(), this.rogue)));
        this.canShoot = true;
        this.enemyStats = SF.createBaseEnemyStatistic(
            DUMMY_LIFE, DUMMY_ATTACK, DUMMY_SPEED, DUMMY_SIZE,
            DUMMY_POSITION, DUMMY_POINTS, SF.createOptional()
        );
    }

    /**
     * Test to check if the knives are not colliding with each other and spread in the correct directions.
     */
    @Test
    void testKnifeThrowDirections() {
        this.level.update(1.0);
        this.canShoot = false;
        final List<Vector2> startingKnivesPositions = this.level.getGameObjects()
            .stream()
            .filter(t -> t instanceof Knife)
            .map(GameObject::getPosition)
            .toList();
        this.level.update(1.0);
        final List<Vector2> knives = this.level.getGameObjects()
            .stream()
            .filter(t -> t instanceof Knife)
            .map(GameObject::getPosition)
            .toList();
        Assertions.assertTrue(
            knives.get(0).x() > startingKnivesPositions.get(0).x()
                && Double.compare(knives.get(0).y(), startingKnivesPositions.get(0).y()) == 0,
            "First knife should move only in the x axes"
        );
        Assertions.assertTrue(
            knives.get(1).x() > startingKnivesPositions.get(1).x()
                && knives.get(1).y() > startingKnivesPositions.get(1).y(),
            "Second knife should move down and right"
        );
        Assertions.assertTrue(
            knives.get(2).x() > startingKnivesPositions.get(2).x()
                && knives.get(2).y() < startingKnivesPositions.get(2).y(),
            "Third knife should move up and right"
        );
    }

    /**
     * Test if thrown knives are dealing damage.
     */
    @Test
    void testKnifeDamageOnEnemy() {
        final Enemy dummy = new DummyEnemyTest(this.level, enemyStats);
        dummy.setPosition(Vector2.zero());
        final double initialLife = dummy.getStats().getLife();
        this.level.update(1.0);
        Assertions.assertTrue(dummy.getStats().getLife() < initialLife, "The enemy should be hit and take damage");
    }

    /**
     * Test to check if the rogue activates the evade ability when it takes damage from an enemy.
     */
    @Test
    void testEvadePassiveAbilityWithEnemy() {
        final Enemy dummy = new DummyEnemyTest(this.level, enemyStats);
        this.rogue.onCollision(dummy, Vector2.zero());
        Assertions.assertTrue(
            this.rogue.getStats().getInitialSpeed().magnitude() < this.rogue.getStats().getSpeed().magnitude(),
            "The rogue should have his speed buffed"
        );
    }

    /**
     * Test to check if the rogue activates the evade ability when it takes damage from an enemy projectile.
     */
    @Test
    void testEvadePassiveAbilityWithEnemyProjectile() {
        final Projectile projectile =
            new BaseEnemyProjectile(this.level, ROGUE_POSITION, Vector2.zero(), new BoxCollider(), 1, "test.png");
        this.rogue.onCollision(projectile, Vector2.zero());
        Assertions.assertTrue(
            this.rogue.getStats().getInitialSpeed().magnitude() < this.rogue.getStats().getSpeed().magnitude(),
            "The rogue should have his speed buffed"
        );
    }

    /**
     * Test to check if the rogue doesn't active passive ability when not hit.
     */
    @Test
    void testEvadePassiveAbilityWithoutBeingHit() {
        this.level.update(1.0);
        Assertions.assertEquals(
            this.rogue.getStats().getInitialSpeed(),
            this.rogue.getStats().getSpeed(),
            "The rogue should have not his speed buffed"
        );
    }

    /**
     * Update the rogue until he reaches the max jump height.
     */
    private void jumpUntilMaxHeightIsReached() {
        double lastFrameY;
        do {
            lastFrameY = this.rogue.getPosition().y();
            this.level.update(1.0);
        } while (this.rogue.getPosition().y() < lastFrameY);
    }

    /**
     * Test if the rogue's double jump works correctly.
     */
    @Test
    void testDoubleJump() {
        final double startY = this.rogue.getPosition().y();
        this.level.addCondition("Jump", () -> true);
        this.level.update(1.0);
        this.jumpUntilMaxHeightIsReached();
        Assertions.assertTrue(
            this.rogue.getPosition().y() < startY,
            "The rogue should go up when jumping"
        );

        final double releasedJumpY = this.rogue.getPosition().y();
        // Stop jumping
        this.level.addCondition("Jump", () -> false);
        this.level.update(1.0);
        Assertions.assertTrue(
            this.rogue.getPosition().y() > releasedJumpY,
            "The rogue should go down when jump reaches max height"
        );

        // Start the double jump and updates at least two times to reset the jump
        this.level.addCondition("Jump", () -> true);
        this.level.update(1.0);
        this.level.update(1.0);
        this.jumpUntilMaxHeightIsReached();
        Assertions.assertTrue(
            this.rogue.getPosition().y() < releasedJumpY,
            "The rogue should go up when double jumping"
        );
    }
}
