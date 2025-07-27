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
        if (multiplier <= 0) {
            throw new IllegalArgumentException("The mana multiplier should be between 0 and 1");
        }
        this.buffAmount = characterStatistics.getInitialMana() * multiplier;
    }

    /**
     * {@inheritDoc}
     * It works only for characters.
     */
    @Override
    public void apply() {
        final CharacterStatistics characterStatistics =  (CharacterStatistics) this.getCharacterStatistics();
        characterStatistics.addTemporaryMana(buffAmount);
    }

    /**
     * {@inheritDoc}
     * It works only for characters.
     */
    @Override
    public void remove() {
        final CharacterStatistics characterStatistics =  (CharacterStatistics) this.getCharacterStatistics();
        characterStatistics.subTemporaryMana(buffAmount);
    }
}
