package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff;

import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.BuffManager;
import it.unibo.falltohell.model.impl.CustomTimerImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the addition and removal of buffs to the character.
 * @author Martina Malagoli
 */
public class BuffManagerImpl implements BuffManager {

    private static final long DURATION = 15 * 1000;
    private final TimerManager timerManager;
    private final Map<String, Buff> buffs;
    private long counter;

    /**
     * Initialization of the BufferManagerClass.
     * @param timerManager of the current level
     */
    public BuffManagerImpl(final TimerManager timerManager) {
        this.timerManager = timerManager;
        this.buffs = new HashMap<>();
        this.counter = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuff(final Buff buff) {
        final String name = "Buff" + this.counter;
        this.buffs.put(name, buff);
        buff.apply();
        this.timerManager.addTimer(name, new CustomTimerImpl(DURATION, buff::remove));
        this.counter++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBuffs() {
        for (final var buffEntry : buffs.entrySet()) {
            this.timerManager.removeTimer(buffEntry.getKey());
            buffEntry.getValue().remove();
        }
        this.buffs.clear();
    }
}
