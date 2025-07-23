package it.unibo.falltohell.model.impl.abilities;

import it.unibo.falltohell.model.api.abilities.AbilityFactory;
import it.unibo.falltohell.model.api.abilities.active.ActiveAbilityBuilder;
import it.unibo.falltohell.model.api.abilities.active.GhostAbilityCreate;
import it.unibo.falltohell.model.api.abilities.active.GhostActiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.MethodPassiveAbility;
import it.unibo.falltohell.model.api.abilities.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

import it.unibo.falltohell.model.impl.abilities.active.ActiveAbilityImplBuilder;
import it.unibo.falltohell.model.impl.abilities.active.GhostActiveAbilityImpl;
import it.unibo.falltohell.model.impl.abilities.passive.StatisticPassiveAbilityImpl;

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
