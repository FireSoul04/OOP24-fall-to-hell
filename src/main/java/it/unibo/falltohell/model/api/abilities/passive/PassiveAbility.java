package it.unibo.falltohell.model.api.abilities.passive;

import it.unibo.falltohell.model.api.abilities.Ability;

/**
 * Interface for Passive type of abilities
 * @author Sara Visani
 */
public interface PassiveAbility extends Ability{

    /**
     * Execute the passive ability
     */
    public void carryOut();
}
