package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.CollisionsManager;
import it.unibo.falltohell.model.api.GameObject;

import java.util.List;

/**
 * Base class for any type of collision manager.
 *
 * @author Davide Mancini
 */
public abstract class AbstractCollisionsManager implements CollisionsManager {

    @Override
    public void checkCollisions(final List<GameObject> gameObjects) {
        for (final GameObject g1 : gameObjects) {
            for (final GameObject g2 : gameObjects) {
                if (g1 != g2 && g1.isSolid() && g2.isSolid()) {
                    final boolean collided = this.determineCollision(g1, g2);

                    if (collided) {
                        g1.onCollide(g2);
                    }
                }
            }
        }
    }

    /**
     * @param g1 first game object
     * @param g2 second game object
     * @return if g1 and g2 colliders are overlapping
     */
    abstract boolean determineCollision(GameObject g1, GameObject g2);
}
