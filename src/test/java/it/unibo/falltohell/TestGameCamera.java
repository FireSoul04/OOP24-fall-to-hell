package it.unibo.falltohell;

import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the GameCameraImpl class.
 * These tests verify that the camera:
 * Follows the player and moves towards the expected target position.
 * Is correctly clamped within the level boundaries.
 * Positions itself at the top-left corner when the player is there.
 * @author Lorenzo Casadei
 */
class TestGameCamera {
    private static final double CAMERA_WIDTH = 10;
    private static final double CAMERA_HEIGHT = 8;
    private static final double FOLLOW_SPEED = 1.0;
    private static final double LEVEL_WIDTH = 100;
    private static final double LEVEL_HEIGHT = 100;
    private static final Vector2 INITIAL_POSITION = new Vector2(0, 0);
    private static final double DELTA_TIME = 1.0;
    private GameCameraImpl camera;

    /**
     * The set up for the test.
     */
    @BeforeEach
    public void setUp() {
        camera = new GameCameraImpl(INITIAL_POSITION, CAMERA_WIDTH, CAMERA_HEIGHT, FOLLOW_SPEED);
        camera.setLevelSize(new Vector2(LEVEL_WIDTH, LEVEL_HEIGHT));
    }

    /**
     * test if the gameCamera follow the player.
     */
    @Test
    void testCameraFollowsPlayer() {
        final double pPos = 20.0;
        final Vector2 playerPosition = new Vector2(pPos, pPos);
        camera.updateCamera(playerPosition, DELTA_TIME);
        final Vector2 expectedTarget = new Vector2(playerPosition.x() - CAMERA_WIDTH / 2, playerPosition.y() - CAMERA_HEIGHT / 2);
        assertEquals(expectedTarget.x(), camera.getCameraPosition().x(), "Camera x not at expected position");
        assertEquals(expectedTarget.y(), camera.getCameraPosition().y(), "Camera y not at expected position");
    }

    /**
     * Test if the game camera is clamped to the level boundaries
     * while the player is there.
     */
    @Test
    void testCameraClampedToLevelBounds() {
        final double pPos = 99;
        final Vector2 playerPosition = new Vector2(pPos, pPos);
        camera.updateCamera(playerPosition, DELTA_TIME);

        final double maxX = LEVEL_WIDTH - CAMERA_WIDTH;
        final double maxY = LEVEL_HEIGHT - CAMERA_HEIGHT;

        final Vector2 cameraPos = camera.getCameraPosition();

        assertEquals(maxX, cameraPos.x(), "Camera x not correctly clamped");
        assertEquals(maxY, cameraPos.y(),  "Camera y not correctly clamped");
    }

    /**
     * Test if the camera is clamped in the top left corner.
     */
    @Test
    void testCameraAtTopLeftCorner() {
        final Vector2 playerPosition = INITIAL_POSITION;
        camera.updateCamera(playerPosition, DELTA_TIME);
        assertEquals(0.0, camera.getCameraPosition().x());
        assertEquals(0.0, camera.getCameraPosition().y());
    }
}
