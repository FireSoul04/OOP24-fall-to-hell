package it.unibo.falltohell.model.impl.buff;

import it.unibo.falltohell.model.api.statistic.Statistics;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a buff associated with the speed statistic.
 *
 * @author Martina Malagoli
 */
public class SpeedBuff extends BaseBuff {

    private final Vector2 buffAmount;

    /**
     * Initialization of the SpeedBuff class.
     *
     * @param statistics is the set of statistics associated with the
     *                            character
     * @param multiplier          is the value used to compute the buff amount that
     *                            should be
     *                            between -1 and 1
     */
    public SpeedBuff(final Statistics statistics, final double multiplier) {
        super(statistics, multiplier);
        this.buffAmount = statistics.getInitialSpeed().multiply(multiplier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        this.getCharacterStatistics().addSpeed(buffAmount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        final Statistics statistics = this.getCharacterStatistics();
        if (statistics.getInitialSpeed().magnitude() < statistics.getSpeed().magnitude()) {
            statistics.subSpeed(buffAmount);
        } else {
            statistics.setSpeed(statistics.getInitialSpeed());
        }
    }
}
