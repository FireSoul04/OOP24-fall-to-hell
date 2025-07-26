package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.gameobject.GameObject;

import java.util.List;

/**
 * Class that check the collisions between a list of game objects.
 *
 * @author Davide Mancini
 */
public interface CollisionsManager {

    /**
     * Check if any collision of these game objects is happening using checkCollision's algorithm.
     *
     * @param gameObjects to check
     */
    void checkCollisions(List<GameObject> gameObjects);
}
