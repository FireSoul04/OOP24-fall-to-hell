package it.unibo.falltohell.model.impl.abilities;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.Ability;
import it.unibo.falltohell.model.api.abilities.AbilityFactory;
import it.unibo.falltohell.model.api.abilities.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.abilities.OptionalCollision;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;

public class AbilityFactoryImpl implements AbilityFactory{

    /*
     * {@inheritDoc}
     */
    @Override
    public Ability createActiveAbility(final Level level, final Vector2 position, final double damage, final Collider collider, final Vector2 velocity, final ActiveAbilityUpdate attack, final Optional<OptionalCollision> collided) {
        return new ActiveAbilityImpl(level, position, damage, collider, velocity, attack, collided);
    }

    @Override
    public Ability createPassiveAbility() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPassiveAbility'");
    }
    
}
