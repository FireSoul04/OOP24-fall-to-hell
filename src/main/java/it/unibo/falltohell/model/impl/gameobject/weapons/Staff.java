package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a caster's staff.
 * @author Martina Malagoli
 */
public class Staff extends BaseMeleeWeapon {

    private static final double DAMAGE_MULTIPLIER = 0.7;
    private static final Dimensions DIMENSIONS = new Dimensions(24.0, 20.0);
    private static final Vector2 OFFSET = new Vector2(8.0, 8.0);
    private static final long COOLDOWN = 1000;

    /**
     * Creates a staff.
     *
     * @param caster   associated to the staff
     */
    public Staff(final Caster caster) {
        super(caster, new BoxCollider(DIMENSIONS), DAMAGE_MULTIPLIER, COOLDOWN, "staff.png", OFFSET);
    }
}
