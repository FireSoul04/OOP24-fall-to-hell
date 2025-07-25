package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.Drawable.Priority;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * <p>
 * Represents a {@code WarScythe}, a melee weapon with a predefined hitbox.
 * </p>
 *
 * <p>
 * Features:
 * </p>
 * <ul>
 * <li>Configured with a {@link BoxCollider} of fixed size (10x10)</li>
 * <li>Used by characters for close-range attacks</li>
 * </ul>
 *
 * @author Sara Visani
 * @see MeleeWeapon
 * @see BoxCollider
 */
public class WarScythe extends MeleeWeapon {

    /**
     * <p>
     * Constructs a {@code WarScythe} with a default collider.
     * </p>
     *
     * <p>
     * The collider has origin at (0, 0) and dimensions (10, 10).
     * </p>
     */
    public WarScythe(final Level lv, final Vector2 position) {
        super(lv, position, new BoxCollider(Vector2.zero(), new Dimensions(10, 10)), "warscythe.png");
    }

}
