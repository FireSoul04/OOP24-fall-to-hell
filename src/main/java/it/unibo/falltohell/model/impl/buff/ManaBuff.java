package it.unibo.falltohell.model.impl.buff;

import it.unibo.falltohell.model.api.statistic.CharacterStatistics;

/**
 * Class that represents a buff associated with the mana statistic.
 * @author Martina Malagoli
 */
public class ManaBuff extends BaseBuff {

    private final double buffAmount;

    /**
     * Initialization of the ManaBuff class.
     * @param characterStatistics is the set of statistics associated with the character
     * @param multiplier is the value used to compute the buff amount that should be
     *                   between 0 and 1
     */
    public ManaBuff(final CharacterStatistics characterStatistics, final double multiplier) {
        super(characterStatistics, multiplier);
        this.buffAmount = super.getCharacterStatistics().getInitialMana() * multiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        super.getCharacterStatistics().addTemporaryMana(buffAmount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        super.getCharacterStatistics().subTemporaryMana(buffAmount);
    }
}
