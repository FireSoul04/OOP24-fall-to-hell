package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.Drawable.Priority;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.api.Weapon;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Weapon that attack on close range.
 * This weapon uses a collider to check for any hits.
 *
 * @author Davide Mancini
 */
public abstract class MeleeWeapon extends GameObjectImpl implements Weapon {

	/**
	 * Creates an abstract close ranged weapon.
	 * @param lv is the level where there is the melee weapon
	 * @param position is the position of the melee weapon in the level
	 * @param collider associated to the melee weapon
	 * @param fileName is the name of the image file associated to the melee weapon
	 */
	public MeleeWeapon(final Level lv, final Vector2 position, final Collider collider, final String fileName) {
		super(lv, position, collider);
		this.initDrawable(Priority.MEDIUM, fileName);
	}

	@Override
	public void attack() {
		// TODO add a timer to determine when the weapon can check for any hits
	}

}
