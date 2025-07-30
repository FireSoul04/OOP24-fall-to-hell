package it.unibo.falltohell.model.impl.ability.passive;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.ability.passive.PassiveAbilityDo;
import it.unibo.falltohell.model.api.ability.passive.StatisticPassiveAbility;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

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

    private final Character character;
    private final PassiveAbilityDo event;

    /**
     * Constructs a new StatisticPassiveAbilityImpl.
     * <p>
     *
     * @param character the {@link Character} that holds this passive ability
     * @param lambda    the {@link PassiveAbilityDo} that defines the behavior of
     *                  this passive ability
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
    justification = "Character is only stored as a reference and not mutated")
    public StatisticPassiveAbilityImpl(final Character character, final PassiveAbilityDo lambda) {
        this.character = character;
        this.event = lambda;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void carryOut() {
        this.event.carryOut(this.character);
    }
}
