package it.unibo.falltohell.model.api.abilities;

import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface for lambda of how Active ability updates
 * @author Sara Visani
 */
public interface ActiveAbilityUpdate {
    
    /**
     * lambda for build pattern of the ability
     * @param velocity of the ability
     * @param deltaTime time passed since last update
     */
    public void attack(final Vector2 velocity, final double deltaTime);
}
