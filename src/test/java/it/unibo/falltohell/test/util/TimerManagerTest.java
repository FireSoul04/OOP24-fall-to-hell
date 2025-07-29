package it.unibo.falltohell.test.util;

import it.unibo.falltohell.model.impl.manager.TimerManagerImpl;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Class to handle multiple timer for tests.
 * @author Martina Malagoli
 */
public class TimerManagerTest extends TimerManagerImpl {

    /**
     * Method to block the execution of the current thread until the timer has run out
     * (or it has been stopped) or until it has passed a certain specified amount of time (timeout).
     * @param name of the timer to be waited
     * @param timeout to wait until the signaling
     * @throws IllegalStateException if the timer has not run out or has been stopped before timeout
     */
    public void waitForTimer(final String name, final long timeout) {
        this.checkExists(name);
        final Logger logger = Logger.getLogger("TimerLogger");
        try {
            final boolean finished = this.getTimer(name).getLatch().await(timeout, TimeUnit.MILLISECONDS);
            if (!finished) {
                throw new IllegalStateException("Timer " + name + " has not finished before " + timeout + " ms");
            }
        } catch (final InterruptedException e) {
            logger.severe("Timer " + name + " interrupted: " + e);
        }
    }

}
