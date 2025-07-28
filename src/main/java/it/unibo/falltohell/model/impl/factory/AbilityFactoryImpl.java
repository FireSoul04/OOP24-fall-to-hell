package it.unibo.falltohell.model.impl.factory;

import it.unibo.falltohell.model.api.factory.AbilityFactory;
import it.unibo.falltohell.model.api.builder.ActiveAbilityBuilder;
import it.unibo.falltohell.model.api.ability.active.GhostAbilityCreate;
import it.unibo.falltohell.model.api.ability.active.GhostActiveAbility;
import it.unibo.falltohell.model.api.ability.active.SpecialActiveAbility;
import it.unibo.falltohell.model.api.ability.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

import it.unibo.falltohell.model.impl.ability.SpecialAbilityAbilityRegistry;
import it.unibo.falltohell.model.impl.builder.ActiveAbilityImplBuilder;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Archer;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.ability.active.BlastAbility;
import it.unibo.falltohell.model.impl.ability.active.GhostActiveAbilityImpl;
import it.unibo.falltohell.model.impl.ability.active.HealAbility;
import it.unibo.falltohell.model.impl.ability.active.ReturnArrowAbility;
import it.unibo.falltohell.model.impl.ability.active.ThrowKnifeAbility;
import it.unibo.falltohell.model.impl.ability.passive.StatisticPassiveAbilityImpl;

/**
 * Implementation of the {@link AbilityFactory} interface.
 * <p>
 * This factory creates active and passive abilities and manages
 * a registry mapping {@link Character} subclasses to their
 * respective {@link SpecialActiveAbility} creators.
 * </p>
 *
 * @author Sara Visani
 */
public class AbilityFactoryImpl implements AbilityFactory {

    private final SpecialAbilityAbilityRegistry registry = new SpecialAbilityAbilityRegistry();

    /**
     * Registers supported {@link Character} subclasses with their
     * corresponding {@link SpecialActiveAbility} creators.
     */
    public AbilityFactoryImpl() {
        registry.register(Archer.class,
                character -> new ReturnArrowAbility((Archer) character));
        registry.register(Rogue.class,
                character -> new ThrowKnifeAbility((Rogue) character));
        registry.register(Caster.class,
                character -> new BlastAbility((Caster) character));
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
    public SpecialActiveAbility createSpecialActiveAbility(final Character character) {
        return registry.createAbility(character);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecialActiveAbility createHealAbility(final Character character) {
        if (character instanceof Caster caster) {
            return new HealAbility(caster);
        }
        throw new IllegalArgumentException("You are not a Caster");
    }
}
