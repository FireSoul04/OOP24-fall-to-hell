package it.unibo.falltohell.model.impl.gameobjects;

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
    public BaseBlock(Level lv, Vector2 position, double width, double height, Collider collider) {
        super(lv, position, width, height, collider);
    }

}
