package it.unibo.falltohell.model.api.abilities;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

/**
 * Interface for lambda of what the Passive ability needs to do
 * @author Sara Visani
 */
public interface PassiveAbilityDo {
    
    public void carryOut(final Character character);
}
