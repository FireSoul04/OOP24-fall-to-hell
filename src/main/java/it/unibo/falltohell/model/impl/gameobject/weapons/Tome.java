package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.Fireball;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a caster's tome used to evoke fireballs.
 * @author Martina Malagoli
 */
public class Tome extends BaseRangedWeapon{

    private static final long COOLDOWN = 500;
    private static final int MAX_AMMO = 1;

    private final Caster caster;
    /**
     * Constructs a tome which can evoke fireballs with a certain cooldown time.
     *
     * @param caster       is the caster user of the tome
     */
    public Tome(Caster caster) {
        super(caster, MAX_AMMO, COOLDOWN, "tome.png");
        this.caster = caster;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Projectile createProjectile(Level level, Vector2 position) {
        final Vector2 direction = this.caster.isFacingRight() ? Vector2.right() : Vector2.left();
        return new Fireball(direction, this.caster);
    }
}
