package it.unibo.falltohell;

import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

/**
 * Class to test if the TimeCustomImpl class works as expected.
 * @author Martina Malagoli
 */
class TestCustomTimer {

    private static final String INFO_MESSAGE = "The IllegalStateException has been thrown correctly";
    private static final long TIMEOUT = 500;
    private static final long DURATION = 100;
    private CustomTimer timer;
    private boolean test;
    private Logger logger;

    /**
     * Initialization of the variables used in the tests.
     */
    @BeforeEach
    void initialization() {
        this.timer = new CustomTimerImpl(DURATION, () -> this.test = true);
        this.test = false;
        this.logger = Logger.getLogger("TestLogger");
    }

    /**
     * Tests if a timer is started correctly.
     */
    @Test
    void testTimerStart() {
        this.timer.start();
        Assertions.assertTrue(this.timer.isStarted(), "The timer has not been started as it should have");
    }

    /**
     * Tests if it is thrown an exception if the timer is not started correctly.
     */
    @Test
    void testExceptionTimerStart() {
        this.timer.start();
        try {
            this.timer.start();
            Assertions.fail("The timer should not be started when it is already running");
        } catch (final IllegalStateException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if a timer is stopped correctly.
     */
    @Test
    void testTimerStop() {
        this.timer.start();
        this.timer.stop();
        Assertions.assertFalse(this.timer.isStarted(), "The timer has not been stopped as it should have");
    }

    /**
     * Tests if it is thrown an exception if the timer is not stopped correctly.
     */
    @Test
    void testExceptionTimerStop() {
        try {
            this.timer.stop();
            Assertions.fail("The timer should not be stopped when it is already not running");
        } catch (final IllegalStateException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if the timer is paused correctly.
     */
    @Test
    void testTimerPause() {
        this.timer.start();
        this.timer.pause();
        Assertions.assertTrue(this.timer.isPaused(), "The timer has not been paused as it should have");
    }

    /**
     * Tests if it is thrown an exception if someone tries
     * to pause a timer that wasn't stated yet.
     */
    @Test
    void testExceptionTimerPauseIfNotStarted() {
        try {
            this.timer.pause();
            Assertions.fail("The timer should not be paused when it is not running yet");
        } catch (final IllegalStateException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if it is thrown an exception if someone tries
     * to pause a timer that is already paused.
     */
    @Test
    void testExceptionTimerPauseIfAlreadyPaused() {
        this.timer.start();
        this.timer.pause();
        try {
            this.timer.pause();
            Assertions.fail("The timer should not be paused when it is already paused");
        } catch (final IllegalStateException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if a timer is resumed correctly.
     */
    @Test
    void testTimerResume() {
        this.timer.start();
        this.timer.pause();
        this.timer.resume();
        Assertions.assertFalse(this.timer.isPaused(), "The timer has not been resumed as it should have");
    }

    /**
     * Tests if it is thrown an exception if someone tries
     * to resume a timer that wasn't stated yet.
     */
    @Test
    void testExceptionTimerResumeIfNotStarted() {
        try {
            this.timer.resume();
            Assertions.fail("The timer should not be resumed when it is not running yet");
        } catch (final IllegalStateException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if it is thrown an exception if someone tries
     * to resume a timer that was already resumed.
     */
    @Test
    void testExceptionTimerResumeIfAlreadyResumed() {
        this.timer.start();
        this.timer.pause();
        this.timer.resume();
        try {
            this.timer.resume();
            Assertions.fail("The timer should not be resumed when it is already running");
        } catch (final IllegalStateException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if an event is executed correctly at the end of a timer.
     */
    @Test
    void testCorrectExecutionOfEvent() {
        this.timer.start();
        long frames = 0;
        while (frames <= this.timer.getDuration()) {
            if (frames > TIMEOUT) {
                Assertions.fail("The timer has timed out");
            }
            this.timer.update(1.0);
            frames++;
        }
        Assertions.assertTrue(this.test, "The event is not executed as expected");
    }
}
