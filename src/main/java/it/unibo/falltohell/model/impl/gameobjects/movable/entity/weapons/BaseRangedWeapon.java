package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.Drawable.Priority;
import it.unibo.falltohell.model.api.Weapon;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.ProjectileImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.Level;

import it.unibo.falltohell.model.api.CustomTimer;

/**
 * An abstract base class for ranged weapons that can shoot projectiles with a
 * cooldown and limited ammo.
 *
 * Handles:
 * - Ammo management (current and max)
 * - Cooldown timing between shots
 * - Projectile creation (can be overridden)
 */
public abstract class BaseRangedWeapon extends GameObjectImpl implements Weapon {

    private int ammo;
    private final int maxAmmo;
    private final long cooldownTimeMs;
    private final CustomTimer cooldownTimer;

    /**
     * Constructs a ranged weapon with specified maximum ammo and cooldown time.
     *
     * @param maxAmmo      the maximum ammo the weapon can carry
     * @param cooldownTime the cooldown time between attacks, in seconds
     * @param fileName is the name of the image file associated to the ranged weapon
     */
    protected BaseRangedWeapon(final Level lv, final Vector2 position, final int maxAmmo, final double cooldownTime,
                               final String fileName) {
        super(lv, position, new BoxCollider(new Dimensions(0, 0)));
        this.maxAmmo = maxAmmo;
        this.ammo = maxAmmo;
        this.cooldownTimeMs = (long) (cooldownTime * 1000);
        this.cooldownTimer = new CustomTimerImpl(this.cooldownTimeMs, () -> {
        });
        this.initDrawable(Priority.MEDIUM, fileName);
    }

    /**
     * Attempts to attack (shoot a projectile) if possible.
     *
     * @param level    the level where the projectile will be spawned
     * @param position spawn position
     * @param speedX   horizontal speed of the projectile
     * @param speedY   vertical speed of the projectile
     * @param width    width of the projectile
     * @param height   height of the projectile
     * @param collider collider for the projectile
     */
    public Projectile attack(final Level level, final Vector2 position, final Vector2 speed, final Collider collider) {
        if (canShoot()) {
            final Projectile p = createProjectile(level, position, speed, collider);
            ammo--;
            cooldownTimer.start();
            onShoot(p);
            return p;
        }
        return null;
    }

    /**
     * Checks if the weapon can shoot (cooldown is stopped and has ammo).
     *
     * @return true if can shoot, false otherwise
     */
    public boolean canShoot() {
        return ammo > 0 && !cooldownTimer.isStarted();
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
     * Creates a projectile. By default, returns a ProjectileImpl.
     */
    protected Projectile createProjectile(final Level level, final Vector2 position, final Vector2 speed,
            final Collider collider) {
        return new ProjectileImpl(level, position, speed, collider, "");
    }

    /**
     * Hook for subclasses: called after a projectile is shot.
     *
     * @param projectile the projectile that was shot
     */
    protected void onShoot(final Projectile projectile) {
        // Default: do nothing
    }

}
