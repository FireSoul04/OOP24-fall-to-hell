package it.unibo.falltohell.model.api;

public interface Collider {

    /**
     * @param other
     * @return if this is colliding with another collider
     */
    boolean isColliding(Collider other);

    /**
     * @return the game object binded with this collider
     */
    GameObject getAttachedGameObject();
}
