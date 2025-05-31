package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.GameObjectImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.MovableImpl;
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
    
    Level level;
    GameObject dummy1;
    GameObject dummy2;
    GameObject block;
    boolean collision;

    @BeforeEach
    void init() {
        level = new LevelImpl(null);
        dummy1 = new MovableImpl(
            level, Vector2.zero(),
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
        dummy2 = new MovableImpl(
            level, Vector2.one().multiply(200),
            0,
            0,
            -10,
            -10,
            new BoxCollider(Vector2.zero(), new Dimensions(20, 20))
        ) {
            public void onCollision(final GameObject other) {
                collision = true;
            }
        };
        block = new GameObjectImpl(level, Vector2.one().multiply(100), 0, 0, true, new BoxCollider(Vector2.zero(), new Dimensions(20, 20))) {
        };
        collision = false;
    }

    /**
     * Base method to test.
     * Uses a timer to determine if a collision is going to happen it should between 2 seconds.
     * If not, assert that it didn't collide.
     */
    void baseCollisionTest() {
        final long time = System.currentTimeMillis();
        // If dummy doesn't collide within 2 seconds throws an exception
        while (System.currentTimeMillis() - time < 2000) {
            level.update(1.0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertTrue(collision, "Dummy should collide in 2 seconds");
    }

    @Test
    void testGameDummyVsBlock() {
        level.addGameObject(dummy1);
        level.addGameObject(block);
        baseCollisionTest();
    }

    @Test
    void testGameDummyVsGameDummy() {
        level.addGameObject(dummy1);
        level.addGameObject(dummy2);
        baseCollisionTest();
    }

    @Test
    void testGameDummyShouldNotCollide() {
        level.addGameObject(dummy1);
        block.setPosition(block.getPosition().add(new Vector2(30, 0)));
        level.addGameObject(block);
        final long time = System.currentTimeMillis();
        // If dummy does collide within 2 seconds throws an exception
        while (System.currentTimeMillis() - time < 2000) {
            level.update(1.0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(collision, "Dummy should not collide");
    }
}
