package it.unibo.falltohell.model.impl.buff;

import it.unibo.falltohell.model.api.statistic.CharacterStatistics;

/**
 * Class that represents a buff associated with the life statistic.
 *
 * @author Martina Malagoli
 */
public class LifeBuff extends BaseBuff {

    private final double buffAmount;

    /**
     * Initialization of the LifeBuff class.
     *
     * @param characterStatistics is the set of statistics associated with the
     *                            character
     * @param multiplier          is the value used to compute the buff amount that
     *                            should be
     *                            between 0 and 1
     */
    public LifeBuff(final CharacterStatistics characterStatistics, final double multiplier) {
        super(characterStatistics, multiplier);
        if (multiplier <= 0) {
            throw new IllegalArgumentException("The life multiplier should be between 0 and 1");
        }
        this.buffAmount = characterStatistics.getFullLife() * multiplier;
    }

    /**
     * {@inheritDoc}
     * It works only for characters.
     */
    @Override
    public void apply() {
        final CharacterStatistics characterStatistics =  (CharacterStatistics) this.getCharacterStatistics();
        characterStatistics.addTemporaryLife(buffAmount);
    }

    /**
     * {@inheritDoc}
     * It works only for characters.
     */
    @Override
    public void remove() {
        final CharacterStatistics characterStatistics =  (CharacterStatistics) this.getCharacterStatistics();
        characterStatistics.subTemporaryLife(buffAmount);
    }
}
