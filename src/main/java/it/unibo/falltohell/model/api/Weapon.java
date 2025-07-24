package it.unibo.falltohell.model.api;

/**
 * Interface for any weapon in the game.
 *
 * @author Davide Mancini
 */
public interface Weapon extends GameObject {

	/**
	 * Perform an attack.
	 */
	void attack();
}
