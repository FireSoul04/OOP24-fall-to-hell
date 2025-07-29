package it.unibo.falltohell;

import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the GameCameraImpl class.
 * These tests verify that the camera:
 * Follows the player and moves towards the expected target position.
 * Is correctly clamped within the level boundaries.
 * Positions itself at the top-left corner when the player is there.
 * @author Lorenzo Casadei
 */
public class TestGameCamera {
    private GameCameraImpl camera;
    private final double cameraWidth = 10;
    private final double cameraHeight = 8;
    private final double followSpeed = 1.0;
    private final double levelWidth = 100;
    private final double levelHeight = 100;
    private final Vector2 initialPosition = new Vector2(0, 0);
    private final double deltaTime = 1.0;

    /**
     * The set up for the test.
     */
    @BeforeEach
    public void setUp() {
        camera = new GameCameraImpl(initialPosition, cameraWidth, cameraHeight, followSpeed);
        camera.setLevelSize(new Vector2(levelWidth, levelHeight));
    }

    /**
     * test if the gameCamera follow the player.
     */
    @Test
    void testCameraFollowsPlayer() {
        final double pPos = 20.0;
        final Vector2 playerPosition = new Vector2(pPos, pPos);
        camera.updateCamera(playerPosition, deltaTime);
        
        final Vector2 expectedTarget = new Vector2(playerPosition.x() - cameraWidth / 2, playerPosition.y() - cameraHeight / 2);

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
        camera.updateCamera(playerPosition, deltaTime);

        final double maxX = levelWidth - cameraWidth;
        final double maxY = levelHeight - cameraHeight;

        final Vector2 cameraPos = camera.getCameraPosition();

        assertEquals(maxX, cameraPos.x(), "Camera x not correctly clamped");
        assertEquals(maxY, cameraPos.y(),  "Camera y not correctly clamped");
    }

    /**
     * Test if the camera is clamped in the top left corner.
     */
    @Test
    void testCameraAtTopLeftCorner() {
        final Vector2 playerPosition = new Vector2(0, 0);
        camera.updateCamera(playerPosition, deltaTime);
        assertEquals(0.0, camera.getCameraPosition().x());
        assertEquals(0.0, camera.getCameraPosition().y());
    }
}
