package it.unibo.falltohell.model.impl.gameobjects.block;

import it.unibo.falltohell.model.api.Drawable.Priority;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a base block.
 * @author Martina Malagoli
 */
public class BaseBlock extends GameObjectImpl {

    /**
     * Initialization of the BaseBlock class.
     * @param lv is the level of the block
     * @param position is the position of the block in the level
     * @param collider associated to the block
     * @param fileName is the name of the image file associated to the block
     */
    public BaseBlock(final Level lv, final Vector2 position,
                     final Collider collider, final String fileName) {
        this(lv, position, collider, fileName, Vector2.zero());
    }

    /**
     * Initialization of the BaseBlock class with the additional offset information.
     * @param lv is the level of the block
     * @param position is the position of the block in the level
     * @param collider associated to the block
     * @param fileName is the name of the image file associated to the block
     * @param offset to apply to the sprite's position
     */
    public BaseBlock(final Level lv, final Vector2 position,
                     final Collider collider, final String fileName, final Vector2 offset) {
        super(lv, position, collider);
        initDrawable(offset, Priority.HIGH, fileName);
    }

}
