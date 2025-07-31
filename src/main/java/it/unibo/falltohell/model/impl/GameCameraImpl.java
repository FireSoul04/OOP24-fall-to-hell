package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of the {@link GameCamera} interface.
 * Represents the camera that follows the player within the game level.
 *
 * @author Casadei Lorenzo
 */
public class GameCameraImpl implements GameCamera {
    private Vector2 cameraPosition;
    private final double cameraWidth;
    private final double cameraHeight;
    private final double followSpeed;
    private Vector2 levelSize;

    /**
     * Constructor for the game camera.
     *
     * @param initialPosition the initial position of the camera
     * @param cameraWidth     the width of the visible area
     * @param cameraHeight    the height of the visible area
     * @param followSpeed     the speed at which the camera follows the player
     */
    public GameCameraImpl(final Vector2 initialPosition, final double cameraWidth, final double cameraHeight,
            final double followSpeed) {
        this.cameraPosition = initialPosition;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.followSpeed = followSpeed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevelSize(final Vector2 size) {
        this.levelSize = size;
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
    public void updateCamera(final Vector2 playerPosition, final double deltaTime) {
        if (this.levelSize == null) {
            throw new IllegalStateException("Level size must be set before updating the camera.");
        }
        final Vector2 targetPosition = new Vector2(
                playerPosition.x() - cameraWidth / 2,
                playerPosition.y() - cameraHeight / 2);
        final Vector2 difference = targetPosition.subtract(this.cameraPosition);

        this.cameraPosition = this.cameraPosition.add(difference.multiply(followSpeed * deltaTime));
        this.cameraPosition = new Vector2(
                Math.max(0, Math.min(this.cameraPosition.x(), levelSize.x() - cameraWidth)),
                Math.max(0, Math.min(this.cameraPosition.y(), levelSize.y() - cameraHeight)));
    }

}
