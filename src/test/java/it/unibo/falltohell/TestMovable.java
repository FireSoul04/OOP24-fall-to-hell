package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
/**
 * Tests for the Movableimpl class. 
 * this tests verify if the movement works and if the value
 * of the speed and the position is correct.
 * @author Casadei Lorenzo
 */
public class TestMovable {
    private Level level;
    private Collider collider;
    private MovableImpl movable;
    /**
     * Set up for the tests.
     */
    @BeforeEach
    void setUp() {
        level = new LevelTest();
        collider = new BoxCollider(new Dimensions(5, 5));
        Vector2 position = new Vector2(0, 0);
        Vector2 speed = new Vector2(2, 0); 
        movable = new MovableImpl(level, position, speed, collider);
    }
    /**
     * Test if the return of the getters are correct.
     */
    @Test
    void testInitialValues() {
        assertEquals(Vector2.zero(), movable.getPosition());
        assertEquals(new Vector2(2, 0), movable.getSpeed());
        assertFalse(movable.isFacingRight());
    }
    /**
     * Simulate a movement and check if the movable object is 
     * in the expected position.
     */
    @Test
    void testMovement() {
        movable.update(0.5);
        final Vector2 expectedPosition = Vector2.right();
        assertEquals(expectedPosition, movable.getPosition());
    }
}
