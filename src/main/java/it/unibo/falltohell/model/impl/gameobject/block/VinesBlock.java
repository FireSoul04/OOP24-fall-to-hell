package it.unibo.falltohell.model.impl.gameobject.block;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.model.api.manager.BuffManager;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.buff.SpeedBuff;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a block that reduces the speed of an entity
 * when it walks over it.
 * @author Martina Malagoli.
 */
public class VinesBlock extends BaseCollidableBlock {

    private static final double MULTIPLIER = -0.5;

    /**
     * Initialization of the VinesBlock class.
     * @param lv is the level of the block
     * @param position is the position of the block in the level
     * @param collider associated to the block
     * @param fileName is the name of the image file associated to the block
     * @param offset of the drawable associated to the block
     */
    public VinesBlock(final Level lv, final Vector2 position,
                      final Collider collider, final String fileName, final Vector2 offset) {
        super(lv, position, collider, fileName, offset);
    }

    /**
     * {@inheritDoc}
     * It is used to reduce the speed of an entity when it collides
     * with this type of block.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (direction.equals(Vector2.up()) && other instanceof Entity entity) {
            final BuffManager buffManager = entity.getBuffManager();
            final String name = "vines_buff" + entity.hashCode();
            if (!buffManager.searchBuff(name)) {
                buffManager.addInfiniteBuff(new SpeedBuff(entity.getStats(), MULTIPLIER), name);
            }
        }
    }

    /**
     * {@inheritDoc}
     * It is used to reset the normal speed of an entity when it
     * walks away from this block.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        if (other instanceof Entity entity) {
            final BuffManager buffManager = entity.getBuffManager();
            final String name = "vines_buff" + entity.hashCode();
            if (buffManager.searchBuff(name)) {
                buffManager.removeInfiniteBuff(name);

            }
        }
    }
}
