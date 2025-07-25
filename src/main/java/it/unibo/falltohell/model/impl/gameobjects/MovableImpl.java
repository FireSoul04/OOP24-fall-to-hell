package it.unibo.falltohell.model.impl.gameobjects;

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
 * @author Casadei Lorenzo
 */
public class MovableImpl extends GameObjectImpl implements Movable{
    private Vector2 speed;
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
    public MovableImpl(Level level, Vector2 position, Vector2 speed, Collider collider) {
        super(level, position, collider);
        this.speed = speed;
    }
    /**
     * {@inheritDoc}
     */
    public void update(double deltaTime) {
        Vector2 displacement = speed.multiply(deltaTime);
        setPosition(getPosition().add(displacement));
    }
    /**
     * {@inheritDoc}
     */
    public double getSpeedX() {
        return this.speed.x();
    }
    /**
     * {@inheritDoc}
     */
    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }
    /**
     * {@inheritDoc}
     */
    public double getSpeedY() {
        return this.speed.y();
    }
    


}
