package it.unibo.falltohell;

import it.unibo.falltohell.model.impl.GameCameraImpl;
import it.unibo.falltohell.model.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameCameraImpl class.
 * These tests verify that the camera:
 *   Follows the player and moves towards the expected target position.
 *   Is correctly clamped within the level boundaries.
 *   Positions itself at the top-left corner when the player is there.
 * A small delta is used in assertions to account for floating-point precision.
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

    @BeforeEach
    public void setUp() {
        camera = new GameCameraImpl(initialPosition,cameraWidth, cameraHeight, followSpeed, levelWidth, levelHeight);
    }
    @Test
    void testCameraFollowsPlayer() {
        Vector2 playerPosition = new Vector2(20, 20);
        camera.updateCamera(playerPosition, 1.0);
        
        Vector2 expectedTarget = new Vector2(playerPosition.x() - cameraWidth / 2, playerPosition.y() - cameraHeight / 2);

        assertEquals(expectedTarget.x(), camera.getCameraPosition().x(), 0.0001, "Camera x not at expected position");
        assertEquals(expectedTarget.y(), camera.getCameraPosition().y(), 0.0001, "Camera y not at expected position");
    }
    @Test
    void testCameraClampedToLevelBounds() {
        Vector2 playerPosition = new Vector2(99, 99);
        camera.updateCamera(playerPosition, 1.0);

        double maxX = levelWidth - cameraWidth;
        double maxY = levelHeight - cameraHeight;

        Vector2 cameraPos = camera.getCameraPosition();

        assertEquals(maxX, cameraPos.x(), 0.0001, "Camera x non clampata correttamente");
        assertEquals(maxY, cameraPos.y(), 0.0001, "Camera y non clampata correttamente");
    }
    @Test
    void testCameraAtTopLeftCorner() {
        Vector2 playerPosition = new Vector2(0, 0); 
        camera.updateCamera(playerPosition, 1.0);
        assertEquals(0.0, camera.getCameraPosition().x(), 0.0001);
        assertEquals(0.0, camera.getCameraPosition().y(), 0.0001);
    }
    
}
