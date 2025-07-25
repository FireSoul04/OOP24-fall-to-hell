package it.unibo.falltohell.model.impl.gameobjects.block;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Entity;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a block that reduces the speed of an entity
 * when it walks over it.
 * @author Martina Malagoli.
 */
public class VinesBlock extends BaseCollidableBlock {

    private static final double MULTIPLIER = 0.3;

    /**
     * Initialization of the VinesBlock class.
     * @param lv is the level of the block
     * @param position is the position of the block in the level
     * @param collider associated to the block
     * @param fileName is the name of the image file associated to the block
     */
    public VinesBlock(final Level lv, final Vector2 position,
                      final Collider collider, final String fileName, Vector2 offset) {
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
            final Statistics statistics = entity.getStats();
            statistics.setSpeed(statistics.getInitialSpeed().multiply(MULTIPLIER));
        }
    }

    /**
     * {@inheritDoc}
     * It is used to reset the normal speed of an entity when it
     * walks away from this block.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        if (direction.equals(Vector2.up()) && other instanceof Entity entity) {
            final Statistics statistics = entity.getStats();
            statistics.setSpeed(statistics.getInitialSpeed());
        }
    }
}
