package it.unibo.falltohell.model.api.timer;

/**
 * Event that has to happen when a timer ends.
 * @author Martina Malagoli
 */
@FunctionalInterface
public interface CustomTimerEvent {

    /**
     * Method to make an event happen.
     */
    void execute();
}
