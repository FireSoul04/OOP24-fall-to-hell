package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;

public class Dagger extends BaseMeleeWeapon {

    private static final long COOLDOWN_TIME = 400;
    private static final double DAMAGE_MULTIPLIER = 0.8;
    private static final Dimensions SIZE = new Dimensions(15, 10);

    /**
     * Creates an short range dagger.
     * @param owner    is the owner of the dagger
     */
    public Dagger(final Rogue owner) {
        super(owner, new BoxCollider(SIZE), DAMAGE_MULTIPLIER, COOLDOWN_TIME, "dagger.png");
    }
}
