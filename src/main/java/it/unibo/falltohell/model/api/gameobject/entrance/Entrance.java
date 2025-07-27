package it.unibo.falltohell.model.api.gameobject.entrance;

import it.unibo.falltohell.model.api.listener.EnterSafeZoneListener;
import it.unibo.falltohell.model.api.listener.ExitSafeZoneListener;

/**
 * Interface that represents an entrance.
 * @author Martina Malagoli
 */
public interface Entrance {

    /**
     * @param listener to give to the entrance that tells if the character has entered
     */
    void setListenerEnter(EnterSafeZoneListener listener);

    /**
     * @param listener to give to the entrance that tells if the character has exited
     */
    void setListenerExit(ExitSafeZoneListener listener);
}
