package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.api.TimerManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle multiple timers.
 * @author Martina Malagoli
 */
public class TimerManagerImpl implements TimerManager {

    private final Map<String, CustomTimer> timers;
    //private boolean allPaused; TODO --> VERIFY IF NEEDED

    /**
     * Initialization of the TimeManager.
     */
    public TimerManagerImpl() {
        this.timers = new HashMap<>();
        //this.allPaused = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTimer(final String name, final CustomTimer timer) {
        this.timers.put(name, timer);
        timer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTimer(final String name) {
        this.timers.get(name).stop();
        this.timers.remove(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseTimer(final String name) {
        this.timers.get(name).pause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseAllTimers() {
        for (final CustomTimer timer : this.timers.values()) {
            if (!timer.isPaused()) {
                timer.pause();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeTimer(final String name) {
        this.timers.get(name).resume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeAllTimers() {
        for (final CustomTimer timer : this.timers.values()) {
            if (timer.isPaused()) {
                timer.resume();
            }
        }
    }
}
