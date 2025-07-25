package it.unibo.falltohell.model.api.gameobjects.movable;

import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.api.GameObject;

/**
 * Represents a projectile in the game that can move and hit with other game objects.
 * <p>
 * This interface extends the {@link Movable} interface and adds methods for handling
 * hit status and collision with other game objects.
 * </p>
 *
 * @author Casadei Lorenzo
 */
public interface Projectile extends Movable {

    /**
     * Returns whether the projectile has hit something.
     *
     * @return {@code true} if the projectile has hit, {@code false} otherwise
     */
    boolean isHit();

    /**
     * Sets the hit status of the projectile.
     *
     * @param hit {@code true} if the projectile has hit something, {@code false}
     *            otherwise
     */
    void setHit(boolean hit);

    /**
     * Updates the projectile's position if it has not hit anything.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    void update(double deltaTime);

    /**
     * Handles collision with another game object.
     * If the other object is solid and the projectile has not already hit, marks
     * this projectile as hit
     *
     * @param other the other game object involved in the collision
     */
    void onCollision(GameObject other);

}
