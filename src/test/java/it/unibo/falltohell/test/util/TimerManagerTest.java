package it.unibo.falltohell.test.util;

import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.impl.manager.TimerManagerImpl;

/**
 * Class to handle multiple timer for tests.
 * @author Martina Malagoli
 */
public class TimerManagerTest extends TimerManagerImpl {

    private static final double VIRTUAL_MILLISECONDS = 1.0;

    /**
     * Method to block the execution of the code until the timer has run out
     * (or it has been stopped) or until it has passed a certain specified amount of time (timeout).
     * @param name of the timer to be waited
     * @param timeout to wait until the signaling
     * @throws IllegalStateException if the timer has not run out or has been stopped before timeout
     */
    public void waitForTimer(final String name, final long timeout) {
        this.checkExists(name);
        final CustomTimer timer = this.getTimer(name);
        long frames = 0;
        while (frames <= timer.getDuration()) {
            if (frames >= timeout) {
                throw new IllegalStateException("Timer " + name + " has not finished before " + timeout + " ms");
            }
            // Simulates the passage of a real amount of milliseconds everytime update is called
            timer.update(VIRTUAL_MILLISECONDS);
            frames++;
        }
    }

    /**
     * Method to block execution of code until a certain timeout.
     * @param timeout is the time to wait
     */
    public void waitUntilTimeout(final long timeout) {
        long frames = 0;
        while (frames < timeout) {
            this.updateAllTimers(VIRTUAL_MILLISECONDS);
            frames++;
        }
    }

}
