package it.unibo.falltohell.model.api.abilities;

import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityBuilder;
import it.unibo.falltohell.model.api.abilities.active.GhostAbilityCreate;
import it.unibo.falltohell.model.api.abilities.active.GhostActiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Factory interface for creating different types of abilities.
 * Provides methods to create active and passive abilities.
 * <p>
 *
 * @author Sara Visani
 * @see ActiveAbilityBuilder
 * @see StatisticPassiveAbility
 * @see MethodPassiveAbility
 * @see GhostActiveAbility
 */
public interface AbilityFactory {

    /**
     * <p>
     * Starts building a new
     * {@link it.unibo.falltohell.model.api.abilities.active.ActiveAbility}
     * using a fluent {@link ActiveAbilityBuilder}.
     * </p>
     *
     * <p>
     * The builder allows you to configure:
     * <ul>
     * <li>the {@link it.unibo.falltohell.model.api.Level} the ability belongs
     * to</li>
     * <li>position, velocity, damage, and collider</li>
     * <li>movement and update logic via
     * {@link it.unibo.falltohell.model.api.abilities.active.ActiveAbilityUpdate}</li>
     * <li>optional collision behavior via
     * {@link it.unibo.falltohell.model.api.abilities.active.OptionalCollision}</li>
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
     * Creates a method-based passive ability associated with the given character.
     * <p>
     *
     * @param character the {@link Character} for which to create the method passive
     *                  ability
     * @return a new {@link MethodPassiveAbility} instance
     */
    MethodPassiveAbility createMethodPassiveAbility(Character character);
}
