package it.unibo.falltohell.model.impl.gameobject.entrance;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.listener.AggroListener;
import it.unibo.falltohell.model.impl.factory.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a base entrance.
 * @author Martina Malagoli
 */
public class BaseEntrance extends GameObjectImpl {

    private static final Dimensions DIMENSIONS = new Dimensions(40, 40);
    private static final double OFFSET = 10;
    private final AggroListener listener;

    /**
     * Initialization of the BaseEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     */
    public BaseEntrance(final Level lv, final Vector2 position) {
        super(lv, position, new BoxCollider(Vector2.up().multiply(OFFSET), DIMENSIONS));
        this.listener = new EnemyFactoryImpl().askManager(lv).addEntrance(this);
    }

    protected AggroListener getListener() {
        return this.listener;
    }
}
