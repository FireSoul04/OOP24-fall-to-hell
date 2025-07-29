package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

/**
 * Weapon that attack on close range.
 * This weapon uses a collider to check for any hits.
 *
 * @author Davide Mancini
 */
public abstract class BaseMeleeWeapon extends BaseWeapon {

    private final double damageMultiplier;

    /**
     * Creates an abstract close ranged weapon with offset zero.
     *
     * @param owner            of the weapon
     * @param collider         associated to the melee weapon
     * @param damageMultiplier based on owner's attack stat
     * @param cooldownTime     time to elapse between every attack
     * @param fileName         is the name of the image file associated to the melee
     *                         weapon
     */
    public BaseMeleeWeapon(final Character owner, final Collider collider,
            final double damageMultiplier, final long cooldownTime, final String fileName) {
        this(owner, collider, damageMultiplier, cooldownTime, fileName, Vector2.zero());
    }

    /**
     * Creates an abstract close ranged weapon.
     *
     * @param owner            of the weapon
     * @param collider         associated to the melee weapon
     * @param damageMultiplier based on owner's attack stat
     * @param cooldownTime     time to elapse between every attack
     * @param fileName         is the name of the image file associated to the melee
     *                         weapon
     * @param offset           where to set the position based on the owner's
     *                         position
     */
    public BaseMeleeWeapon(final Character owner, final Collider collider, final double damageMultiplier,
            final long cooldownTime, final String fileName, final Vector2 offset) {
        super(owner, Optional.of(collider), cooldownTime, fileName, offset);
        this.damageMultiplier = damageMultiplier;
    }

    /**
     * {@inheritDoc}
     * If the weapon is attacking, it checks for any enemy collided, otherwise it
     * will do nothing.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (this.isAttacking()) {
            final CharacterStatistics stats = (CharacterStatistics) this.getOwner().getStats();
            if (other instanceof Enemy e) {
                e.setDamagedLife(this.damageMultiplier * stats.getAttack());
            }
        }
    }
}
