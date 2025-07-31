package it.unibo.falltohell.model.api.timer;

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

    /**
     * Updates the timer countdown if it is started and not paused.
     * @param deltaTime is the amount of time passed in a frame
     */
    void update(double deltaTime);

    /**
     * @return the timer's duration
     */
    long getDuration();

    /**
     * @return the time already elapsed
     */
    long getElapsedTime();
}
