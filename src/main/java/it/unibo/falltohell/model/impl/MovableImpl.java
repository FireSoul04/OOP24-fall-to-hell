package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Position;
import it.unibo.falltohell.model.api.gameobjects.Movable;
public class MovableImpl extends GameObjectImpl implements Movable{
    private double speedX;
    private double speedY;
    
    public MovableImpl(Position position, double width, double height, double speedX, double speedY) {
        super(position, width, height);
        this.speedX = speedX;
        this.speedY = speedY;
    }
    
    public void move(double deltaTime) {
        Position currentPos = getPosition();
        double newX = (currentPos.x() + speedX) * deltaTime;
        double newY = (currentPos.y() + speedY) * deltaTime; 
        setPosition(new Position(newX, newY));
        
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
