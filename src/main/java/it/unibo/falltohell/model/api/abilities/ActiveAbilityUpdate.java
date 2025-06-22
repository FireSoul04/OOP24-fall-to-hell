package it.unibo.falltohell.model.api.abilities;

import it.unibo.falltohell.model.util.Vector2;

public interface ActiveAbilityUpdate {
    
    /**
     * lambda for build pattern of the ability
     * @param velocity of the ability
     * @param deltaTime time passed since last update
     */
    public void attack(final Vector2 velocity, final double deltaTime);
}
