package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.api.Weapon;

/**
 * Weapon that attack on close range.
 * This weapon uses a collider to check for any hits.
 *
 * @author Davide Mancini
 */
public abstract class MeleeWeapon implements Weapon {

	private final Collider collider;

	/**
	 * Creates an abstract close ranged weapon.
	 * @param collider representing the hitbox of the weapon when it attacks
	 */
	public MeleeWeapon(final Collider collider) {
		this.collider = collider;
	}

	@Override
	public void attack() {
		// TODO add a timer to determine when the weapon can check for any hits
	}

	/**
	 * @return hitbox collider
	 */
	public Collider getCollider() {
		return this.collider;
	}
}
