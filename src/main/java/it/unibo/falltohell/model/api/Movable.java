package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.GameObject;

public interface Movable extends GameObject {
    
    /**
     * @param deltaTime elapsed time between the current frame and the last one
     */
    void move(double deltaTime);
}
