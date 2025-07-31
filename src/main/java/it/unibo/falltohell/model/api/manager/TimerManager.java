package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.timer.CustomTimer;

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
     * Method to remove all timers.
     */
    void removeAllTimers();

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

    /**
     * Method to restart a timer.
     * @param name of the timer to be restarted
     */
    void restartTimer(String name);

    /**
     * Method to stop a timer.
     * @param name of the timer to be stopped
     */
    void stopTimer(String name);

    /**
     * Method that tells if there is already a timer with the given name.
     * @param name of the timer to be searched
     * @return true if there is a timer with that name, false otherwise
     */
    boolean searchTimer(String name);

    /**
     * Method to add a timer if it is not already existent or to restart it
     * if present.
     * @param name of the timer to be added or restarted
     * @param timer to be added or restarted
     */
    void restartIfPresent(String name, CustomTimer timer);

    /**
     * Method to update all timers.
     * @param deltaTime is the amount of time passed in a frame
     */
    void updateAllTimers(double deltaTime);

}
