package it.unibo.falltohell.model.impl.gameobject.movable.projectile;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class Fireball extends ProjectileImpl{

    private static final Dimensions DIMENSIONS = new Dimensions(5.0,5.0);
    private static final double SPEED = 10;
    private final Caster caster;

    /**
     * Creates a fireball with a certain speed.
     *
     * @param level    the game level this projectile belongs to
     * @param position the initial position of the projectile
     * @param direction of the fireball
     * @param caster   associated with this projectile
     */
    public Fireball(final Level level, final Vector2 position, Vector2 direction, final Caster caster) {
        super(level, position, direction.multiply(SPEED), new BoxCollider(DIMENSIONS), "fireball.png");
        this.caster = caster;
    }

    @Override
    public void onCollision(final GameObject other, Vector2 direction) {
        super.onCollision(other, direction);
    }

    @Override
    protected void onProjectileHit(final GameObject other) {
        if (other instanceof Enemy enemy) {
            enemy.setDamagedLife(this.caster.getStats().getAttack());
        }
        if (other.isSolid() && !(other instanceof Character)) {
            this.getLevel().removeGameObject(this);
        }
    }

}
