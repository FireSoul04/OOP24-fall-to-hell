package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.api.TimerManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle multiple timers
 * @author Martina Malagoli
 */
public class TimerManagerImpl implements TimerManager {
    private final Map<String, CustomTimer> timers;
    //private boolean allPaused; TODO --> VERIFY IF NEEDED

    public TimerManagerImpl() {
        this.timers = new HashMap<>();
        //this.allPaused = false;
    }

    @Override
    public void addTimer(String name, CustomTimer timer) {
        this.timers.put(name, timer);
        timer.start();
    }

    @Override
    public void removeTimer(String name) {
        this.timers.get(name).stop();
        this.timers.remove(name);
    }

    @Override
    public void pauseTimer(String name) {
        this.timers.get(name).pause();
    }

    @Override
    public void pauseAllTimers() {
        for (final CustomTimer timer : this.timers.values()) {
            if (!timer.isPaused()) {
                timer.pause();
            }
        }
    }

    @Override
    public void resumeTimer(String name) {
        this.timers.get(name).resume();
    }

    @Override
    public void resumeAllTimers() {
        for (final CustomTimer timer : this.timers.values()) {
            if (timer.isPaused()) {
                timer.resume();
            }
        }
    }
}
