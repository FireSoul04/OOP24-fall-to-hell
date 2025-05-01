package it.unibo.falltohell.model.api;

import java.util.List;

public interface CollisionManager {

    /**
     * Check if any collision of these game objects is happening using checkCollision's algorithm
     * @param gameObjects to check
     */
    void checkCollisions(List<GameObject> gameObjects);
}
