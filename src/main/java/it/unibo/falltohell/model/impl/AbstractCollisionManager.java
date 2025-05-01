package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.CollisionManager;
import it.unibo.falltohell.model.api.GameObject;

import java.util.List;

public abstract class AbstractCollisionManager implements CollisionManager {

    @Override
    public void checkCollisions(List<GameObject> gameObjects) {
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
