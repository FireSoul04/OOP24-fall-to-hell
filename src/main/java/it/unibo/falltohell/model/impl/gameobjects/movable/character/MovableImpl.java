package it.unibo.falltohell.model.impl.gameobjects.movable.character;

import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.Collider;
import it.unibo.falltohell.model.api.gameobjects.Movable;
public class MovableImpl extends GameObjectImpl implements Movable{
    private double speedX;
    private double speedY;
    
    public MovableImpl(Vector2 vector2, double width, double height, double speedX, double speedY, Collider collider) {
        super(vector2, width, height, collider);
        this.speedX = speedX;
        this.speedY = speedY;
    }
    
    public void update(double deltaTime) {
        Vector2 displacement = new Vector2(speedX, speedY).multiply(deltaTime);
        setPosition(getPosition().add(displacement));
        
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
