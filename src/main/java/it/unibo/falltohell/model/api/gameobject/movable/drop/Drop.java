package it.unibo.falltohell.model.api.gameobject.movable.drop;

import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.buff.Buff;

/**
 * A {@link Movable} object that contains a {@link Buff} and can be collected by
 * a player character.
 *
 * <p>
 * When a Drop collides with a character, it applies its buff and disappears.
 * It may also be removed automatically after a fixed amount of time.
 * </p>
 *
 * @see Buff
 * @see Movable
 *
 * @author Sara Visani
 */
public interface Drop extends Movable {

}
