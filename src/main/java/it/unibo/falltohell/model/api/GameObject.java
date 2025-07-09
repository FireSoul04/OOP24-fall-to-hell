package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;

public interface GameObject {
    
    static final double TILE_SIZE = 20.0;
    
    /**
     * @return current position of this game object
     */
    Vector2 getPosition();

    /**
     * @return collider attached to this game object.
     */
    Collider getCollider();
    
    /**
     * @return the width of the game object
     */
    double getWidth();

    /**
     * @return the height of the game object
     */
    double getHeight();

    /**
     * @return true if the game object is solid, false otherwise
     */
    boolean isSolid();

    /**
     * @return the width of the game object in terms of tile size
     */
    double getWidthSize();

    /**
     * @return the height of the game object in terms of tile size
     */
    double getHeightSize();
    /**
     * @param vector2
     *           the new position of this game object
     */
    void setPosition(Vector2 vector2);

    /**
     * This function is called every collision with another game object.
     * @param other game object collided with
     */
    void onCollision(GameObject other);

    /**
     * This function is called every collision with another game object.
     * @param other game object collided with
     * @param direction where the collision happened
     */
    void onCollision(GameObject other, Vector2 direction);
    /**
     * @return the level this game object belongs to
     */
    Level getLevel();
}
