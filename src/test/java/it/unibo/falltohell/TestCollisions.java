package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.test.util.LevelTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Vector2;

import java.util.function.Supplier;

import org.junit.jupiter.api.Assertions;

/**
 * Test for collisions between gameobjects.
 * This test covers both a dynamic game object moving to a static game object,
 * two game objects going to each other and a game object that doesn't collide with a block.
 *
 * @author Davide Mancini
 */
class TestCollisions {

    private static final int STEPS = 500;

    private final Level fakeLevel = new LevelTest();

    private boolean collision;
    private boolean exitedCollision;
    private Vector2 direction;

    private final Movable dummy1 = new MovableImpl(
            fakeLevel,
            Vector2.zero(),
            Vector2.one(),
            new BoxCollider()
        ) {
        @Override
        public void onCollision(final GameObject other, final Vector2 dir) {
            collision = true;
            direction = dir;
        }
        @Override
        public void onCollisionExit(final GameObject other, final Vector2 direction) {
            exitedCollision = true;
        }
    };
    private final GameObject dummy2 = new MovableImpl(
        fakeLevel,
        Vector2.zero(),
        Vector2.one().invert(),
        new BoxCollider()
    ) {
    };
    private final GameObject block = new GameObjectImpl(
        fakeLevel,
        new Vector2(200, 200),
        true,
        new BoxCollider()
    );

    /**
     * Before every test, the game objects are added to a fake level because every game object
     * are added inside the level passed inside the constructor.
     * The tests want to add manually the game objects needed, so every test has its level.
     */
    @BeforeEach
    void initialize() {
        collision = false;
        exitedCollision = false;
        direction = Vector2.zero();
        dummy1.setPosition(Vector2.zero());
    }

    /**
     * Base method to test.
     * Uses a number of steps to determine if a collision is going to happen.
     * If it happens, collision and direction are set accordingly.
     * @param level where it needs to check collisions
     * @param additionalStopCondition if a test needs an additional condition for concluding early
     */
    void baseCollisionTest(final Level level, final Supplier<Boolean> additionalStopCondition) {
        int steps = 0;
        while (steps < STEPS && additionalStopCondition.get()) {
            level.update(1.0);
            steps++;
        }
    }

    /**
     * Test that check if a dummy going into a block is going to collide.
     */
    @Test
    void testGameDummyVsBlock() {
        final Level level = new LevelTest();
        final Vector2 blockPosition = new Vector2(STEPS / 2.0, STEPS / 2.0);
        block.setPosition(blockPosition);
        level.addGameObject(dummy1);
        level.addGameObject(block);
        baseCollisionTest(level, () -> true);
        Assertions.assertTrue(collision, "Dummy should collide in 500 steps");
        Assertions.assertTrue(exitedCollision, "Dummy should have exited collision");
    }

    /**
     * Test that check if two dummies going into each other are going to collide.
     */
    @Test
    void testGameDummyVsGameDummy() {
        final Level level = new LevelTest();
        dummy2.setPosition(new Vector2(STEPS / 2.0, STEPS / 2.0));
        level.addGameObject(dummy1);
        level.addGameObject(dummy2);
        baseCollisionTest(level, () -> true);
        Assertions.assertTrue(collision, "Dummy should collide in 500 steps");
        Assertions.assertTrue(exitedCollision, "Dummy should have exited collision");
    }

    /**
     * Test that check if a dummy moving close to a block is not going to collide.
     */
    @Test
    void testGameDummyShouldNotCollide() {
        final Level level = new LevelTest();
        final Vector2 blockPosition = new Vector2(STEPS / 2.0 + 50, STEPS / 2.0);
        block.setPosition(blockPosition);
        level.addGameObject(dummy1);
        level.addGameObject(block);
        baseCollisionTest(level, () -> true);
        Assertions.assertFalse(collision, "Dummy should not collide");
        Assertions.assertFalse(exitedCollision, "Dummy should have never collided");
    }

    /**
     * Test to check if the collision direction is correct on the x axis.
     */
    @Test
    void testCollisionDirectionX() {
        dummy1.setSpeed(Vector2.right());
        block.setPosition(new Vector2(STEPS / 2.0, 0));
        final Level testCollisionDirectionXLevel = new LevelTest();
        testCollisionDirectionXLevel.addGameObject(dummy1);
        testCollisionDirectionXLevel.addGameObject(block);
        baseCollisionTest(testCollisionDirectionXLevel, () -> !collision);
        Assertions.assertEquals(direction, Vector2.right(), "Collision direction should be right");
        dummy1.setPosition(new Vector2(STEPS, 0));
        collision = false;
        direction = Vector2.zero();
        dummy1.setSpeed(new Vector2(dummy1.getSpeed().x() * -1, dummy1.getSpeed().y()));
        baseCollisionTest(testCollisionDirectionXLevel, () -> !collision);
        Assertions.assertEquals(direction, Vector2.left(), "Collision direction should be left");
    }

    /**
     * Test to check if the collision direction is correct on the y axis.
     */
    @Test
    void testCollisionDirectionY() {
        dummy1.setSpeed(Vector2.down());
        block.setPosition(new Vector2(0, STEPS / 2.0));
        final Level testCollisionDirectionYLevel = new LevelTest();
        testCollisionDirectionYLevel.addGameObject(dummy1);
        testCollisionDirectionYLevel.addGameObject(block);
        baseCollisionTest(testCollisionDirectionYLevel, () -> !collision);
        Assertions.assertEquals(direction, Vector2.down(), "Collision direction should be down");
        dummy1.setPosition(new Vector2(0, STEPS));
        collision = false;
        direction = Vector2.zero();
        dummy1.setSpeed(new Vector2(dummy1.getSpeed().x(), dummy1.getSpeed().y() * -1));
        baseCollisionTest(testCollisionDirectionYLevel, () -> !collision);
        Assertions.assertEquals(direction, Vector2.up(), "Collision direction should be up");
    }

    /**
     * Test to check if the collision direction is correct on the both x and y axes.
     */
    @Test
    void testCollisionDirectionXandY() {
        dummy1.setSpeed(Vector2.one());
        block.setPosition(new Vector2(STEPS / 2.0, STEPS / 2.0));
        final Level testCollisionDirectionXandYLevel = new LevelTest();
        testCollisionDirectionXandYLevel.addGameObject(dummy1);
        testCollisionDirectionXandYLevel.addGameObject(block);
        baseCollisionTest(testCollisionDirectionXandYLevel, () -> !collision);
        Assertions.assertEquals(direction, Vector2.right(), "Collision direction should be right");
        dummy1.setPosition(new Vector2(STEPS, STEPS));
        collision = false;
        direction = Vector2.zero();
        dummy1.setSpeed(dummy1.getSpeed().invert());
        baseCollisionTest(testCollisionDirectionXandYLevel, () -> !collision);
        Assertions.assertEquals(direction, Vector2.left(), "Collision direction should be left");
    }
}
