package it.unibo.falltohell.model.api.abilities.active;

import it.unibo.falltohell.model.api.abilities.Ability;
import it.unibo.falltohell.model.api.gameobjects.Movable;

/**
 * Interface representing active types of abilities.
 * Extends {@link Ability} and {@link Movable}, indicating
 * that active abilities are abilities that have movement behavior.
 * 
 * @author Sara Visani
 */
public interface ActiveAbility extends Ability, Movable {
}
