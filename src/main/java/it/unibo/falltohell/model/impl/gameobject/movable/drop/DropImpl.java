package it.unibo.falltohell.model.impl.gameobject.movable.drop;

import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.drop.Drop;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of a {@link Drop} object that gives a {@link Buff} to a
 * {@link Character}
 * upon collision and disappears either after a fixed time or after being
 * collected.
 *
 * <p>
 * This drop moves horizontally until it hits a {@link BaseCollidableBlock} from
 * above,
 * in which case it stops moving horizontally.
 * </p>
 *
 * @see Drop
 * @see Buff
 * @see Character
 * @see BaseCollidableBlock
 * @see CustomTimerImpl
 *
 * @author Sara Visani
 */
public class DropImpl extends MovableImpl implements Drop {

    private static final int EXPIRE_TIME = 10_000;
    private static final long BUFF_DURATION = 15_000;
    private static final Vector2 VELOCITY = Vector2.down().multiply(1);
    private static final Dimensions DIMENSIONS = new Dimensions(10, 10);
    private final String name;
    private final Buff buff;
    private boolean collected;

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
     */
    @SuppressFBWarnings(value = {
    "EI_EXPOSE_REP2",
    "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR" },
    justification = "Buff is immutable, and removeGameObject is final and safe to call in constructor")
    public DropImpl(final Level lv, final Vector2 position, final Buff buff, final String fileName) {
        super(lv, position, VELOCITY,
                new BoxCollider(Vector2.zero(), DIMENSIONS));
        this.buff = buff;

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

}
