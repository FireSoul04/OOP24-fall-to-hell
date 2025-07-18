package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class WarScythe extends MeleeWeapon {

    public WarScythe() {
        super(new BoxCollider(Vector2.zero(), new Dimensions(10,10)));
    }

}
