package it.unibo.falltohell.model.api.abilities;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbility;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.abilities.active.OptionalCollision;
import it.unibo.falltohell.model.api.abilities.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Factory interface for creating different types of abilities.
 * Provides methods to create active and passive abilities.
 * 
 * @author Sara Visani
 */
public interface AbilityFactory {

    /**
     * Creates an active ability.
     * <p>
     * 
     * @param level    the {@link Level} in which the ability exists
     * @param position the position where the ability is cast
     * @param damage   the damage value of the ability
     * @param collider the {@link Collider} used for collision detection of the
     *                 ability
     * @param velocity the velocity vector ({@link Vector2}) of the ability
     * @param attack   a lambda defining the behavior of the attack, with parameters
     *                 velocity and deltaTime, see {@link ActiveAbilityUpdate}
     * @param collided an optional lambda handling collision events; pass
     *                 {@link Optional#empty()} for default collision handling
     * @return a new {@link ActiveAbility} instance
     */
    ActiveAbility createActiveAbility(Level level, Vector2 position, double damage,
            Collider collider, Vector2 velocity, ActiveAbilityUpdate attack,
            Optional<OptionalCollision> collided);

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
