package it.unibo.falltohell.model.api.abilities;

/**
 * Interface for factory for all type of abilities
 * @author Sara Visani
 */

public interface AbilityFactory {
    
    Ability createActiveAbility();

    Ability createPassiveAbility();
}
