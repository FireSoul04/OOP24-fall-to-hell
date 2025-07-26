package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;


public class Staff extends BaseMeleeWeapon {

    private static final double DAMAGE_MULTIPLIER = 0.3;
    private static final Dimensions DIMENSIONS = new Dimensions(3, 10);
    private final Caster caster;

    /**
     * Creates a staff.
     *
     * @param lv       is the level where there is the melee weapon
     * @param position is the position of the melee weapon in the level
     * @param caster   associated to the staff
     */
    public Staff(final Level lv, final Vector2 position, final Caster caster) {
        super(lv, position, new BoxCollider(DIMENSIONS), "staff.png");
        this.caster = caster;
    }

    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof Enemy enemy) {
            enemy.setDamagedLife(this.caster.getStats().getAttack() * DAMAGE_MULTIPLIER);
        }
    }

}
