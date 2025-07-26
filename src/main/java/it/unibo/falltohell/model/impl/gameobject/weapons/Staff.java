package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;


public class Staff extends BaseMeleeWeapon {

    private static final double DAMAGE_MULTIPLIER = 0.3;
    private static final Dimensions DIMENSIONS = new Dimensions(3, 10);
    private static final long COOLDOWN = 600;
    private final Caster caster;

    /**
     * Creates a staff.
     *
     * @param caster   associated to the staff
     */
    public Staff(final Caster caster) {
        super(caster, new BoxCollider(DIMENSIONS), DAMAGE_MULTIPLIER, COOLDOWN ,"staff.png");
        this.caster = caster;
    }

}
