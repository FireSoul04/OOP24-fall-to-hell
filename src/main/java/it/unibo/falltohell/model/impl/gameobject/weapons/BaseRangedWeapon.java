package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

import java.util.Optional;

/**
 * An abstract base class for ranged weapons that can shoot projectiles with a
 * cooldown and limited ammo.
 *
 * Handles:
 * - Ammo management (current and max)
 * - Cooldown timing between shots
 * - Projectile creation (can be overridden)
 * 
 * @author Lorenzo Casadei
 */
public abstract class BaseRangedWeapon extends BaseWeapon implements Weapon {

    private final int maxAmmo;
    private int ammo;
    private Optional<Projectile> shotProjectile;

    /**
     * Constructs a ranged weapon with offset zero and with specified maximum ammo and cooldown time.
     *
     * @param maxAmmo      the maximum ammo the weapon can carry
     * @param cooldownTime the cooldown time between attacks, in seconds
     * @param fileName     is the name of the image file associated to the ranged
     *                     weapon
     */
    protected BaseRangedWeapon(final Character owner, final int maxAmmo, final long cooldownTime, final String fileName) {
        this(owner, maxAmmo, cooldownTime, fileName, Vector2.zero());
    }

    /**
     * Constructs a ranged weapon with specified maximum ammo and cooldown time.
     *
     * @param maxAmmo      the maximum ammo the weapon can carry
     * @param cooldownTime the cooldown time between attacks, in seconds
     * @param fileName     is the name of the image file associated to the ranged
     *                     weapon
     * @param offset       where to set the position based on the owner's position
     */
    protected BaseRangedWeapon(final Character owner, final int maxAmmo, final long cooldownTime, final String fileName, final Vector2 offset) {
        super(owner, Optional.empty(), cooldownTime, fileName, offset);
        this.maxAmmo = maxAmmo;
        this.ammo = maxAmmo;
        this.shotProjectile = Optional.empty();
    }

    /**
     * {@inheritDoc}
     * Shoot a projectile.
     */
    @Override
    public void attack() {
        if (this.canShoot()) {
            super.attack();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onAttack() {
        final Projectile p = createProjectile(this.getLevel(), this.getPosition());
        ammo--;
        this.onShoot(p);
        this.shotProjectile = Optional.of(p);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<Projectile> getShotProjectile() {
        return this.shotProjectile;
    }

    /**
     * Checks if the weapon can shoot (cooldown is stopped and has ammo).
     *
     * @return true if it can shoot, false otherwise
     */
    public boolean canShoot() {
        return ammo > 0;
    }

    /**
     * Refills the weapon's ammo to max.
     */
    public void reload() {
        this.setAmmo(maxAmmo);
    }

    /**
     * Refills the weapon's ammo by a specified amount, without exceeding max ammo.
     *
     * @param numberAmmo the amount of ammo to add
     */
    public void reload(final int numberAmmo) {
        if (numberAmmo > 0 && numberAmmo <= this.getMaxAmmo()) {
            this.setAmmo(Math.min(this.getAmmo() + numberAmmo, this.getMaxAmmo()));
        }
    }

    /**
     * @return current ammo count
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * Refills the weapon's ammo to the specified amount.
     */
    public void setAmmo(final int ammo) {
        if (ammo >= 0 && ammo <= maxAmmo) {
            this.ammo = ammo;
        }
    }

    /**
     * @return max ammo
     */
    public int getMaxAmmo() {
        return maxAmmo;
    }

    /**
     * Hook for subclasses: called after a projectile is shot.
     *
     * @param projectile the projectile that was shot
     */
    protected void onShoot(final Projectile projectile) {
        // Default: do nothing
    }

    /**
     * Creates a projectile. By default, returns a ProjectileImpl.
     */
    protected abstract Projectile createProjectile(final Level level, final Vector2 position);

}
