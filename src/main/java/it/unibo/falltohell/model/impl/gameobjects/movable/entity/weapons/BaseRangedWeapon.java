package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;
import it.unibo.falltohell.model.api.Weapon;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.ProjectileImpl;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.CustomTimer;


/**
 * An abstract base class for ranged weapons that can shoot projectiles with a cooldown and limited ammo.
 * 
 * Handles:
 * - Ammo management (current and max)
 * - Cooldown timing between shots
 * - Projectile creation (can be overridden)
 */
public abstract class BaseRangedWeapon implements Weapon {

    private int ammo;
    private final int maxAmmo;
    private final long cooldownTimeMs;
    private final CustomTimer cooldownTimer;

    /**
     * Constructs a ranged weapon with specified maximum ammo and cooldown time.
     *
     * @param maxAmmo the maximum ammo the weapon can carry
     * @param cooldownTime the cooldown time between attacks, in seconds
     */
    protected BaseRangedWeapon(int maxAmmo, double cooldownTime) {
        this.maxAmmo = maxAmmo;
        this.ammo = maxAmmo;
        this.cooldownTimeMs = (long) (cooldownTime * 1000);
        this.cooldownTimer = new CustomTimerImpl(this.cooldownTimeMs, () -> {});
    }


    /**
     * Attempts to attack (shoot a projectile) if possible.
     * @param level the level where the projectile will be spawned
     * @param position spawn position
     * @param speedX horizontal speed of the projectile
     * @param speedY vertical speed of the projectile
     * @param width width of the projectile
     * @param height height of the projectile
     * @param collider collider for the projectile
     */
    public Projectile attack(Level level, Vector2 position, double speedX, double speedY, double width, double height, Collider collider) {
        if (canShoot()) {
            Projectile p = createProjectile(level, position, speedX, speedY, width, height, collider);
            ammo--;
            cooldownTimer.start();
            onShoot(p);
            return p;
        }
        return null;
    }



    /**
     * Checks if the weapon can shoot (cooldown is stopped and has ammo).
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
    public void reload(int numberAmmo){
        if(numberAmmo > 0 && numberAmmo <= this.getMaxAmmo()) {
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
    public void setAmmo(int ammo) {
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
    protected Projectile createProjectile(Level level, Vector2 position, double speedX, double speedY, double width, double height, Collider collider) {
        return new ProjectileImpl(level, position, width, height, speedX, speedY, collider);
    }
    

    /**
     * Hook for subclasses: called after a projectile is shot.
     * @param projectile the projectile that was shot
     */
    protected void onShoot(Projectile projectile) {
        // Default: do nothing
    }

    
    
}
