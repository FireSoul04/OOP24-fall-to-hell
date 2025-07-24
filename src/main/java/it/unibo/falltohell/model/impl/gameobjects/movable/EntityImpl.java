package it.unibo.falltohell.model.impl.gameobjects.movable;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.impl.Sprite;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
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
     * @param stats    the {@link Statistics} defining attributes like life and
     *                 speed
     */
    public EntityImpl(final Level level, final Vector2 position, final Statistics stats) {
        super(level, position, stats.getSpeed().x(), stats.getSpeed().y(),
                new BoxCollider(Vector2.zero(), stats.getDimensions()));
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

    /**
     * Initializes the graphical representation of this entity by associating it
     * with a {@link Sprite}.
     * <p>
     * This method should be called by subclasses <b>after</b> their construction is
     * complete,
     * to ensure that {@code this} refers to the fully initialized subclass
     * instance.
     * It sets the drawable of the entity using {@link #setDrawable} and wraps the
     * sprite in an {@link Optional}.
     *
     * @implNote This method avoids invoking {@code setDrawable(new Sprite(this))}
     *           inside the constructor
     *           to prevent premature access to uninitialized subclass state during
     *           object construction.
     *
     * @see Sprite
     *
     */
    protected void initDrawable() {
        super.setDrawable(Optional.of(new Sprite(this)));
    }
}
