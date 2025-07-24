package it.unibo.falltohell.model.api.gameobjects;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.gameobjects.block.BaseBlock;
import it.unibo.falltohell.util.Vector2;

public interface BlockFactory {

    /**
     * Method to create a basic block.
     * @param level is the level of the block
     * @param position is the position of the block in the level
     * @return the basic block created
     */
    BaseBlock createBaseBlock(Level level, Vector2 position);

    /**
     * Method to create a lava block that can deal damage to entities.
     * @param level is the level of the block
     * @param position is the position of the block in the level
     * @return the lava block created
     */
    BaseBlock createLavaBlock(Level level, Vector2 position);

    /**
     * Method to create a vines block that can slow down entities.
     * @param level is the level of the block
     * @param position is the position of the block in the level
     * @return the vines block created
     */
    BaseBlock createVinesBlock(Level level, Vector2 position);
}
