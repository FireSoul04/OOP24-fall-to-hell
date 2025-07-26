package it.unibo.falltohell.model.impl.factory;

import it.unibo.falltohell.model.api.factory.AbilityFactory;
import it.unibo.falltohell.model.api.builder.ActiveAbilityBuilder;
import it.unibo.falltohell.model.api.ability.active.GhostAbilityCreate;
import it.unibo.falltohell.model.api.ability.active.GhostActiveAbility;
import it.unibo.falltohell.model.api.ability.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.ability.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

import it.unibo.falltohell.model.impl.ability.MethodPassiveAbilityRegistry;
import it.unibo.falltohell.model.impl.builder.ActiveAbilityImplBuilder;
import it.unibo.falltohell.model.impl.ability.active.GhostActiveAbilityImpl;
import it.unibo.falltohell.model.impl.ability.passive.StatisticPassiveAbilityImpl;

/**
 * Implementation of the {@link AbilityFactory} interface.
 * <p>
 * This factory creates active and passive abilities and manages
 * a registry mapping {@link Character} subclasses to their
 * respective {@link MethodPassiveAbility} creators.
 * </p>
 *
 * @author Sara Visani
 */
public class AbilityFactoryImpl implements AbilityFactory {

    private final MethodPassiveAbilityRegistry registry = new MethodPassiveAbilityRegistry();

    /**
     * Registers supported {@link Character} subclasses with their
     * corresponding {@link MethodPassiveAbility} creators.
     */
    public AbilityFactoryImpl() {
        /*registry.register(TestCharacter.class,
                character -> new MethodPassiveAbilityTest1(character));*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActiveAbilityBuilder buildActiveAbility() {
        return new ActiveAbilityImplBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GhostActiveAbility createGhostActiveAbility(final GhostAbilityCreate obj, final Character character) {
        return new GhostActiveAbilityImpl(obj, character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatisticPassiveAbility createPassiveAbility(final Character character, final PassiveAbilityDo lambda) {
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
