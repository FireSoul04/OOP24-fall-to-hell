package it.unibo.falltohell.model.api.abilities.passive;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Interface for lambda of what the Passive ability needs to do
 * @author Sara Visani
 */
public interface PassiveAbilityDo {
    
    /**
     * What the ability needs to do
     * @param character that undergoes the ability
     */
    public void carryOut(final Character character);
}
