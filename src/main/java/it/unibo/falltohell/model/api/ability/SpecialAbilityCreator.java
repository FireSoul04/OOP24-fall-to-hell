package it.unibo.falltohell.model.api.ability;

import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * Functional interface representing a factory method used to create
 * {@link SpecialActiveAbility}
 * instances for a specific {@link Character} subclass.
 *
 * @author Sara Visani
 */
@FunctionalInterface
public interface SpecialAbilityCreator {

    /**
     * Creates a {@link SpecialActiveAbility} for the given {@link Character}.
     * <p>
     *
     * @param character the character instance for which the ability should be
     *                  created
     * @return the {@link SpecialActiveAbility} instance associated with the
     *         character
     */
    SpecialActiveAbility create(Character character);
}
