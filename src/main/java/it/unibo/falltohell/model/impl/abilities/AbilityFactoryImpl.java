package it.unibo.falltohell.model.impl.abilities;

import java.util.Optional;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.abilities.AbilityFactory;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbility;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityUpdate;
import it.unibo.falltohell.model.api.abilities.active.OptionalCollision;
import it.unibo.falltohell.model.api.abilities.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.abilities.active.ActiveAbilityImpl;
import it.unibo.falltohell.model.impl.abilities.passive.MethodPassiveAbilityTest1;
import it.unibo.falltohell.model.impl.abilities.passive.MethodPassiveAbilityTest2;
import it.unibo.falltohell.model.impl.abilities.passive.StatisticPassiveAbilityImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.TestCharacter;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.TestCharacter2;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implementation of the {@link AbilityFactory} interface.
 * <p>
 * This factory creates active and passive abilities and manages 
 * a registry mapping {@link Character} subclasses to their
 * respective {@link MethodPassiveAbility} creators.
 * </p>
 * @author Sara Visani
 */
public class AbilityFactoryImpl implements AbilityFactory{

    private final MethodPassiveAbilityRegistry registry = new MethodPassiveAbilityRegistry();

    /**
     * Registers supported {@link Character} subclasses with their 
     * corresponding {@link MethodPassiveAbility} creators.
     */
    public AbilityFactoryImpl() {
        registry.register(TestCharacter.class,
            character -> new MethodPassiveAbilityTest1(character));

        registry.register(TestCharacter2.class,
            character -> new MethodPassiveAbilityTest2(character));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbility createActiveAbility(final Level level, final Vector2 position, final double damage, final Collider collider, final Vector2 velocity, final ActiveAbilityUpdate attack, final Optional<OptionalCollision> collided) {
        return new ActiveAbilityImpl(level, position, damage, collider, velocity, attack, collided);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatisticPassiveAbility createPassiveAbility(final Character character,final PassiveAbilityDo lambda) {
        return new StatisticPassiveAbilityImpl(character, lambda);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MethodPassiveAbility createMethodPassiveAbility(final Character character) {
        return registry.createAbility(character);
    }
}
