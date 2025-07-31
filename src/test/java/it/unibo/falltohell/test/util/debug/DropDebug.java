package it.unibo.falltohell.test.util.debug;

import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.drop.Drop;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * A debug implementation of a {@link Drop} that represents a collectible buff
 * in the game world.
 * <p>
 * The drop moves downwards at a constant velocity and expires after a fixed
 * duration if not collected.
 * Upon collision with a {@link Character}, it applies its associated
 * {@link Buff}
 * for a predefined duration and then removes itself from the level.
 * <p>
 * Collisions with blocks from below stop its vertical movement.
 *
 * @author Sara Visani
 * @see Buff
 * @see Drop
 * @see Character
 */
public class DropDebug extends MovableImpl implements Drop {

    private static final int EXPIRE_TIME = 10_000;
    private static final long BUFF_DURATION = 15_000;
    private static final Vector2 VELOCITY = Vector2.down().multiply(1);
    private static final Dimensions DIMENSIONS = new Dimensions(10, 10);
    private final String name;
    private final Buff buff;
    private boolean collected;
    private final BuffNames type;

    /**
     * Constructs a new drop object that carries a {@link Buff}, is placed at the
     * given position,
     * and is scheduled to be removed after 5 seconds if not collected.
     *
     * @param lv       the {@link Level} in which the drop exists
     * @param position the starting {@link Vector2} position of the drop
     * @param buff     the {@link Buff} to be applied when collected by a
     *                 {@link Character}
     * @param fileName is the name of the image file associated to the drop
     * @param type of the wrapped buff
     */
    @SuppressFBWarnings(value = {
            "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR",
            "EI_EXPOSE_REP2"
    }, justification = "removeGameObject is final and Buff is immutable")
    public DropDebug(final Level lv, final Vector2 position, final Buff buff, final String fileName,
            final BuffNames type) {
        super(lv, position, VELOCITY,
                new BoxCollider(Vector2.zero(), DIMENSIONS));
        this.buff = buff;
        this.type = type;

        this.name = "drop-timer-" + UUID.randomUUID();
        super.getLevel().getTimerManager().addTimer(this.name,
                new CustomTimerImpl(EXPIRE_TIME, () -> super.getLevel().removeGameObject(this)));
        this.initDrawable(Priority.VERY_LOW, fileName);
        this.collected = false;
    }

    /**
     * {@inheritDoc}
     *
     * <ul>
     * <li>If the object is a {@link Character}, the {@link Buff} is applied,
     * the drop is removed from the level, and its associated timer is
     * cancelled.</li>
     * <li>If the object is a {@link BaseCollidableBlock} and the collision is from
     * below (i.e.
     * the drop lands on it),
     * the vertical movement is stopped.</li>
     * </ul>
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (!this.collected) {
            if (other instanceof Character character) {
                this.collected = true;
                final String name = "drop_buff" + this.hashCode();
                character.getBuffManager().addBuff(this.buff, BUFF_DURATION, name);
                if (super.getLevel().getTimerManager().searchTimer(this.name)) {
                    super.getLevel().getTimerManager().removeTimer(this.name);
                }
                super.getLevel().removeGameObject(this);
            } else if (other instanceof BaseCollidableBlock && direction.equals(Vector2.down())) {
                super.setSpeed(Vector2.zero());
            }
        }
    }

    /**
     * Returns the expiration time of this collectible in milliseconds.
     *
     * @return the expiration time in milliseconds
     */
    public static int getExpireTime() {
        return EXPIRE_TIME;
    }

    /**
     * Returns the duration of the buff granted by this collectible in milliseconds.
     *
     * @return the buff duration in milliseconds
     */
    public static long getBuffDuration() {
        return BUFF_DURATION;
    }

    /**
     * Returns the default velocity of this collectible.
     *
     * @return a {@link Vector2} representing the velocity
     */
    public static Vector2 getVelocity() {
        return VELOCITY;
    }

    /**
     * Returns the default dimensions of this collectible.
     *
     * @return a {@link Dimensions} object representing its width and height
     */
    public static Dimensions getDimensions() {
        return DIMENSIONS;
    }

    /**
     * Returns the unique name of this collectible instance.
     *
     * @return the name of the collectible
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the buff associated with this collectible.
     *
     * @return a {@link Buff} instance representing the effect of this collectible
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This is used as Debug")
    public Buff getBuff() {
        return buff;
    }

    /**
     * Checks whether this collectible has already been collected.
     *
     * @return {@code true} if the collectible is collected; {@code false} otherwise
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Returns the type of buff associated with this drop.
     *
     * <p>
     * The type corresponds to one of the {@link BuffNames} values used when
     * this drop was created (e.g., {@link BuffNames#ATTACK},
     * {@link BuffNames#SPEED}, etc.).
     *
     * @return the {@link BuffNames} type of this drop
     */
    public BuffNames getType() {
        return type;
    }
}
