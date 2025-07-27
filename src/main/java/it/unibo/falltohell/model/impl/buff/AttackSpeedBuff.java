package it.unibo.falltohell.model.impl.buff;

import it.unibo.falltohell.model.api.statistic.CharacterStatistics;

/**
 * Class that represents a buff associated with the attack speed statistic.
 *
 * @author Martina Malagoli
 */
public class AttackSpeedBuff extends BaseBuff {

    private final double buffAmount;

    /**
     * Initialization of the AttackSpeedBuff class.
     *
     * @param characterStatistics is the set of statistics associated with the
     *                            character
     * @param multiplier          is the value used to compute the buff amount that
     *                            should be
     *                            between -1 and 1
     */
    public AttackSpeedBuff(final CharacterStatistics characterStatistics, final double multiplier) {
        super(characterStatistics, multiplier);
        this.buffAmount = characterStatistics.getInitialAttackSpeed() * multiplier;
    }

    /**
     * {@inheritDoc}
     * It works only for characters.
     */
    @Override
    public void apply() {
        final CharacterStatistics characterStatistics =  (CharacterStatistics) this.getCharacterStatistics();
        characterStatistics.addAttackSpeed(buffAmount);
    }

    /**
     * {@inheritDoc}
     * It works only for characters.
     */
    @Override
    public void remove() {
        final CharacterStatistics characterStatistics =  (CharacterStatistics) this.getCharacterStatistics();
        characterStatistics.subAttackSpeed(buffAmount);
    }
}
