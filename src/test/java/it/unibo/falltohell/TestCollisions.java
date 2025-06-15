package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Test for collisions between gameobjects.
 * This test covers both a dynamic game object moving to a static game object,
 * two game objects going to each other and a game object that doesn't collide with a block.
 *
 * @author Davide Mancini
 */
public class TestCollisions {

    final static int STEPS = 500;
    boolean collision;

    @BeforeEach
    void init() {
        collision = false;
    }

    /**
     * Base method to test.
     * Uses a number of steps to determine if a collision is going to happen.
     * If not, assert that it didn't collide.
     */
    void baseCollisionTest(final Level level) {
        int steps = 0;
        while (steps < STEPS) {
            level.update(1.0);
            steps++;
        }
    }

    @Test
    void testGameDummyVsBlock() {
        final Level testGameDummyVsBlockLevel = new LevelImpl();
        final GameObject dummy = new MovableImpl(
            testGameDummyVsBlockLevel, Vector2.zero(),
            0,
            0,
            10,
            10,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))
        ) {
            public void onCollision(final GameObject other) {
                collision = true;
            }
        };
        final GameObject block = new GameObjectImpl(
            testGameDummyVsBlockLevel,
            Vector2.one().multiply(100),
            0,
            0,
            true,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))) {
        };
        baseCollisionTest(testGameDummyVsBlockLevel);
        assertTrue(collision, "Dummy should collide in 2 seconds");
    }

    @Test
    void testGameDummyVsGameDummy() {
        final Level testGameDummyVsGameDummyLevel = new LevelImpl();
        final GameObject dummy1 = new MovableImpl(
            testGameDummyVsGameDummyLevel, Vector2.zero(),
            0,
            0,
            10,
            10,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))
        ) {
            public void onCollision(final GameObject other) {
                collision = true;
            }
        };
        final GameObject dummy2 = new MovableImpl(
            testGameDummyVsGameDummyLevel, Vector2.one().multiply(200),
            0,
            0,
            -10,
            -10,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))
        ) {
        };
        baseCollisionTest(testGameDummyVsGameDummyLevel);
        assertTrue(collision, "Dummy should collide in 2 seconds");
    }

    @Test
    void testGameDummyShouldNotCollide() {
        final Level testGameDummyShouldNotCollideLevel = new LevelImpl();
        final GameObject dummy = new MovableImpl(
            testGameDummyShouldNotCollideLevel, Vector2.zero(),
            0,
            0,
            10,
            10,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))
        ) {
            public void onCollision(final GameObject other) {
                collision = true;
            }
        };
        final GameObject block = new GameObjectImpl(
            testGameDummyShouldNotCollideLevel,
            Vector2.one().multiply(100).add(new Vector2(30, 0)),
            0,
            0,
            true,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))) {
        };
        baseCollisionTest(testGameDummyShouldNotCollideLevel);
        assertFalse(collision, "Dummy should not collide");
    }
}
