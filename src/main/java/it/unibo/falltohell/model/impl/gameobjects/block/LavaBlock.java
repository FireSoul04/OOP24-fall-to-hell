package it.unibo.falltohell.model.impl.gameobjects;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.Objects;

/**
 * Class that represents a type of block that deals damage continuously
 * to the character and to enemies while they touch it from above.
 * @author Martina Malagoli
 */
public class LavaBlock extends BaseBlock{

    private static final long TIME = 1500;
    private static final double DAMAGE = 2;

    /**
     * Initialization of the LavaBlock class.
     * @param lv is the level of the block
     * @param position is the position of the block in the level
     * @param width
     * @param height
     * @param collider associated to the block
     */
    public LavaBlock(Level lv, Vector2 position, double width, double height, Collider collider) {
        super(lv, position, width, height, collider);
    }

    /**
     *{@inheritDoc}
     * It is used to deal damage continuously to an entity as it
     * walks on this type of block.
     */
    @Override
    public void onCollision(GameObject other, Vector2 direction) {
        if (other instanceof Entity entity) {
            final String ID = String.valueOf(Objects.hash(this, entity));
            final String name = "LavaBlock" + ID;
            final TimerManager timerManager = this.getLevel().getTimerManager();
            timerManager.addTimer(name + ID, new CustomTimerImpl(TIME, () -> {
                entity.getStats().subLife(DAMAGE);
                timerManager.restartTimer(name);
            }));
        }
    }

    /**
     * {@inheritDoc}
     * It is used to stop the dealing of damage to an entity when it
     * walks away from this type of block.
     */
    @Override
    public void onCollisionExit(GameObject other, Vector2 direction) {
        if (other instanceof Entity entity) {
            final String ID = String.valueOf(Objects.hash(this, entity));
            final String name = "LavaBlock" + ID;
            final TimerManager timerManager = this.getLevel().getTimerManager();
            timerManager.removeTimer(name);
        }
    }
}
