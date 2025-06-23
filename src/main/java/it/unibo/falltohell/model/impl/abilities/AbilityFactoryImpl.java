package it.unibo.falltohell.model.impl.abilities;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.Ability;
import it.unibo.falltohell.model.api.abilities.AbilityFactory;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.abilities.active.OptionalCollision;
import it.unibo.falltohell.model.api.abilities.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implementation of the interface AbilityFactory
 * @author Sara Visani
 */
public class AbilityFactoryImpl implements AbilityFactory{

    /*
     * {@inheritDoc}
     */
    @Override
    public Ability createActiveAbility(final Level level, final Vector2 position, final double damage, final Collider collider, final Vector2 velocity, final ActiveAbilityUpdate attack, final Optional<OptionalCollision> collided) {
        return new ActiveAbilityImpl(level, position, damage, collider, velocity, attack, collided);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Ability createPassiveAbility(final Character character,final PassiveAbilityDo lambda) {
        return new StatisticPassiveAbilityImpl(character, lambda);
    }

    @Override
    public Ability createMethodPassiveAbility(final Character character) {
        //TODO
        return null;
    }
}
