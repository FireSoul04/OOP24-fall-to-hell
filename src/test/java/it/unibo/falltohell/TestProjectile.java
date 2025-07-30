package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.ProjectileImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
/**
 * A class that Test the projectile.
 * it controlls if the projectile hit the target.
 * @author Casadei Lorenzo
 */
class TestProjectile {
    private LevelTest level;
    private Collider collider;
    private ProjectileImpl projectile;

    /**
     * Set up for the test. 
     */
    @BeforeEach
    void setUp() {
        final int size = 5;
        level = new LevelTest();
        collider = new BoxCollider(new Dimensions(size, size));
        final Vector2 position = Vector2.zero();
        final Vector2 speed = Vector2.right();
        projectile = new ProjectileImpl(level, position, speed, collider, "projectile.png");
    }

    /**
     * Test if the projectile is setted hit before actually 
     * hitting something.
     */
    @Test
    void testInitialHitStatus() {
        assertFalse(projectile.isHit(), "The Projectile should not hit");
    }

    /**
     * Test if the projectile set the hit while colliding with something.
     */
    @Test
    void testOnCollisionWithEnemy() {
        final GameObject block = new BaseCollidableBlock(level, Vector2.right(), collider, "test.png");
        final int frame = 60;
        final double deltaTime = 0.016;
        for (int i = 0; i < frame; i++) {
            projectile.update(deltaTime);
        }
        projectile.onCollision(block, Vector2.right());
        assertTrue(projectile.isHit(), "The projectile should be setted as hit after the collision with an a block");
    }

    /**
     * Test if the projectile keep moving after is setted hit.
     */
    @Test
    void testMovementAfterHit() {
        projectile.setHit(true);
        final double deltaTime = 0.30;
        projectile.update(deltaTime);
        assertEquals(projectile.getPosition(), Vector2.zero());
    }


}
