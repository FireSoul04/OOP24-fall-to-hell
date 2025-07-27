package it.unibo.falltohell.model.impl.gameobject.block;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a basic block that cannot collide with anything else.
 * @author Martina Malagoli
 */
public class BaseNonCollidableBlock extends GameObjectImpl {

    /**
     * Initialization of the BaseNonCollidableBlock class.
     * @param lv is the level of the block
     * @param position of the block in the level
     */
    public BaseNonCollidableBlock(final Level lv, final Vector2 position) {
        super(lv, position);
        initDrawable(Priority.HIGH, "base_block.png");
    }
}
