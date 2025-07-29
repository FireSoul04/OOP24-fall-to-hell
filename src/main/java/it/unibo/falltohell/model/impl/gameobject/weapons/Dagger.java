package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class representing a dagger.
 * It's a short range, fast melee weapon that doesn't do much damage.
 *
 * @author Davide Mancini
 */
public class Dagger extends BaseMeleeWeapon {

    private static final long COOLDOWN_TIME = 800;
    private static final double DAMAGE_MULTIPLIER = 0.8;
    private static final Dimensions SIZE = new Dimensions(20, 12);
    private static final Vector2 OFFSET = new Vector2(10, 0);

    /**
     * Creates an short range dagger.
     * @param owner    is the owner of the dagger
     */
    public Dagger(final Rogue owner) {
        super(owner, new BoxCollider(SIZE), DAMAGE_MULTIPLIER, COOLDOWN_TIME, "dagger.png", OFFSET);
    }
}
