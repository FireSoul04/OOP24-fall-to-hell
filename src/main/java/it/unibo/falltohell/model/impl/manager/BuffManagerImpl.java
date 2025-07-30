package it.unibo.falltohell.model.impl.manager;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.manager.BuffManager;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the addition and removal of buffs to the character.
 * @author Martina Malagoli
 */
public class BuffManagerImpl implements BuffManager {

    private final TimerManager timerManager;
    private final Map<String, Buff> buffs;

    /**
     * Initialization of the BufferManagerClass.
     * @param timerManager of the current level
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The buff manager should have access to timer manager to add and remove timers"
    )
    public BuffManagerImpl(final TimerManager timerManager) {
        this.timerManager = timerManager;
        this.buffs = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuff(final Buff buff, final long duration, final String name) {
        if (!this.searchBuff(name)) {
            this.addToManager(buff, name);
            this.timerManager.restartIfPresent(name, new CustomTimerImpl(duration, () -> {
                buff.remove();
                this.buffs.remove(name);
            }));
        } else {
            throw new IllegalArgumentException("There must not be a duplicated buff");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInfiniteBuff(final Buff buff, final String name) {
        if (!this.searchBuff(name)) {
            this.addToManager(buff, name);
        } else {
            throw new IllegalArgumentException("There must not be a duplicated buff");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeInfiniteBuff(final String name) {
        this.buffs.get(name).remove();
        this.buffs.remove(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBuffs() {
        for (final var buffEntry : buffs.entrySet()) {
            if (this.timerManager.searchTimer(buffEntry.getKey())) {
                this.timerManager.removeTimer(buffEntry.getKey());
            }
            buffEntry.getValue().remove();
        }
        this.buffs.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean searchBuff(final String name) {
        return this.buffs.containsKey(name);
    }

    /**
     * Method to add a buff to the active buffs.
     * @param buff to be added
     * @param name of the buff
     */
    private void addToManager(final Buff buff, final String name) {
        this.buffs.put(name, buff);
        buff.apply();
    }
}
