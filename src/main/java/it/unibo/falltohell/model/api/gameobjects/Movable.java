package it.unibo.falltohell.model.api.gameobjects;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.util.Vector2;

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

}
