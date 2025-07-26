package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Dagger extends BaseMeleeWeapon {

    private static final Dimensions SIZE = new Dimensions(15, 10);

    /**
     * Creates an abstract close ranged weapon.
     *
     * @param lv       is the level where there is the melee weapon
     * @param position is the position of the melee weapon in the level
     */
    public Dagger(final Level lv, final Vector2 position) {
        super(lv, position, new BoxCollider(SIZE), "dagger.png");
    }

    @Override
    public void attack() {
        super.attack();
    }
}
