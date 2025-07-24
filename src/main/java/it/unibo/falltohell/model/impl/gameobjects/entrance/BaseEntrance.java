package it.unibo.falltohell.model.impl.gameobjects.entrance;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.AggroListener;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a base entrance.
 * @author Martina Malagoli
 */
public class BaseEntrance extends GameObjectImpl {

    private final AggroListener listener;

    /**
     * Initialization of the BaseEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     * @param collider associated with this entrance
     */
    public BaseEntrance(final Level lv, final Vector2 position, final Collider collider) {
        super(lv, position, false, collider);
        this.listener = new EnemyFactoryImpl().askManager(lv).addEntrance(this);
    }

    AggroListener getListener() {
        return this.listener;
    }
}
