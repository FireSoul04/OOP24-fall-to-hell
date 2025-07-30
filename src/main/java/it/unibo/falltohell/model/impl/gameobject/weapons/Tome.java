package it.unibo.falltohell.model.impl.gameobject.weapons;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.Fireball;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a caster's tome used to evoke fireballs.
 * @author Martina Malagoli
 */
public class Tome extends BaseRangedWeapon {

    private static final long COOLDOWN = 500;
    private static final int MAX_AMMO = 1;
    private static final Vector2 OFFSET = new Vector2(10.0, 5.0);

    private final Caster caster;

    /**
     * Constructs a tome which can evoke fireballs with a certain cooldown time.
     *
     * @param caster is the caster user of the tome
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The tome has to remove mana to the caster when used"
    )
    public Tome(final Caster caster) {
        super(caster, MAX_AMMO, COOLDOWN, "tome.png", OFFSET);
        this.caster = caster;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Projectile createProjectile() {
        final Vector2 direction = this.caster.isFacingRight() ? Vector2.right() : Vector2.left();
        return new Fireball(direction, this.caster);
    }

    /**
     * {@inheritDoc}
     * If there is no fireball to shoot it will be reloaded
     * and mana will be subtracted.
     */
    @Override
    protected void onAttack() {
        super.onAttack();
        if (!this.canShoot()) {
            this.reload();
        }
        this.caster.subMana(this.caster.getAmountManaNormalAttack());
    }
}
