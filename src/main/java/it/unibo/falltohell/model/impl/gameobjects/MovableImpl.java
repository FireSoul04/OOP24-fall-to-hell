package it.unibo.falltohell.model.impl.gameobjects;

import java.util.Optional;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;
/**
 * Implementation of the {@link Movable} interface.
 * <p>
 * Represents a game object that can move within the level, with horizontal and vertical speed.
 * Provides methods to update its position based on speed and elapsed time, and to get or set its speed.
 * </p>
 */
public class MovableImpl extends GameObjectImpl implements Movable{
    private double speedX;
    private double speedY;
    /**
     * Constructs a movable game object.
     *
     * @param level the level to which this object belongs
     * @param position the initial position of the object
     * @param width the width of the object
     * @param height the height of the object
     * @param speedX the initial horizontal speed
     * @param speedY the initial vertical speed
     * @param collider the collider for this object
     */
    public MovableImpl(Level level, Vector2 position, double speedX, double speedY,Collider collider) {
        super(level, position, collider);
        this.speedX = speedX;
        this.speedY = speedY;
    }
    /**
     * {@inheritDoc}
     */
    public void update(double deltaTime) {
        Vector2 displacement = new Vector2(speedX, speedY).multiply(deltaTime);
        setPosition(getPosition().add(displacement));
    }
    /**
     * {@inheritDoc}
     */
    public double getSpeedX() {
        return speedX;
    }
    /**
     * {@inheritDoc}
     */
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }
    /**
     * {@inheritDoc}
     */
    public double getSpeedY() {
        return speedY;
    }
    /**
     * {@inheritDoc}
     */
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }
    
    
}
