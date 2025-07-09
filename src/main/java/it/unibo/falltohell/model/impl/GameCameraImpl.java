package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.util.Vector2;
import java.lang.Math;
/**
 * Implementation of the {@link GameCamera} interface.
 * <p>
 * Represents the camera that follows the player within the game level.
 * The camera smoothly follows the player position, is clamped within the level boundaries,
 * and exposes methods to retrieve its position and visible area size.
 * </p>
 */
public class GameCameraImpl implements GameCamera{
    private Vector2 cameraPosition;
    private final double cameraWidth;
    private final double cameraHeight;
    private final double followSpeed;
    private final double levelWidth;
    private final double levelHeight;

    /**
     * Constructor for the game camera.
     * 
     * @param initialPosition the initial position of the camera
     * @param cameraWidth the width of the visible area
     * @param cameraHeight the height of the visible area
     * @param followSpeed the speed at which the camera follows the player
     * @param levelWidth the width of the level
     * @param levelHeight the height of the level
     */
    public GameCameraImpl(Vector2 initialPosition, double cameraWidth, double cameraHeight, double followSpeed, double levelWidth, double levelHeight) {
        this.cameraPosition = initialPosition;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.followSpeed = followSpeed;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getCameraPosition() {
        return this.cameraPosition;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public double getCameraWidth() {
        return this.cameraWidth;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public double getCameraHeight() {
        return this.cameraHeight;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCamera(Vector2 playerPosition, double deltaTime) {
        Vector2 targetPosition = new Vector2(
            playerPosition.x() - cameraWidth / 2,
            playerPosition.y() - cameraHeight / 2
        );
        Vector2 difference = targetPosition.subtract(this.cameraPosition);

        this.cameraPosition = this.cameraPosition.add(difference.multiply(followSpeed * deltaTime));
        this.cameraPosition = new Vector2(
            Math.max(0, Math.min(this.cameraPosition.x(), levelWidth - cameraWidth)),
            Math.max(0, Math.min(this.cameraPosition.y(), levelHeight - cameraHeight))
        );
    }

}
