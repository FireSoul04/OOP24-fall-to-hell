package it.unibo.falltohell.model.api.abilities.passive;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Functional interface representing the action to be carried out by a passive
 * ability.
 * <p>
 * It defines a single method describing what the passive ability does to a
 * given {@link Character}.
 * </p>
 *
 * @author Sara Visani
 */
@FunctionalInterface
public interface PassiveAbilityDo {

    /**
     * Executes the passive ability's effect on the specified {@link Character}.
     *
     * @param character the {@link Character} instance that undergoes the passive
     *                  ability effect
     */
    void carryOut(Character character);
}
