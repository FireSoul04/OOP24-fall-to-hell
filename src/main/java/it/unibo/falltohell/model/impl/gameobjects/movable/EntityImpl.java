package it.unibo.falltohell.model.impl.gameobjects.movable;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Abstract base class implementing {@link Entity}, representing all movable
 * enemies in the game.
 * It extends {@link MovableImpl} and stores a reference to {@link Statistics}
 * to manage
 * entity attributes like health, speed, and dimensions.
 *
 * @author Sara Visani
 */

public class EntityImpl extends MovableImpl implements Entity {

    private Statistics stats;

    /**
     * Constructs an {@code EntityImpl} with the given parameters.
     * <p>
     *
     * @param level    the {@link Level} where the entity exists
     * @param position the {@link Vector2} position of the entity
     * @param collider the {@link Collider} used for physics and collision
     * @param stats    the {@link Statistics} defining attributes like life and
     *                 speed
     */
    public EntityImpl(final Level level, final Vector2 position, final Collider collider, final Statistics stats) {
        super(level, position, stats.getDimensions().width(), stats.getDimensions().height(), stats.getSpeed().x(),
                stats.getSpeed().y(), collider);
        this.stats = stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Statistics getStats() {
        return this.stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDamagedLife(final double damage) {
        this.stats.subLife(damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDead() {
        if (this.stats.getLife() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Removes this entity from the level if it is considered dead.
     */
    protected void removeEntity() {
        if (this.isDead()) {
            super.getLevel().removeGameObject(this);
        }
    }

}
