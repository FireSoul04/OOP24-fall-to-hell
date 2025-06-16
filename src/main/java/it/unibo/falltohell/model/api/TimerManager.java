package it.unibo.falltohell.model.api;

/**
 * Interface to handle multiple timers.
 * @author Martina Malagoli
 */
public interface TimerManager {

    /**
     * Method to add and start a new timer to the TimerManager.
     * @param name of the new timer
     * @param timer to be added
     */
    void addTimer(String name, CustomTimer timer);

    /**
     * Method to remove and stop a timer from the TimerManager.
     * @param name of the timer to be removed
     */
    void removeTimer(String name);

    /**
     * Method to pause a specific timer.
     * @param name of the timer to be paused
     */
    void pauseTimer(String name);

    /**
     * Method to pause all timers if not already paused.
     */
    void pauseAllTimers();

    /**
     * Method to resume a specific timer.
     * @param name of the timer to be resumed
     */
    void resumeTimer(String name);

    /**
     * Method to resume all timers if not already resumed.
     */
    void resumeAllTimers();

}
