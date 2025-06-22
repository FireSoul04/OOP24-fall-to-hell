package it.unibo.falltohell.model.api.abilities;

import it.unibo.falltohell.model.util.Vector2;

public interface ActiveAbilityUpdate {

    public void attack(final Vector2 velocity, final double deltaTime);
}
