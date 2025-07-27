package it.unibo.falltohell.model.impl.buff;

import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
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
     * @param characterStatistics is the set of statistics associated with the
     *                            character
     * @param multiplier          is the value used to compute the buff amount that
     *                            should be
     *                            between 0 and 1
     */
    public SpeedBuff(final CharacterStatistics characterStatistics, final double multiplier) {
        super(characterStatistics, multiplier);
        this.buffAmount = this.getCharacterStatistics().getInitialSpeed().multiply(multiplier);
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
        final CharacterStatistics statistics = this.getCharacterStatistics();
        if (statistics.getInitialSpeed().magnitude() < statistics.getSpeed().magnitude()) {
            statistics.subSpeed(buffAmount);
        } else {
            statistics.setSpeed(statistics.getInitialSpeed());
        }
    }
}
