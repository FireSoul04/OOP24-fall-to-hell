package it.unibo.falltohell.model.api.abilities.passive;

/**
 * Interface for a passive ability that interferes with the statistics of a {@link it.unibo.falltohell.model.api.gameobjects.movable.entity.Character}.
 * <p>
 * This type of ability typically modifies or affects the character's stats passively.
 * @author Sara Visani
 * @see PassiveAbility
 */
public interface StatisticPassiveAbility extends PassiveAbility{

    /**
     * Executes the passive ability logic.
     */
    public void carryOut();
}
