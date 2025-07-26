package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.CustomTimer;
import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.weapons.Weapon;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Weapon that attack on close range.
 * This weapon uses a collider to check for any hits.
 *
 * @author Davide Mancini
 */
public abstract class MeleeWeapon extends GameObjectImpl implements Weapon {

    private static final long COOLDOWN_TIME = 400;

    private boolean canAttack;

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
        this.canAttack = true;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void attack() {
        if (this.canAttack) {
            this.canAttack = false;
            final String name = "melee-weapon-cooldown" + this.hashCode();
            final TimerManager tm = this.getLevel().getTimerManager();
            if (!tm.searchTimer(name)) {
                final CustomTimer attackCooldown = new CustomTimerImpl(COOLDOWN_TIME, () -> this.canAttack = true);
                tm.addTimer(name, attackCooldown);
            } else {
                tm.restartTimer(name);
            }
        }
	}

}
