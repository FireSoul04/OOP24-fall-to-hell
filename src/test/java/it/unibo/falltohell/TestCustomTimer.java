package it.unibo.falltohell;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test if the TimeCustomImpl class works as expected.
 * @author Martina Malagoli
 */
class TestCustomTimer {

    private CustomTimer timer;
    private boolean test;

    @BeforeEach
    void initialization() {
        this.timer = new CustomTimerImpl(1000, () -> this.test = true);
        this.test = false;
    }

    @Test
    void testTimerStart() {
        this.timer.start();
        assertTrue(this.timer.isStarted(), "The timer has not been started as it should have");
    }

    @Test
    void testExceptionTimerStart() {
        this.timer.start();
        try {
            this.timer.start();
            Assertions.fail("The timer should not be started when it is already running");
        } catch (final IllegalStateException e) {

        }
    }

    @Test
    void testTimerStop() {
        this.timer.start();
        this.timer.stop();
        assertFalse(this.timer.isStarted(), "The timer has not been stopped as it should have");
    }

    @Test
    void testExceptionTimerStop() {
        try {
            this.timer.stop();
            Assertions.fail("The timer should not be stopped when it is already not running");
        } catch (final IllegalStateException e) {

        }
    }

    @Test
    void testTimerPause() {
        this.timer.start();
        this.timer.pause();
        assertTrue(this.timer.isPaused(), "The timer has not been paused as it should have");
    }

    @Test
    void testExceptionTimerPauseIfNotStarted() {
        try {
            this.timer.pause();
            Assertions.fail("The timer should not be paused when it is not running yet");
        } catch (final IllegalStateException e) {

        }
    }

    @Test
    void testExceptionTimerPauseIfAlreadyPaused() {
        this.timer.start();
        this.timer.pause();
        try {
            this.timer.pause();
            Assertions.fail("The timer should not be paused when it is already paused");
        } catch (final IllegalStateException e) {

        }
    }

    @Test
    void testTimerResume() {
        this.timer.start();
        this.timer.pause();
        this.timer.resume();
        assertFalse(this.timer.isPaused(), "The timer has not been resumed as it should have");
    }

    @Test
    void testExceptionTimerResumeIfNotStarted() {
        try {
            this.timer.resume();
            Assertions.fail("The timer should not be resumed when it is not running yet");
        } catch (final IllegalStateException e) {

        }
    }

    @Test
    void testExceptionTimerResumeIfAlreadyResumed() {
        this.timer.start();
        this.timer.pause();
        this.timer.resume();
        try {
            this.timer.resume();
            Assertions.fail("The timer should not be resumed when it is already running");
        } catch (final IllegalStateException e) {

        }
    }

    @Test
    void testCorrectExecutionOfEvent() {
        this.timer.start();
        try {
            Thread.sleep(1100);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertTrue(this.test, "The event is not executed as expected");
    }
}
