package it.unibo.falltohell;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.TimerManagerImpl;
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

    private static final long DURATION = 500;
    private static final String TIMER_NAME = "Timer";
    private TimerManager timerManager;
    private Logger logger;

    @BeforeEach
    void initialization() {
        this.timerManager = new TimerManagerImpl();
        this.logger = Logger.getLogger("TimerManagerLogger");
    }

    @Test
    void testAddTimer() {
        this.timerManager.addTimer(TIMER_NAME, new CustomTimerImpl(DURATION, () -> {}));
        assertTrue(this.timerManager.searchTimer(TIMER_NAME), "The timer was not added correctly");
        try {
            this.timerManager.addTimer(TIMER_NAME, new CustomTimerImpl(DURATION, () -> {}));
            Assertions.fail("An already existent timer should not be replaced");
        } catch (final IllegalArgumentException e) {
            this.logger.info("The IllegalArgumentException was thrown correctly");
        }
    }

    @Test
    void testRemoveTimer() {
        this.timerManager.addTimer(TIMER_NAME, new CustomTimerImpl(DURATION, () -> {}));
        this.timerManager.removeTimer(TIMER_NAME);
        assertFalse(this.timerManager.searchTimer(TIMER_NAME));
        try {
            this.timerManager.removeTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be removed");
        } catch (final IllegalArgumentException e) {
            this.logger.info("The IllegalArgumentException was thrown correctly");
        }
    }

    @Test
    void testPauseAndResumeTimer() {
        final CustomTimer timer = new CustomTimerImpl(DURATION, () -> {});
        this.timerManager.addTimer(TIMER_NAME, timer);
        this.timerManager.pauseTimer(TIMER_NAME);
        assertTrue(timer.isPaused(), "The timer was not paused correctly");
        this.timerManager.resumeTimer(TIMER_NAME);
        assertFalse(timer.isPaused(), "The timer was not resumed correctly");
        this.timerManager.removeTimer(TIMER_NAME);
        try {
            this.timerManager.pauseTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be paused");
        } catch (final IllegalArgumentException e) {
            this.logger.info("The IllegalArgumentException was thrown correctly");
        }
        try {
            this.timerManager.resumeTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be resumed");
        } catch (final IllegalArgumentException e) {
            this.logger.info("The IllegalArgumentException was thrown correctly");
        }
    }

    @Test
    void testRestartAndStopTimer() {
        final CustomTimer timer = new CustomTimerImpl(DURATION, () -> {});
        this.timerManager.addTimer(TIMER_NAME, timer);
        this.timerManager.stopTimer(TIMER_NAME);
        assertFalse(timer.isStarted(), "The timer was not stopped correctly");
        this.timerManager.restartTimer(TIMER_NAME);
        assertTrue(timer.isStarted(), "The timer was not restarted correctly");
        this.timerManager.removeTimer(TIMER_NAME);
        try {
            this.timerManager.stopTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be stopped");
        } catch (final IllegalArgumentException e) {
            this.logger.info("The IllegalArgumentException was thrown correctly");
        }
        try {
            this.timerManager.restartTimer(TIMER_NAME);
            Assertions.fail("A non existent timer should not be restarted");
        } catch (final IllegalArgumentException e) {
            this.logger.info("The IllegalArgumentException was thrown correctly");
        }
    }
}
