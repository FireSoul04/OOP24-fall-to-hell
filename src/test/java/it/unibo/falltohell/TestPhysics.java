package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.Statistics;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.EntityImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test to check if physics of entities works correctly.
 *
 * @author Davide Mancini
 */
class TestPhysics {

    private static final int MAX_UPDATES = 100;
    private static final Vector2 BLOCK_POSITION = Vector2.down().multiply(GameObject.TILE_SIZE);
    private static final Vector2 RIGHT_TARGET_POSITION = Vector2.right().multiply(MAX_UPDATES);
    private static final Vector2 LEFT_TARGET_POSITION = Vector2.left().multiply(MAX_UPDATES);
    private static final Vector2 ENTITY_POSITION = Vector2.zero();
    private static final double LIFE = 1;
    private static final double MANA = 1;
    private static final Vector2 SPEED = Vector2.zero();
    private static final double ATTACK = 1;
    private static final double ATTACK_SPEED = 1;
    private static final Dimensions SIZE = new Dimensions(20, 20);
    private static final Statistics STATS = new StatisticFactoryImpl().createCharacterStatistic(
        LIFE, ATTACK, SPEED, SIZE, MANA, ATTACK_SPEED
    );
    private static final double EPS = 1e-1;
    private static final String MOVE_RIGHT_EVENT = "MoveRight";
    private static final String MOVE_LEFT_EVENT = "MoveLeft";

    private Entity entity;
    private Level level;
    private int updates;
    private boolean collision;

    /**
     * Initiate all variables for the test.
     */
    @BeforeEach
    void initialization() {
        this.level = new LevelTest();
        this.entity = new EntityImpl(this.level, ENTITY_POSITION, STATS) {
            @Override
            public void update(final double deltaTime) {
                super.update(deltaTime);
                if (level.checkCondition(MOVE_RIGHT_EVENT)) {
                    this.setPosition(this.getPosition().add(Vector2.right()));
                }
                if (level.checkCondition(MOVE_LEFT_EVENT)) {
                    this.setPosition(this.getPosition().add(Vector2.left()));
                }
            }
        };
        new GameObjectImpl(this.level, RIGHT_TARGET_POSITION, new BoxCollider()) {
            @Override
            public void onCollision(final GameObject other, final Vector2 direction) {
                collision = true;
            }
        };
        new GameObjectImpl(this.level, LEFT_TARGET_POSITION, new BoxCollider()) {
            @Override
            public void onCollision(final GameObject other, final Vector2 direction) {
                collision = true;
            }
        };
        this.updates = 0;
        this.collision = false;
        for (int x = -MAX_UPDATES; x < MAX_UPDATES; x = x + (int) GameObject.TILE_SIZE) {
            new BaseCollidableBlock(
                this.level, BLOCK_POSITION.add(Vector2.right().multiply(x)), new BoxCollider(), "test.png"
            );
        }
    }

    /**
     * Method that updates the level MAX_UPDATES times.
     */
    private void updateTest() {
        while (this.updates < MAX_UPDATES) {
            this.level.update(1.0);
            this.updates++;
        }
    }

    /**
     * Test to check if the entity will fall while in midair.
     */
    @Test
    void testApplyGravityMidAir() {
        this.entity.setPosition(Vector2.up().multiply(GameObject.TILE_SIZE).multiply(10));
        this.level.update(1.0);
        this.level.update(1.0);
        Assertions.assertNotEquals(ENTITY_POSITION, this.entity.getPosition());
    }

    /**
     * Test to check if the entity will not fall while on ground.
     */
    @Test
    void testApplyNoGravityOnGround() {
        this.updateTest();
        Assertions.assertTrue(Math.abs(ENTITY_POSITION.y() - this.entity.getPosition().y()) < EPS);
    }

    /**
     * Test to check if the entity will not reach the right target with an obstacle in the middle
     * while going right and same with a left target.
     */
    @Test
    void testGoingToTargetsWithObstacles() {
        this.level.addCondition(MOVE_RIGHT_EVENT, () -> true);
        final Vector2 rightObstaclePosition = Vector2.right().multiply(MAX_UPDATES).divide(2);
        new BaseCollidableBlock(this.level, rightObstaclePosition, new BoxCollider(), "test.png");
        this.updateTest();
        Assertions.assertFalse(this.collision, "The entity should not reach the target on the right");
        this.level.addCondition(MOVE_RIGHT_EVENT, () -> false);
        this.level.addCondition(MOVE_LEFT_EVENT, () -> true);
        this.entity.setPosition(ENTITY_POSITION);
        final Vector2 leftObstaclePosition = Vector2.left().multiply(MAX_UPDATES).divide(2);
        new BaseCollidableBlock(this.level, leftObstaclePosition, new BoxCollider(), "test.png");
        this.updates = 0;
        this.updateTest();
        Assertions.assertFalse(this.collision, "The entity should not reach the target on the left");
    }

    /**
     * Test if the entity can reach the right target while going right and same with the left target.
     */
    @Test
    void testGoingToTargetsWithoutObstacles() {
        this.level.addCondition(MOVE_RIGHT_EVENT, () -> true);
        this.updateTest();
        Assertions.assertTrue(this.collision, "The entity should reach the target on the right");
        this.level.addCondition(MOVE_RIGHT_EVENT, () -> false);
        this.level.addCondition(MOVE_LEFT_EVENT, () -> true);
        this.entity.setPosition(ENTITY_POSITION);
        this.updates = 0;
        this.updateTest();
        Assertions.assertTrue(this.collision, "The entity should reach the target on the left");
    }
}
