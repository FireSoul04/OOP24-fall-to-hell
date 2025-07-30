package it.unibo.falltohell.model.api.gameobject;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.drawable.Drawable;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

/**
 * Represents a game object in the game world.
 * This interface defines the basic properties and behaviors of a game object,
 * including its position, collider, solidity, and interactions with other game objects.
 *
 * @author Casadei Lorenzo
 */
public interface GameObject {
    /**
     * the size of the tiles.
     */
    double TILE_SIZE = 20.0;

    /**
     * @return current position of this game object
     */
    Vector2 getPosition();

    /**
     * @return collider attached to this game object if present.
     */
    Optional<Collider> getCollider();

    /**
     * @return true if the game object is solid, false otherwise
     */
    boolean isSolid();

    /**
     * Sets the solid state of this game object.
     *
     * @param solid true if the game object is solid, false otherwise
     */
    void setSolid(boolean solid);

    /**
     * @param vector2
     *                the new position of this game object
     */
    void setPosition(Vector2 vector2);

    /**
     * This function is called every collision with another game object.
     *
     * @param other     game object collided with
     * @param direction where the collision happened
     */
    void onCollision(GameObject other, Vector2 direction);

    /**
     * This function is called every time this game object stopped colliding with
     * another.
     *
     * @param other     game object was colliding with
     * @param direction where the collision happened
     */
    void onCollisionExit(GameObject other, Vector2 direction);

    /**
     * @return the level this game object belongs to
     */
    Level getLevel();

    /**
     * Method called every frame to update the game object's state.
     */
    void update();

    /**
     * @return the drawable attached to this object if present
     */
    Optional<Drawable> getDrawable();
}
