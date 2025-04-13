package it.unibo.falltohell.model.api;

public interface Movable extends GameObject {
    
    /**
     * @param deltaTime elapsed time between the current frame and the last one
     */
    void move(double deltaTime);
}
