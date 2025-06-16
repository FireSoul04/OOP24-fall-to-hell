package it.unibo.falltohell.model.api;

/**
 * Customized timer to schedule game events.
 * @author Martina Malagoli
 */
public interface CustomTimer {

    /**
     * Method to start the timer.
     */
    void start();

    /**
     * Method to check if the timer is actually running.
     * @return if the timer is running
     */
    boolean isStarted();

    /**
     * Method to check if the timer is paused.
     * @return if the timer is paused
     */
    boolean isPaused();

    /**
     * Method to end the timer.
     */
    void stop();

    /**
     * Method to pause the timer.
     */
    void pause();

    /**
     * Method to resume the timer.
     */
    void resume();
}
