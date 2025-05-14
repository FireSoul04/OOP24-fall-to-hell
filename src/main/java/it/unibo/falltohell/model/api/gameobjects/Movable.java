package it.unibo.falltohell.model.api.gameobjects;

import it.unibo.falltohell.model.api.GameObject;

public interface Movable extends GameObject {
    
    /**
     * @param deltaTime elapsed time between the current frame and the last one
     */
    void update(double deltaTime);

    double getSpeedX();
    
    double getSpeedY();

    void setSpeedX(double speedX);
    
    void setSpeedY(double speedY);
}
