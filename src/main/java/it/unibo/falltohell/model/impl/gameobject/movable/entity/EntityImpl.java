package it.unibo.falltohell.model.impl.gameobject.movable.entity;

import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.model.api.manager.BuffManager;
import it.unibo.falltohell.model.api.statistic.Statistics;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;
import it.unibo.falltohell.model.impl.manager.BuffManagerImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Dimensions;
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

    private static final Vector2 GRAVITY_STEP = new Vector2(0.0, 0.06);
    private static final long INVICIBILITY_TIME = 1000;
    private static final double EPSILON = 1e-3;

    private final Statistics stats;
    private Vector2 gravity;
    private Vector2 velocity;
    private boolean onGround;
    private final BuffManager buffManager;
    private final String name = "Invicibility -" + UUID.randomUUID();
    private boolean invincible;

    /**
     * Constructs an {@code EntityImpl} with the given parameters.
     * <p>
     *
     * @param level    the {@link Level} where the entity exists
     * @param position the {@link Vector2} position of the entity
     * @param stats    the {@link Statistics} defining attributes like life and
     *                 speed
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "Entity must have access to its statistics"
    )
    public EntityImpl(final Level level, final Vector2 position, final Statistics stats) {
        super(level, position, stats.getSpeed(), new BoxCollider(Vector2.zero(), stats.getDimensions()));
        this.stats = stats;
        this.gravity = Vector2.zero();
        this.velocity = Vector2.zero();
        this.onGround = false;
        this.buffManager = new BuffManagerImpl(level.getTimerManager());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Any external object can read and modify the entity's stats"
    )
    @Override
    public final Statistics getStats() {
        return this.stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDamagedLife(final double damage) {
        if (this.invincible) {
            return;
        }
        this.invincible = true;
        this.subLife(damage);

        final var tm = super.getLevel().getTimerManager();
        if (tm.searchTimer(this.name)) {
            tm.restartTimer(this.name);
        } else {
            tm.addTimer(this.name, new CustomTimerImpl(INVICIBILITY_TIME, () -> this.invincible = false));
        }
    }

    /**
     * Subtracts life to the entity.
     *
     * @param damage to take
     */
    protected void subLife(final double damage) {
        this.stats.subLife(damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDead() {
        return this.stats.getLife() <= 0;
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
     * Add a force to the entity.
     *
     * @param force to apply
     */
    protected void addForce(final Vector2 force) {
        this.velocity = this.velocity.add(force);
    }

    /**
     * @return if the entity is on ground
     */
    protected boolean isOnGround() {
        return this.onGround;
    }

    /**
     * Reset the gravity applied to this entity.
     */
    protected void resetGravity() {
        this.gravity = Vector2.zero();
    }

    /**
     * {@inheritDoc}
     * Apply the gravity and all forces of the entity in this frame.
     */
    @Override
    public void update(final double deltaTime) {
        this.applyGravity(deltaTime);
        this.setPosition(this.getPosition().add(this.velocity));
        this.velocity = Vector2.zero();
        this.getDrawable().ifPresent(drawable -> drawable.mirror(!this.isFacingRight()));
    }

    /**
     * Apply gravity to the entity every frame.
     *
     * @param deltaTime difference between two frames
     */
    private void applyGravity(final double deltaTime) {
        if (!this.onGround) {
            this.gravity = this.gravity.add(GRAVITY_STEP.multiply(deltaTime));
            this.velocity = this.velocity.add(this.gravity);
        }
    }

    /**
     * {@inheritDoc}
     * Notify if the entity is on ground.
     * If the entity is inside a BaseBlock because of gravity, this method will move
     * it up to the floor level.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof BaseCollidableBlock) {
            if (direction.equals(Vector2.down())) {
                this.onGround = true;
                this.gravity = Vector2.zero();
                this.pushUpToFloorLevel(other);
            } else if (direction.equals(Vector2.up())) {
                this.pushDownFromBlock(other);
            } else {
                this.pushFarFromBlock(other);
            }
        }
    }

    /**
     * {@inheritDoc}
     * Notify if the entity is leaving the ground.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        if (other instanceof BaseCollidableBlock) {
            this.onGround = false;
        }
    }

    /**
     * Push up the entity until it reaches the floor's height.
     *
     * @param other block colliding with
     */
    private void pushUpToFloorLevel(final GameObject other) {
        final double distance = this.getPosition().subtract(other.getPosition()).y();
        final double thisHeight = this.getCollider().orElseThrow().size().height();
        final double idealDistance = this.getIdealDistances(other).y();
        // Range of values for the y that the entity needs to be to reach floor level
        final double eps = 1 + (distance / thisHeight);
        final double moveTo = Math.abs(distance) - idealDistance;
        if (eps < EPSILON) {
            this.setPosition(this.getPosition().subtract(new Vector2(0, moveTo)));
        } else if (Math.abs(moveTo) > eps) {
            this.setPosition(this.getPosition().subtract(new Vector2(0, eps)));
        }
    }

    /**
     * Push the entity down when it reached a ceiling.
     *
     * @param other block colliding with
     */
    private void pushDownFromBlock(final GameObject other) {
        final double distance = this.getPosition().subtract(other.getPosition()).y();
        final double idealDistance = this.getIdealDistances(other).y();
        final double moveTo = Math.abs(distance) - idealDistance;
        if (moveTo > EPSILON) {
            this.setPosition(this.getPosition().add(new Vector2(0, moveTo)));
        }
    }

    /**
     * Push the entity left or right based on the direction facing to prevent going
     * through blocks.
     *
     * @param other block colliding with
     */
    private void pushFarFromBlock(final GameObject other) {
        final double distance = this.getPosition().subtract(other.getPosition()).x();
        final double idealDistance = this.getIdealDistances(other).x();
        final double moveTo = Math.abs(Math.abs(distance) - idealDistance);
        if (moveTo > EPSILON) {
            this.setPosition(this.getPosition().add(new Vector2(moveTo * Math.signum(distance), 0)));
        }
    }

    /**
     * Compute the ideal distance based on the other block's size.
     *
     * @param other block to compute distance
     * @return the ideal distance to reach
     */
    private Vector2 getIdealDistances(final GameObject other) {
        final Dimensions thisSize = this.getCollider().orElseThrow().size();
        final Dimensions otherSize = other.getCollider().orElseThrow().size();
        return new Vector2(thisSize.width(), thisSize.height())
                .add(new Vector2(otherSize.width(), otherSize.height()))
                .divide(2);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "This must be accessible to add and remove buffs when using passive abilities, drops and potions"
    )
    @Override
    public BuffManager getBuffManager() {
        return this.buffManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInvincible() {
        return this.invincible;
    }
}
