package it.unibo.falltohell.model.impl.gameobjects.entrance;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a base entrance.
 * @author Martina Malagoli
 */
public class BaseEntrance extends GameObjectImpl {

    /**
     * Initialization of the BaseEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     * @param width
     * @param height
     * @param collider associated with this entrance
     */
    public BaseEntrance(final Level lv, final Vector2 position,
                        final double width, final double height, final Collider collider) {
        super(lv, position, width, height, false, collider);
    }
}
