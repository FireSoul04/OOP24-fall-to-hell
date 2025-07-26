package it.unibo.falltohell.model.impl.gameobject.weapons;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
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

    private static final double MULTIPLIER = 2.5;

    /**
     * <p>
     * Constructs a {@code WarScythe} with a default collider.
     * </p>
     *
     * @param lv level in whitch the weapon is
     * @param owner character that owns the weapon
     */
    public WarScythe(final Character owner, final long cooldownTime) {
        super(owner, new BoxCollider(Vector2.zero(), new Dimensions(10, 10)), MULTIPLIER, cooldownTime, "warscythe.png");
    }

    @Override
    public void onCollision(final GameObject other, final Vector2 direction){
        if(other instanceof Enemy){
            final var damage = ((CharacterStatistics)super.getOwner().getStats()).getAttack();
            ((Enemy)other).setDamagedLife(damage);
        }
    }

}
