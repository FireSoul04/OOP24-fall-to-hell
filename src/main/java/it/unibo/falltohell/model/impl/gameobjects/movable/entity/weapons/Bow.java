package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Archer;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.ReturnableArrow;
/**
 * A bow weapon used by the Archer character.
 *
 * It creates {@link ReturnableArrow} projectiles when fired, allowing arrows to be recalled later.
 */
public class Bow extends BaseRangedWeapon{

    private final Archer owner;
    /**
     * Constructs a new Bow with the specified ammo and cooldown.
     *
     * @param ammo the initial amount of ammo
     * @param cooldown the cooldown time between shots
     */
    public Bow(int ammo, double cooldown,Archer owner) {
        super(ammo, cooldown);
        this.owner = owner;
    }
     /**
     * Creates a new {@link ReturnableArrow} when the bow is fired.
     *
     * @param level the level in which the arrow will be spawned
     * @param position the initial position of the arrow
     * @param speedX the horizontal speed of the arrow
     * @param speedY the vertical speed of the arrow
     * @param width the width of the arrow
     * @param height the height of the arrow
     * @param collider the collider that defines the arrow's shape
     * @return a new {@link ReturnableArrow} instance
     */
    @Override
    public Projectile createProjectile(Level level, Vector2 position, double speedX, double speedY,Collider collider) {
        return new ReturnableArrow(level, position, speedX, speedY, collider, owner);
    }
    public void attack(){

    }

}
