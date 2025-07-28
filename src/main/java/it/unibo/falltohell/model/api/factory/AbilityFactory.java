package it.unibo.falltohell.model.api.factory;

import it.unibo.falltohell.model.api.builder.ActiveAbilityBuilder;
import it.unibo.falltohell.model.api.ability.active.GhostAbilityCreate;
import it.unibo.falltohell.model.api.ability.active.GhostActiveAbility;
import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.ability.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.level.Level;

/**
 * Factory interface for creating different types of abilities.
 * Provides methods to create active and passive abilities.
 * <p>
 *
 * @author Sara Visani
 * @see ActiveAbilityBuilder
 * @see StatisticPassiveAbility
 * @see SpecialActiveAbility
 * @see GhostActiveAbility
 */
public interface AbilityFactory {

    /**
     * <p>
     * Starts building a new
     * {@link it.unibo.falltohell.model.api.ability.active.ActiveAbility}
     * using a fluent {@link ActiveAbilityBuilder}.
     * </p>
     *
     * <p>
     * The builder allows you to configure:
     * <ul>
     * <li>the {@link Level} the ability belongs
     * to</li>
     * <li>position, velocity, damage, and collider</li>
     * <li>movement and update logic via
     * {@link it.unibo.falltohell.model.api.ability.active.ActiveAbilityUpdate}</li>
     * <li>optional collision behavior via
     * {@link it.unibo.falltohell.model.api.ability.active.OptionalCollision}</li>
     * </ul>
     * </p>
     *
     * @return a new instance of {@link ActiveAbilityBuilder}
     */
    ActiveAbilityBuilder buildActiveAbility();

    /**
     * <p>
     * Creates a ghost-based active ability tied to a specific character and
     * creation logic.
     * </p>
     *
     * @param obj       a {@link GhostAbilityCreate} functional interface
     *                  representing the familiar's creation logic
     * @param character the {@link Character} to which the ability belongs
     * @return a configured {@link GhostActiveAbility} instance
     *
     * @see GhostAbilityCreate
     * @see GhostActiveAbility
     * @see Character
     */
    GhostActiveAbility createGhostActiveAbility(GhostAbilityCreate obj, Character character);

    /**
     * Creates a passive ability.
     * <p>
     *
     * @param character the {@link Character} that holds this passive ability
     * @param lambda    the behavior to execute for the passive ability, see
     *                  {@link PassiveAbilityDo}
     * @return a new {@link StatisticPassiveAbility} instance
     */
    StatisticPassiveAbility createPassiveAbility(Character character, PassiveAbilityDo lambda);

    /**
     * Creates a special active ability associated with the given character.
     * <p>
     *
     * @param character the {@link Character} for which to create the method passive
     *                  ability
     * @return a new {@link SpecialActiveAbility} instance
     */
    SpecialActiveAbility createSpecialActiveAbility(Character character);

    /**
     * Creates a special ability for ONLY the caster.
     * <p>
     *
     * @param character the {@link Character} for which to create the method passive
     *                  ability
     * @return a new {@link SpecialActiveAbility} instance
     */
    SpecialActiveAbility createHealAbility(Character character);
}
