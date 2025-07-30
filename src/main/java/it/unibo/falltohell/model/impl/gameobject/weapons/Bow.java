package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Archer;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.ReturnableArrow;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

/**
 * A bow weapon used by the Archer character.
 *
 * It creates {@link ReturnableArrow} projectiles when fired, allowing arrows to be recalled later.
 * @author Lorenzo Casadei
 */
public class Bow extends BaseRangedWeapon {
    private static final Vector2 OFFSET = new Vector2(4.0, 0.0);
    private final Vector2 projectileSpeed;

    /**
     * Constructs a new Bow with the specified ammo and cooldown.
     *
     * @param owner the character that wield this bow.
     * @param ammo the initial amount of ammo.
     * @param cooldown the cooldown time between shots.
     * @param fileName the name of this bow.
     * @param projectileSpeed the speed of the arrow used by this bow.
     */
    public Bow(final Character owner, final int ammo, final long cooldown, final String fileName, final Vector2 projectileSpeed) {
        super(owner, ammo, cooldown, fileName, OFFSET);
        this.projectileSpeed = projectileSpeed;
    }

    /**
     * Creates a new {@link ReturnableArrow} when the bow is fired.
     * @return a new {@link ReturnableArrow} instance
     */
    @Override
    public Projectile createProjectile() {
        final Vector2 direction = this.getOwner().isFacingRight() ? Vector2.right() : Vector2.left();
        final Vector2 dirSpeed = projectileSpeed.multiply(direction.x());
        return new ReturnableArrow(this.getOwner().getLevel(), this.getOwner().getPosition(), 
        dirSpeed, new BoxCollider(new Dimensions(3.0, 3.0)), (Archer) this.getOwner());
    }
}
