package it.unibo.falltohell.model.impl.abilities.passive;

import it.unibo.falltohell.model.api.abilities.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.abilities.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Implementation of the {@link StatisticPassiveAbility} interface,
 * representing a passive ability associated with a {@link Character}.
 * <p>
 * It uses a {@link PassiveAbilityDo} functional interface to define
 * the behavior executed when the passive ability is carried out.
 * </p>
 * 
 * @author Sara Visani
 */
public class StatisticPassiveAbilityImpl implements StatisticPassiveAbility {

    final private Character character;
    final private PassiveAbilityDo event;

    /**
     * Constructs a new StatisticPassiveAbilityImpl.
     * <p>
     * 
     * @param character the {@link Character} that holds this passive ability
     * @param lambda    the {@link PassiveAbilityDo} that defines the behavior of
     *                  this passive ability
     */
    public StatisticPassiveAbilityImpl(final Character character, final PassiveAbilityDo lambda) {
        this.character = character;
        this.event = lambda;
    }

    /**
     * {@inheritDoc}
     */
    public void carryOut() {
        this.event.carryOut(this.character);
    }
}