package it.unibo.falltohell.model.impl.buff;

import it.unibo.falltohell.model.api.statistic.Statistics;

/**
 * Class that represents a buff associated with the attack statistic.
 *
 * @author Martina Malagoli
 */
public class AttackBuff extends BaseBuff {

    private final double buffAmount;

    /**
     * Initialization of the AttackBuff class.
     *
     * @param statistics is the set of statistics associated with an
     *                            entity
     * @param multiplier          is the value used to compute the buff amount that
     *                            should be
     *                            between -1 and 1
     */
    public AttackBuff(final Statistics statistics, final double multiplier) {
        super(statistics, multiplier);
        this.buffAmount = statistics.getInitialAttack() * multiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        this.getCharacterStatistics().addAttack(buffAmount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        this.getCharacterStatistics().subAttack(buffAmount);
    }
}
