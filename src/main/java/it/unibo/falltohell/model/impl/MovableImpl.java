package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Vector2;
import it.unibo.falltohell.model.api.gameobjects.Movable;
public class MovableImpl extends GameObjectImpl implements Movable{
    private double speedX;
    private double speedY;
    
    public MovableImpl(Vector2 vector2, double width, double height, double speedX, double speedY) {
        super(vector2, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
    }
    
    public void move(double deltaTime) {
        Vector2 currentPos = getPosition();
        double newX = (currentPos.x() + speedX) * deltaTime;
        double newY = (currentPos.y() + speedY) * deltaTime; 
        setPosition(new Vector2(newX, newY));
        
    }
    public double getSpeedX() {
        return speedX;
    }
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }
    public double getSpeedY() {
        return speedY;
    }
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }
    
    
}
