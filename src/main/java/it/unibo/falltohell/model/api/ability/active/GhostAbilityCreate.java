package it.unibo.falltohell.model.api.ability.active;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * <p>
 * Functional interface for creating ghost familiars or ghost-related abilities
 * associated with a given {@link Character}.
 * </p>
 *
 * <p>
 * Typically used in conjunction with {@link GhostActiveAbility} to define the
 * logic
 * executed when the ability is activated.
 * </p>
 *
 * @author Sara Visani
 * @see Character
 * @see GhostActiveAbility
 */
@FunctionalInterface
public interface GhostAbilityCreate {

    /**
     * <p>
     * Creates or spawns the ghost-related ability or entity for the specified
     * character.
     * </p>
     *
     * @param character the {@link Character} the ghost ability will be associated
     *                  with
     */
    void create(Character character);
}
