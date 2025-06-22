package it.unibo.falltohell.model.api.abilities;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Interface for factory for all type of abilities
 * @author Sara Visani
 */

public interface AbilityFactory {
    
    /**
     * method to create an active ability
     * @param level level where is it
     * @param position position of the cast
     * @param damage damage of the ability
     * @param collider collider of the ability
     * @param velocity Vector2(velocity X, velocity y)
     * @param attack lambda needed for the type of movement, attack. it has two parameters velocity and deltaTime
     * @param collided this lambda is optional. give optional null if you want standard implementation of OnCollision
     * @return active ability
     */
    Ability createActiveAbility(final Level level, final Vector2 position, final double damage, final Collider collider, final Vector2 velocity, final ActiveAbilityUpdate attack, final Optional<OptionalCollision> collided);

    /**
     * method to create a passive ability
     * @param character refers to who holds this passive
     * @param lambda what needs to be done
     * @return passive ability
     */
    Ability createPassiveAbility(final Character character,final PassiveAbilityDo lambda);
}
