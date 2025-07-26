package it.unibo.falltohell.model.impl.gameobject.movable.projectile;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a fireball evoked by a caster.
 * @author Martina Malagoli
 */
public class Fireball extends ProjectileImpl{

    private static final Dimensions DIMENSIONS = new Dimensions(5.0,5.0);
    private static final double SPEED = 10;
    private final Caster caster;

    /**
     * Creates a fireball with a certain speed.
     *
     * @param direction of the fireball
     * @param caster   associated with this projectile
     */
    public Fireball(Vector2 direction, final Caster caster) {
        super(caster.getLevel(), caster.getPosition(), direction.multiply(SPEED), new BoxCollider(DIMENSIONS), "fireball.png");
        this.caster = caster;
    }

    /**
     * {@inheritDoc}
     * If an enemy is hit it will be damaged.
     * When the fireball hits an object which is not the character
     * or an entrance will disappear.
     */
    @Override
    protected void onProjectileHit(final GameObject other) {
        if (other instanceof Enemy enemy) {
            enemy.setDamagedLife(this.caster.getStats().getAttack());
        }
        if (!(other instanceof BaseEntrance) && !(other instanceof Character)) {
            this.getLevel().removeGameObject(this);
        }
    }

}
