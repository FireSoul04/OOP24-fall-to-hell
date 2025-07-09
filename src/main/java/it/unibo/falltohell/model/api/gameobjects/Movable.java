package it.unibo.falltohell.model.api.gameobjects;

import it.unibo.falltohell.model.api.GameObject;

public interface Movable extends GameObject {
    
    /**
     * Updates the position of the object based on its speed and the elapsed time.
     *
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    void update(double deltaTime);
    /**
     * Returns the horizontal speed of the object.
     *
     * @return the horizontal speed
     */
    double getSpeedX();
    /**
     * Returns the vertical speed of the object.
     *
     * @return the vertical speed
     */
    double getSpeedY();
    /**
     * Sets the horizontal speed of the object.
     *
     * @param speedX the new horizontal speed
     */
    void setSpeedX(double speedX);
    /**
     * Sets the vertical speed of the object.
     *
     * @param speedY the new vertical speed
     */
    void setSpeedY(double speedY);
}
