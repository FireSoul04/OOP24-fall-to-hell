package it.unibo.falltohell.model.impl.gameobject.block;

import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a base block which can collide with other objects.
 * @author Martina Malagoli
 */
public class BaseCollidableBlock extends GameObjectImpl {

    /**
     * Initialization of the BaseBlock class.
     * @param lv is the level of the block
     * @param position is the position of the block in the level
     * @param collider associated to the block
     * @param fileName is the name of the image file associated to the block
     */
    public BaseCollidableBlock(final Level lv, final Vector2 position,
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
    public BaseCollidableBlock(final Level lv, final Vector2 position,
                               final Collider collider, final String fileName, final Vector2 offset) {
        super(lv, position, collider);
        super.initDrawable(offset, Priority.HIGH, fileName);
    }

}
