package it.unibo.falltohell.model.api.ability.passive;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * Interface for a passive ability that interferes with the
 * statistics of a
 * {@link Character}.
 * <p>
 * This type of ability typically modifies or affects the character's stats
 * passively.
 *
 * @author Sara Visani
 * @see PassiveAbility
 */
public interface StatisticPassiveAbility extends PassiveAbility {

    /**
     * Executes the passive ability logic.
     */
    void carryOut();
}
