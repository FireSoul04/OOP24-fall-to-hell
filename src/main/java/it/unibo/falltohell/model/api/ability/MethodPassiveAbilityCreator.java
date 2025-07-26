package it.unibo.falltohell.model.api.ability;

import it.unibo.falltohell.model.api.ability.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * Functional interface representing a factory method used to create
 * {@link MethodPassiveAbility}
 * instances for a specific {@link Character} subclass.
 * 
 * @author Sara Visani
 */
@FunctionalInterface
public interface MethodPassiveAbilityCreator {

    /**
     * Creates a {@link MethodPassiveAbility} for the given {@link Character}.
     * <p>
     * 
     * @param character the character instance for which the ability should be
     *                  created
     * @return the {@link MethodPassiveAbility} instance associated with the
     *         character
     */
    MethodPassiveAbility create(Character character);
}
