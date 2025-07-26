package it.unibo.falltohell.model.impl.buff;

import it.unibo.falltohell.model.api.statistic.CharacterStatistics;

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
     * @param characterStatistics is the set of statistics associated with the
     *                            character
     * @param multiplier          is the value used to compute the buff amount that
     *                            should be
     *                            between 0 and 1
     */
    public AttackBuff(final CharacterStatistics characterStatistics, final double multiplier) {
        super(characterStatistics, multiplier);
        this.buffAmount = super.getCharacterStatistics().getInitialAttack() * multiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        super.getCharacterStatistics().addAttack(buffAmount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        super.getCharacterStatistics().subAttack(buffAmount);
    }
}
