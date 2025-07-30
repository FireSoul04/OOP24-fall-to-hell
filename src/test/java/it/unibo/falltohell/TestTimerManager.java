package it.unibo.falltohell;

import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.model.impl.manager.TimerManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Class to test if the timer manager works as expected.
 * @author Martina Malagoli
 */
class TestTimerManager {

    private static final String INFO_MESSAGE = "The IllegalArgumentException was thrown correctly";
    private static final long DURATION = 500;
    private static final String TIMER_NAME = "Timer";
    private CustomTimer timer;
    private TimerManager timerManager;
    private Logger logger;

    /**
     * Initialization of the variables used in the tests.
     */
    @BeforeEach
    void initialization() {
        this.timer = new CustomTimerImpl(DURATION, () -> { });
        this.timerManager = new TimerManagerImpl();
        this.logger = Logger.getLogger("TimerManagerLogger");
    }

    /**
     * Tests if a timer is added correctly and if it is thrown an exception when
     * someone tries to add a timer with the same name of one already existent.
     */
    @Test
    void testAddTimer() {
        this.timerManager.addTimer(TIMER_NAME, this.timer);
        assertTrue(this.timerManager.searchTimer(TIMER_NAME), "The timer was not added correctly");
        try {
            this.timerManager.addTimer(TIMER_NAME, this.timer);
            Assertions.fail("An already existent timer should not be replaced");
        } catch (final IllegalArgumentException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if a timer is removed correctly and if it is thrown an exception when
     * someone tries to remove a non-existent timer.
     */
    @Test
    void testRemoveTimer() {
        this.timerManager.addTimer(TIMER_NAME, this.timer);
        this.timerManager.removeTimer(TIMER_NAME);
        assertFalse(this.timerManager.searchTimer(TIMER_NAME));
        try {
            this.timerManager.removeTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be removed");
        } catch (final IllegalArgumentException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if a timer is paused and resumed correctly.
     */
    @Test
    void testPauseAndResumeTimer() {
        this.timerManager.addTimer(TIMER_NAME, this.timer);
        this.timerManager.pauseTimer(TIMER_NAME);
        assertTrue(this.timer.isPaused(), "The timer was not paused correctly");
        this.timerManager.resumeTimer(TIMER_NAME);
        assertFalse(this.timer.isPaused(), "The timer was not resumed correctly");
        this.timerManager.removeTimer(TIMER_NAME);
        try {
            this.timerManager.pauseTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be paused");
        } catch (final IllegalArgumentException e) {
            this.logger.info(INFO_MESSAGE);
        }
        try {
            this.timerManager.resumeTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be resumed");
        } catch (final IllegalArgumentException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    /**
     * Tests if a timer is stopped and restarted correctly.
     */
    @Test
    void testRestartAndStopTimer() {
        this.timerManager.addTimer(TIMER_NAME, this.timer);
        this.timerManager.stopTimer(TIMER_NAME);
        assertFalse(this.timer.isStarted(), "The timer was not stopped correctly");
        this.timerManager.restartTimer(TIMER_NAME);
        assertTrue(this.timer.isStarted(), "The timer was not restarted correctly");
        this.timerManager.removeTimer(TIMER_NAME);
        try {
            this.timerManager.stopTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be stopped");
        } catch (final IllegalArgumentException e) {
            this.logger.info(INFO_MESSAGE);
        }
        try {
            this.timerManager.restartTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be restarted");
        } catch (final IllegalArgumentException e) {
            this.logger.info(INFO_MESSAGE);
        }
    }

    @Test
    void testRestartIfPresent() {
        this.timerManager.restartIfPresent(TIMER_NAME, this.timer);
        try {
            this.timerManager.restartIfPresent(TIMER_NAME, this.timer);
        } catch (final IllegalArgumentException e) {
            Assertions.fail("The timer should have been restarted but it was tried to be added");
        }
    }

}
