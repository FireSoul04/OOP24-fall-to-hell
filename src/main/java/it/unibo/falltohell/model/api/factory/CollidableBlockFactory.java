package it.unibo.falltohell.model.api.factory;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface that handles the creation of different types of block.
 * @author Martina Malagoli
 */
public interface CollidableBlockFactory {

    /**
     * Method to create a basic block.
     * @param level is the level of the block
     * @param position is the position of the block in the level
     * @return the basic block created
     */
    BaseCollidableBlock createCollidableBaseBlock(Level level, Vector2 position);

    /**
     * Method to create a lava block that can deal damage to entities.
     * @param level is the level of the block
     * @param position is the position of the block in the level
     * @return the lava block created
     */
    BaseCollidableBlock createLavaBlock(Level level, Vector2 position);

    /**
     * Method to create a vines block that can slow down entities.
     * @param level is the level of the block
     * @param position is the position of the block in the level
     * @return the vines block created
     */
    BaseCollidableBlock createVinesBlock(Level level, Vector2 position);
}
