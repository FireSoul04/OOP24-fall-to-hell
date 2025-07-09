package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;

/**
 * Class that represents a buff associated with the life statistic.
 * @author Martina Malagoli
 */
public class LifeBuff extends BaseBuff{

    private final double buffAmount;

    /**
     * Initialization of the LifeBuff class.
     * @param characterStatistics is the set of statistics associated with the character
     * @param multiplier is the value used to compute the buff amount that should be
     *                   between 0 and 1
     */
    public LifeBuff(final CharacterStatistics characterStatistics, final double multiplier) {
        super(characterStatistics, multiplier);
        this.buffAmount = super.getCharacterStatistics().getFullLife() * multiplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply() {
        super.getCharacterStatistics().addTemporaryLife(buffAmount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove() {
        super.getCharacterStatistics().subTemporaryLife(buffAmount);
    }
}
