package it.unibo.falltohell.model.impl.gameobject.entrance;

import it.unibo.falltohell.model.api.gameobject.entrance.Entrance;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.listener.EnterSafeZoneListener;
import it.unibo.falltohell.model.api.listener.ExitSafeZoneListener;
import it.unibo.falltohell.model.impl.factory.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Class that represents a base entrance.
 * @author Martina Malagoli
 */
public abstract class BaseEntrance extends GameObjectImpl implements Entrance {

    private static final Dimensions DIMENSIONS = new Dimensions(40, 40);
    private static final double OFFSET = 10;
    private Optional<EnterSafeZoneListener> listenerEnter;
    private Optional<ExitSafeZoneListener> listenerExit;

    /**
     * Initialization of the BaseEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     */
    @SuppressFBWarnings(value = "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR",
    justification = "It doesn't change entrance. It's needed as a reference'")
    public BaseEntrance(final Level lv, final Vector2 position) {
        super(lv, position, new BoxCollider(Vector2.up().multiply(OFFSET), DIMENSIONS));
        this.listenerEnter = Optional.empty();
        this.listenerExit = Optional.empty();
        new EnemyFactoryImpl().askManager(lv).addEntrance(this);
    }

    /**
     * @return the optional entrance listener
     */
    protected Optional<EnterSafeZoneListener> getListenerEnter() {
        return this.listenerEnter;
    }

    /**
     * @return the optional exit listener
     */
    protected Optional<ExitSafeZoneListener> getListenerExit() {
        return this.listenerExit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListenerEnter(final EnterSafeZoneListener listener) {
        this.listenerEnter = Optional.of(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListenerExit(final ExitSafeZoneListener listener) {
        this.listenerExit = Optional.of(listener);
    }


}
