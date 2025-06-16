package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.api.CustomTimerEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A timer that works as a cooldown but can be paused and resumed anytime.
 * @author Martina Malagoli
 */
public class CustomTimerImpl implements CustomTimer {

    private Timer timer;
    private int elapsedTime;
    private boolean started;
    private boolean paused;
    private final TimerTask eventOnFinish;

    /**
     * Initialization of the new CustomTimer.
     * @param duration of the timer
     * @param event is what has to happen when the timer ends
     */
    public CustomTimerImpl(final int duration, final CustomTimerEvent event) {
        this.timer = new Timer();
        this.started = false;
        this.paused = false;
        this.eventOnFinish = new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    elapsedTime++;
                }
                if (elapsedTime >= duration) {
                    event.execute();
                    stop();
                }
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if (!this.started) {
            this.timer = new Timer();
            this.elapsedTime = 0;
            this.timer.scheduleAtFixedRate(this.eventOnFinish, 0, 1);
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
            this.timer.cancel();
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
        if (!this.paused && this.started) {
            this.paused = true;
        } else {
            throw new IllegalStateException("Cannot pause a timer that is already paused");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {
        if (this.paused && this.started) {
            this.paused = false;
        } else {
            throw new IllegalStateException("Cannot resume a timer that is not paused");
        }
    }

}
