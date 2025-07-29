package it.unibo.falltohell.model.impl.gameobject.block;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a type of block that deals damage continuously
 * to the character and to enemies while they touch it from above.
 * @author Martina Malagoli
 */
public class LavaBlock extends BaseCollidableBlock {

    private static final double DAMAGE = 0.001;

    /**
     * Initialization of the LavaBlock class.
     * @param lv is the level of the block
     * @param position is the position of the block in the level
     * @param collider associated to the block
     * @param fileName is the name of the image file associated to the block
     * @param offset of the drawable associated to the block
     */
    public LavaBlock(final Level lv, final Vector2 position,
                     final Collider collider, final String fileName, final Vector2 offset) {
        super(lv, position, collider, fileName, offset);
    }

    /**
     * {@inheritDoc}
     * It is used to deal damage continuously to an entity as it
     * walks on this type of block.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (direction.equals(Vector2.up()) && other instanceof Entity entity) {
            entity.getStats().subLife(DAMAGE);
        }
    }
}
