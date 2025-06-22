package it.unibo.falltohell.model.api.abilities;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface for factory for all type of abilities
 * @author Sara Visani
 */

public interface AbilityFactory {
    
    Ability createActiveAbility(final Level level, final Vector2 position, final double damage, final Collider collider, final Vector2 velocity, final ActiveAbilityUpdate attack, final Optional<OptionalCollision> collided);

    Ability createPassiveAbility();
}
