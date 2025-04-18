package it.unibo.falltohell.model.impl.colliders;

import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.GameObject;

public class BoxCollider implements Collider {

    private final GameObject attachedGameObject;

    /**
     * Create a box collider binded with a game object.
     * @param attachedGameObject
     */
    public BoxCollider(final GameObject attachedGameObject) {
        this.attachedGameObject = attachedGameObject;
    }

    @Override
    public boolean isColliding(final Collider other) {
        // TODO Convex Polygon algorithm
        return false;
    }

    @Override
    public GameObject getAttachedGameObject() {
        return this.attachedGameObject;
    }
}
