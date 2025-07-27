package it.unibo.falltohell.model.api;

import it.unibo.falltohell.util.Vector2;

/**
 * Represents a game camera that follows the player and determines the visible area of the level
 * @author Casadei Lorenzo
 */
public interface GameCamera {
    /**
     * set the height of the level for the game camera.
     * @param levelHeight the height of the level.
     */
    void setLevelHigh(double levelHeight);

    /**
     * set the level width for the game camera.
     * @param levelWidth the width of the level.
     */
    void setLevelWidth(double levelWidth);
    /**
     * Updates the camera position based on the player position and the time elapsed
     * since the last update.
     *
     * @param playerPosition the current position of the player
     * @param deltaTime      the time elapsed since the last update
     */
    void updateCamera(Vector2 playerPosition, double deltaTime);

    /**
     * @return the current position of the camera
     */
    Vector2 getCameraPosition();

    /**
     * @return the width of the camera's view
     */
    double getCameraWidth();

    /**
     * @return the height of the camera's view
     */
    double getCameraHeight();

}
