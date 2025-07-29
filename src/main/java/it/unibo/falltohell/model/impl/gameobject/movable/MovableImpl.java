package it.unibo.falltohell.model.impl.gameobject.movable;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of the {@link Movable} interface.
 * <p>
 * Represents a game object that can move within the level, with horizontal and
 * vertical speed.
 * Provides methods to update its position based on speed and elapsed time, and
 * to get or set its speed.
 * </p>
 *
 * @author Casadei Lorenzo
 */
public class MovableImpl extends GameObjectImpl implements Movable {

    private Vector2 speed;
    private boolean facingRight;

    /**
     * Constructs a movable game object.
     *
     * @param level    the level to which this object belongs
     * @param position the initial position of the object
     * @param speed    the initial speed
     * @param collider the collider for this object
     */
    public MovableImpl(final Level level, final Vector2 position, final Vector2 speed, final Collider collider) {
        super(level, position, collider);
        this.speed = speed;
    }

    /**
     * {@inheritDoc}
     */
    public void update(final double deltaTime) {
        final Vector2 displacement = speed.multiply(deltaTime);
        setPosition(getPosition().add(displacement));
    }

    /**
     * {@inheritDoc}
     */
    public Vector2 getSpeed() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     */
    public void setSpeed(final Vector2 speed) {
        this.speed = speed;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFacingRight() {
        return this.facingRight;
    }

    /**
     * @param facingRight tells if an object is facing right
     */
    protected final void setFacingRight(final boolean facingRight) {
        this.facingRight = facingRight;
    }

}
