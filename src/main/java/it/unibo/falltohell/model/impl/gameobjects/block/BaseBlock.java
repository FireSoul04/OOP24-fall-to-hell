package it.unibo.falltohell.model.impl.gameobjects.block;

import java.util.Optional;

import it.unibo.falltohell.model.api.Drawable;
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
     * @param width
     * @param height
     * @param collider associated to the block
     */
    public BaseBlock(final Level lv, final Vector2 position,
                final Collider collider, final Optional <Drawable> drawable) {
        super(lv, position, collider, drawable);
    }

}
