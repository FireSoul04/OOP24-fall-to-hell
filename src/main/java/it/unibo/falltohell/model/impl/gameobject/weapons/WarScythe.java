package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
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
 * @see BaseMeleeWeapon
 * @see BoxCollider
 */
public class WarScythe extends BaseMeleeWeapon {

    private static final Dimensions SIZE = new Dimensions(15, 30);
    private static final Vector2 OFFSET = new Vector2(16, 5);
    private static final double MULTIPLIER = 1.5;

    /**
     * <p>
     * Constructs a {@code WarScythe} with a default collider.
     * </p>
     *
     * @param owner character that owns the weapon
     * @param cooldownTime the time in nanoseconds between hits
     */
    public WarScythe(final Character owner, final long cooldownTime) {
        super(owner, new BoxCollider(Vector2.zero(), SIZE), MULTIPLIER, cooldownTime, "warscythe.png", OFFSET);
    }
}
