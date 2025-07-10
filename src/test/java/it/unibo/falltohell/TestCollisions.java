package it.unibo.falltohell;

import it.unibo.falltohell.model.api.gameobjects.Movable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for collisions between gameobjects.
 * This test covers both a dynamic game object moving to a static game object,
 * two game objects going to each other and a game object that doesn't collide with a block.
 *
 * @author Davide Mancini
 */
class TestCollisions {

    private static final int STEPS = 500;

    private final Level fakeLevel = new LevelImpl();

    private boolean collision;
    private Vector2 direction;

    private final Movable dummy1 = new MovableImpl(
            fakeLevel,
            Vector2.zero(),
            0,
            0,
            1,
            1,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))
        ) {
        @Override
        public void onCollision(final GameObject other, final Vector2 dir) {
            collision = true;
            direction = dir;
        }
    };
    private final GameObject dummy2 = new MovableImpl(
        fakeLevel,
        Vector2.zero(),
        0,
        0,
        -1,
        -1,
        new BoxCollider(Vector2.zero(), new Dimensions(20, 20))
    ) {
    };
    private final GameObject block = new GameObjectImpl(
        fakeLevel,
        new Vector2(200, 200),
        0,
        0,
        true,
        new BoxCollider(Vector2.zero(), new Dimensions(20, 20))) {
    };

    /**
     * Before every test, the game objects are added to a fake level because every game object
     * are added inside the level passed inside the constructor.
     * The tests want to add manually the game objects needed, so every test has its level.
     */
    @BeforeEach
    void initialize() {
        collision = false;
        direction = Vector2.zero();
        dummy1.setPosition(Vector2.zero());
    }

    /**
     * Base method to test.
     * Uses a number of steps to determine if a collision is going to happen.
     * If it happens, collision and direction are set accordingly.
     * @param level where it needs to check collisions
     */
    void baseCollisionTest(final Level level) {
        int steps = 0;
        while (steps < STEPS && !collision) {
            level.update(1.0);
            steps++;
        }
    }

    @Test
    void testGameDummyVsBlock() {
        final Level level = new LevelImpl();
        final Vector2 blockPosition = new Vector2(STEPS / 2.0, STEPS / 2.0);
        block.setPosition(blockPosition);
        level.addGameObject(dummy1);
        level.addGameObject(block);
        baseCollisionTest(level);
        assertTrue(collision, "Dummy should collide in 500 steps");
    }

    @Test
    void testGameDummyVsGameDummy() {
        final Level level = new LevelImpl();
        dummy2.setPosition(new Vector2(STEPS / 2.0, STEPS / 2.0));
        level.addGameObject(dummy1);
        level.addGameObject(dummy2);
        baseCollisionTest(level);
        assertTrue(collision, "Dummy should collide in 500 steps");
    }

    @Test
    void testGameDummyShouldNotCollide() {
        final Level level = new LevelImpl();
        final Vector2 blockPosition = new Vector2(STEPS / 2.0 + 50, STEPS / 2.0);
        block.setPosition(blockPosition);
        level.addGameObject(dummy1);
        level.addGameObject(block);
        baseCollisionTest(level);
        assertFalse(collision, "Dummy should not collide");
    }

    @Test
    void testCollisionDirectionX() {
        dummy1.setSpeedX(1);
        dummy1.setSpeedY(0);
        block.setPosition(new Vector2(STEPS / 2.0, 0));
        final Level testCollisionDirectionXLevel = new LevelImpl();
        testCollisionDirectionXLevel.addGameObject(dummy1);
        testCollisionDirectionXLevel.addGameObject(block);
        baseCollisionTest(testCollisionDirectionXLevel);
        assertEquals(direction, Vector2.right(), "Collision direction should be right");
        dummy1.setPosition(new Vector2(STEPS, 0));
        collision = false;
        direction = Vector2.zero();
        dummy1.setSpeedX(dummy1.getSpeedX() * -1);
        baseCollisionTest(testCollisionDirectionXLevel);
        assertEquals(direction, Vector2.left(), "Collision direction should be left");
    }

    @Test
    void testCollisionDirectionY() {
        dummy1.setSpeedX(0);
        dummy1.setSpeedY(1);
        block.setPosition(new Vector2(0, STEPS / 2.0));
        final Level testCollisionDirectionYLevel = new LevelImpl();
        testCollisionDirectionYLevel.addGameObject(dummy1);
        testCollisionDirectionYLevel.addGameObject(block);
        baseCollisionTest(testCollisionDirectionYLevel);
        assertEquals(direction, Vector2.down(), "Collision direction should be down");
        dummy1.setPosition(new Vector2(0, STEPS));
        collision = false;
        direction = Vector2.zero();
        dummy1.setSpeedY(dummy1.getSpeedY() * -1);
        baseCollisionTest(testCollisionDirectionYLevel);
        assertEquals(direction, Vector2.up(), "Collision direction should be up");
    }

    @Test
    void testCollisionDirectionXandY() {
        dummy1.setSpeedX(1);
        dummy1.setSpeedY(1);
        block.setPosition(new Vector2(STEPS / 2.0, STEPS / 2.0));
        final Level testCollisionDirectionXandYLevel = new LevelImpl();
        testCollisionDirectionXandYLevel.addGameObject(dummy1);
        testCollisionDirectionXandYLevel.addGameObject(block);
        baseCollisionTest(testCollisionDirectionXandYLevel);
        assertEquals(direction, Vector2.right(), "Collision direction should be right");
        dummy1.setPosition(new Vector2(STEPS, STEPS));
        collision = false;
        direction = Vector2.zero();
        dummy1.setSpeedX(dummy1.getSpeedX() * -1);
        dummy1.setSpeedY(dummy1.getSpeedY() * -1);
        baseCollisionTest(testCollisionDirectionXandYLevel);
        assertEquals(direction, Vector2.left(), "Collision direction should be left");
    }
}
