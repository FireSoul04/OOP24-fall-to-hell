package it.unibo.falltohell.model.api.gameobject.movable;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.util.Vector2;
/**
 * Represents a movable game object that can change its position based on speed.
 * This interface extends the {@link GameObject} interface and adds methods for
 * updating the position based on speed and time, as well as getting and setting
 * the speed of the object.
 *
 * @author Casadei Lorenzo
 */
public interface Movable extends GameObject {

    /**
     * Updates the position of the object based on its speed and the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update
     */
    void update(double deltaTime);

    /**
     * Returns the speed of the object.
     *
     * @return the speed
     */
    Vector2 getSpeed();

    /**
     * Sets the speed of the object.
     *
     * @param speed the new speed
     */
    void setSpeed(Vector2 speed);

    /**
     * @return if the entity is facing right
     */
    boolean isFacingRight();


}
