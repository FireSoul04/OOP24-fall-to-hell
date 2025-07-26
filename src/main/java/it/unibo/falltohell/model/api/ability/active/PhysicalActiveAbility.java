package it.unibo.falltohell.model.api.ability.active;

import it.unibo.falltohell.model.api.gameobject.movable.Movable;

/**
 * <p>
 * Represents an {@link ActiveAbility} that also has physical movement
 * capabilities in the game world.
 * </p>
 *
 * <p>
 * This interface combines active abilities with movement behavior by extending
 * both {@link ActiveAbility} and {@link Movable}.
 * It is typically used for projectiles or effects that have position, velocity,
 * and collision handling.
 * </p>
 *
 * @author Sara Visani
 * @see ActiveAbility
 * @see Movable
 */
public interface PhysicalActiveAbility extends ActiveAbility, Movable {
}
