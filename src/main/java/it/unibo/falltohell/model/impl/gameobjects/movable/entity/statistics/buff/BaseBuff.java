package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;

/**
 * Class that represents a generic buff.
 * @author Martina Malagoli
 */
public abstract class BaseBuff implements Buff {

    private final CharacterStatistics characterStatistics;

    /**
     * Initialization of the BaseBuff class.
     * @param characterStatistics is the set of statistics associated with the character
     */
    public BaseBuff(final CharacterStatistics characterStatistics, final double multiplier) {
        this.characterStatistics = characterStatistics;
        if (multiplier <= 0 || multiplier > 1) {
            throw new IllegalArgumentException("The multiplier should be between the values of 0 and 1");
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
     * @return the set of statistics associated with the character
     */
    protected CharacterStatistics getCharacterStatistics() {
        return this.characterStatistics;
    }

}
