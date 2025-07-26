package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Dagger extends BaseMeleeWeapon {

    private static final Dimensions SIZE = new Dimensions(15, 10);

    /**
     * Creates an short range dagger.
     * @param lv       is the level where there is the dagger
     * @param position is the position of the dagger in the level
     * @param owner    is the owner of the dagger
     */
    public Dagger(final Level lv, final Vector2 position, final Rogue owner) {
        super(lv, position, new BoxCollider(SIZE), owner, "dagger.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack() {
        super.attack();
    }
}
