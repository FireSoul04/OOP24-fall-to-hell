package it.unibo.falltohell.model.impl.timer;

import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.api.timer.CustomTimerEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * A timer that works as a cooldown but can be paused and resumed anytime.
 * @author Martina Malagoli
 */
public class CustomTimerImpl implements CustomTimer {

    private Timer timer;
    private long elapsedTime;
    private boolean started;
    private boolean paused;
    private final CustomTimerEvent eventOnFinish;
    private final CountDownLatch latch;

    /**
     * Initialization of the new CustomTimer.
     * @param duration of the timer in milliseconds
     * @param event is what has to happen when the timer ends
     */
    public CustomTimerImpl(final long duration, final CustomTimerEvent event) {
        this.timer = new Timer();
        this.started = false;
        this.paused = false;
        this.eventOnFinish = () -> {
                if (!paused) {
                    elapsedTime++;
                }
                if (elapsedTime >= duration) {
                    stop();
                    event.execute();
                }
        };
        this.latch = new CountDownLatch(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        if (!this.started) {
            this.timer = new Timer();
            this.elapsedTime = 0;
            this.timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    eventOnFinish.execute();
                }
            }, 0, 1);
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
            this.latch.countDown();
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

    /**
     * {@inheritDoc}
     */
    public CountDownLatch getLatch() {
        return this.latch;
    }

}
