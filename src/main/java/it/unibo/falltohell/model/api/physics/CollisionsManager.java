package it.unibo.falltohell.model.api.physics;

import it.unibo.falltohell.model.api.GameObject;

import java.util.List;

public interface CollisionsManager {

    /**
     * Check if any collision of these game objects is happening using checkCollision's algorithm
     * @param gameObjects to check
     */
    void checkCollisions(List<GameObject> gameObjects);
}
