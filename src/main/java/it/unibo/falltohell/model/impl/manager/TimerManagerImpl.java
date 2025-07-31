package it.unibo.falltohell.model.impl.manager;

import it.unibo.falltohell.model.api.timer.CustomTimer;
import it.unibo.falltohell.model.api.manager.TimerManager;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Class to handle multiple timers.
 * @author Martina Malagoli
 */
public class TimerManagerImpl implements TimerManager {

    private final Map<String, CustomTimer> timers;

    /**
     * Initialization of the TimeManager.
     */
    public TimerManagerImpl() {
        this.timers = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTimer(final String name, final CustomTimer timer) {
        this.checkNotExists(name);
        this.timers.put(name, timer);
        timer.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTimer(final String name) {
        this.checkExists(name);
        if (this.timers.get(name).isStarted()) {
            this.timers.get(name).stop();
        }
        this.timers.remove(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllTimers() {
        this.timers.keySet().forEach(this::removeTimer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseTimer(final String name) {
        this.checkExists(name);
        if (!this.timers.get(name).isPaused()) {
            this.timers.get(name).pause();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pauseAllTimers() {
        for (final CustomTimer timer : this.timers.values()) {
            if (!timer.isPaused() && timer.isStarted()) {
                timer.pause();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeTimer(final String name) {
        this.checkExists(name);
        if (this.timers.get(name).isPaused()) {
            this.timers.get(name).resume();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumeAllTimers() {
        for (final CustomTimer timer : this.timers.values()) {
            if (timer.isPaused() && timer.isStarted()) {
                timer.resume();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartTimer(final String name) {
        this.checkExists(name);
        if (this.timers.get(name).isStarted()) {
            this.timers.get(name).stop();
        }
        this.timers.get(name).start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopTimer(final String name) {
        this.checkExists(name);
        if (this.timers.get(name).isStarted()) {
            this.timers.get(name).stop();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean searchTimer(final String name) {
        return this.timers.containsKey(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartIfPresent(final String name, final CustomTimer timer) {
        if (!this.timers.containsKey(name)) {
            this.addTimer(name, timer);
        } else {
            this.restartTimer(name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAllTimers(final double deltaTime) {
        Set.copyOf(this.timers.values()).forEach(timer -> timer.update(deltaTime));
    }

    /**
     * @param name of the timer
     * @return the timer with the given name
     */
    protected CustomTimer getTimer(final String name) {
        this.checkExists(name);
        return this.timers.get(name);
    }

    /**
     * Method to check if a timer does exist with the given name, otherwise it
     * throws an exception.
     * @param name of the timer
     */
    protected void checkExists(final String name) {
        if (!this.searchTimer(name)) {
            throw new IllegalArgumentException("There is no timer with the name:" + name);
        }
    }

    /**
     * Method to check if a timer doesn't exist with the given name, otherwise it
     * throws an exception.
     * @param name of the timer
     */
    protected void checkNotExists(final String name) {
        if (this.searchTimer(name)) {
            throw new IllegalArgumentException("There is already a timer with the name:" + name);
        }
    }
}
