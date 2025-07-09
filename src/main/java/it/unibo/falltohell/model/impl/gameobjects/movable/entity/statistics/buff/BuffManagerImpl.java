package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff;

import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.BuffManager;
import it.unibo.falltohell.model.impl.CustomTimerImpl;

/**
 * Class to handle the addition and removal of buffs to the character.
 * @author Martina Malagoli
 */
public class BuffManagerImpl implements BuffManager {

    private static final long DURATION = 15 * 1000;
    private final TimerManager timerManager;
    private long counter;

    /**
     * Initialization of the BufferManagerClass.
     * @param timerManager of the current level
     */
    public BuffManagerImpl(TimerManager timerManager) {
        this.timerManager = timerManager;
        this.counter = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuff(final Buff buff) {
        final String name = "Buff" + this.counter;
        buff.apply();
        this.timerManager.addTimer(name, new CustomTimerImpl(DURATION, buff::remove));
        this.counter++;
    }
}
