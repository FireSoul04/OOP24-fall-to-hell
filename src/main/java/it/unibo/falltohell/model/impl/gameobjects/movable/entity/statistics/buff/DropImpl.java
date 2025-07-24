package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff;

import java.util.UUID;

import it.unibo.falltohell.model.api.Drawable.Priority;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Drop;

import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.block.BaseBlock;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of a {@link Drop} object that gives a {@link Buff} to a
 * {@link Character}
 * upon collision and disappears either after a fixed time or after being
 * collected.
 *
 * <p>
 * This drop moves horizontally until it hits a {@link Block} from above,
 * in which case it stops moving horizontally.
 * </p>
 *
 * @see Drop
 * @see Buff
 * @see Character
 * @see Block
 * @see CustomTimerImpl
 *
 * @author Sara Visani
 */
public class DropImpl extends MovableImpl implements Drop {

    private static final int EXPIRE_TIME = 5000;
    private static final Vector2 VELOCITY = new Vector2(0, -10);
    private static final Dimensions DIMENSIONS = new Dimensions(10, 10);
    private final String name;
    private final Buff buff;

    /**
     * Constructs a new drop object that carries a {@link Buff}, is placed at the
     * given position,
     * and is scheduled to be removed after 5 seconds if not collected.
     *
     * @param lv       the {@link Level} in which the drop exists
     * @param position the starting {@link Vector2} position of the drop
     * @param buff     the {@link Buff} to be applied when collected by a
     *                 {@link Character}
     *@param fileName is the name of the image file associated to the drop
     */
    public DropImpl(final Level lv, final Vector2 position, final Buff buff, final String fileName) {
        super(lv, position, VELOCITY,
                new BoxCollider(Vector2.zero(), DIMENSIONS));
        this.buff = buff;

        this.name = "drop-timer-" + UUID.randomUUID();
        super.getLevel().getTimerManager().addTimer(this.name,
                new CustomTimerImpl(EXPIRE_TIME, () -> super.getLevel().removeGameObject(this)));
        this.initDrawable(Priority.VERY_LOW, fileName);
    }

    /**
     * {@inheritDoc}
     *
     * <ul>
     * <li>If the object is a {@link Character}, the {@link Buff} is applied,
     * the drop is removed from the level, and its associated timer is
     * cancelled.</li>
     * <li>If the object is a {@link Block} and the collision is from below (i.e.
     * the drop lands on it),
     * the vertical movement is stopped.</li>
     * </ul>
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof Character) {
            final var character = (Character) other;
            character.getBuffManager().addBuff(this.buff);
            super.getLevel().getTimerManager().removeTimer(this.name);
            super.getLevel().removeGameObject(this);
        } else if (other instanceof BaseBlock && direction.y() < 0) {
            super.setSpeed(new Vector2(this.getSpeedX(), 0));
        }
    }

}
