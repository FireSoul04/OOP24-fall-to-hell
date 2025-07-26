package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.Projectile;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.gameobjects.movable.projectile.Fireball;
import it.unibo.falltohell.util.Vector2;

public class Tome extends BaseRangedWeapon{

    private static long COOLDOWN = 500;
    private static int MAX_AMMO = 1;
    /**
     * Constructs a tome which can evoke fireballs with a certain cooldown time.
     *
     * @param lv           is the level of the weapon
     * @param position     of the weapon in the level
     * @param caster       is the caster user of the tome
     */
    public Tome(Level lv, Vector2 position, Caster caster) {
        super(lv, position, MAX_AMMO, COOLDOWN, "tome.png");
    }

    @Override
    protected Projectile createProjectile(Level level, Vector2 position, Vector2 speed, Collider collider) {
        return super.createProjectile(level, position, speed, collider);
    }
}
