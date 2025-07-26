package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.api.gameobject.movable.Projectile;

import java.util.Optional;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Archer;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.ReturnableArrow;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * A bow weapon used by the Archer character.
 *
 * It creates {@link ReturnableArrow} projectiles when fired, allowing arrows to be recalled later.
 * @author Lorenzo Casadei
 */
public class Bow extends BaseRangedWeapon {

    private Archer owner;
    private final Vector2 projectileSpeed;

    /**
     * Constructs a new Bow with the specified ammo and cooldown.
     *
     * @param ammo     the initial amount of ammo
     * @param cooldown the cooldown time between shots
     */
    public Bow(final Character owner, final int ammo, final long cooldown, final String fileName , final Vector2 projectileSpeed) {
        super(owner, ammo, cooldown, fileName);
        this.projectileSpeed = projectileSpeed;
    }

    /**
     * Creates a new {@link ReturnableArrow} when the bow is fired.
     *
     * @param level    the level in which the arrow will be spawned
     * @param position the initial position of the arrow
     * @param speedX   the horizontal speed of the arrow
     * @param speedY   the vertical speed of the arrow
     * @param width    the width of the arrow
     * @param height   the height of the arrow
     * @param collider the collider that defines the arrow's shape
     * @return a new {@link ReturnableArrow} instance
     */
    @Override
    public Projectile createProjectile(final Level level, final Vector2 position) {
        return new ReturnableArrow(level, position, projectileSpeed, this.getCollider().get(), (Archer)this.getOwner());
    }

    public void attack() {

    }

    public void setOwner(final Archer owner) {
        this.owner = owner;
    }

}
