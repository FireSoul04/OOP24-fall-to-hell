package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.api.CustomTimerEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A timer that works as a cooldown but can be paused and resumed anytime
 * @author Martina Malagoli
 */
public class CustomTimerImpl implements CustomTimer {
    private Timer timer;
    private final int duration;
    private boolean started;
    private boolean paused;
    private final TimerTask eventOnFinish;

    public CustomTimerImpl(final int duration, final CustomTimerEvent event) {
        this.started = false;
        this.paused = false;
        this.duration = duration;
        this.eventOnFinish = new TimerTask() {
            @Override
            public void run() {
                event.execute();
                started = false;
            }
        };
    }

    @Override
    public void start() {
        if (!this.started) {
            this.timer = new Timer();
            this.timer.schedule(this.eventOnFinish, this.duration);
            this.started = true;
        } else {
            throw new IllegalStateException("Cannot start a timer that is already running");
        }
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public boolean isPaused() {
        return this.paused;
    }

    @Override
    public void stop() {
        if (this.started) {
            this.timer.cancel();
            this.started = false;
        } else {
            throw new IllegalStateException("Cannot stop a timer that is not running");
        }
    }

    @Override
    public void pause() {
        try {
            if (!this.paused) {
                this.timer.wait();
                this.paused = true;
            } else {
                throw new IllegalStateException("Cannot pause a timer that is already paused");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("An interrupt has been occurred");
        }
    }

    @Override
    public void resume() {
        if (this.paused) {
            this.timer.notify();
            this.paused = false;
        } else {
            throw new IllegalStateException("Cannot resume a timer that is not paused");
        }
    }

}
