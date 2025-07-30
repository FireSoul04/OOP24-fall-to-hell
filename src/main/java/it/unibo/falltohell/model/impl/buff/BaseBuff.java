package it.unibo.falltohell.model.impl.buff;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.statistic.Statistics;

/**
 * Class that represents a generic buff.
 * @author Martina Malagoli
 */
public abstract class BaseBuff implements Buff {

    private final Statistics statistics;

    /**
     * Initialization of the BaseBuff class.
     * @param statistics is the set of statistics associated with an entity
     * @param multiplier is the value used to compute the buff amount that should be
     *                   between -1 and 1
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The buff must know which stats to use"
    )
    public BaseBuff(final Statistics statistics, final double multiplier) {
        this.statistics = statistics;
        if (multiplier <= -1 || multiplier > 1) {
            throw new IllegalArgumentException("The multiplier should be between the values of -1 and 1");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void apply();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void remove();

    /**
     * @return the set of statistics associated with the entity
     */
    protected Statistics getCharacterStatistics() {
        return this.statistics;
    }

}
