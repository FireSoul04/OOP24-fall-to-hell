package it.unibo.falltohell.model.impl.timer;

import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.api.timer.CustomTimerEvent;

/**
 * A timer that works as a cooldown but can be paused and resumed anytime.
 * @author Martina Malagoli
 */
public class CustomTimerImpl implements CustomTimer {

    private final long duration;
    private double elapsedTime;
    private boolean started;
    private boolean paused;
    private final CustomTimerEvent eventOnFinish;

    /**
     * Initialization of the new CustomTimer.
     * @param duration of the timer in milliseconds
     * @param event is what has to happen when the timer ends
     */
    public CustomTimerImpl(final long duration, final CustomTimerEvent event) {
        this.started = false;
        this.paused = false;
        this.eventOnFinish = event;
        this.duration = duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if (!this.started) {
            this.elapsedTime = 0;
            this.started = true;
        } else {
            throw new IllegalStateException("Cannot start a timer that is already running");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStarted() {
        return this.started;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        if (this.started) {
            this.started = false;
        } else {
            throw new IllegalStateException("Cannot stop a timer that is not running");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        if (this.paused) {
            throw new IllegalStateException("Cannot pause a timer that is already paused");
        } else if (!this.started) {
            throw new IllegalStateException("Cannot pause a timer that is not started");
        } else {
            this.paused = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        if (!this.paused) {
            throw new IllegalStateException("Cannot resume a timer that is not paused");
        } else if (!this.started) {
            throw new IllegalStateException("Cannot resume a timer that is not started");
        } else {
            this.paused = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        if (this.started && !this.paused) {
            this.elapsedTime = this.elapsedTime + deltaTime;
            if (this.elapsedTime >= this.duration) {
                this.started = false;
                this.eventOnFinish.execute();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDuration() {
        return this.duration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getElapsedTime() {
        return (long) this.elapsedTime;
    }

}
