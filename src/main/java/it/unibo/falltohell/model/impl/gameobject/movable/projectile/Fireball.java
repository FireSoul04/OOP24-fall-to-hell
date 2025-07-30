package it.unibo.falltohell.model.impl.gameobject.movable.projectile;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.Projectile;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.gameobject.weapon.Weapon;
import it.unibo.falltohell.model.impl.gameobject.Blast;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.Set;

/**
 * Class that represents a fireball evoked by a caster.
 * @author Martina Malagoli
 */
public class Fireball extends ProjectileImpl {

    private static final Dimensions DIMENSIONS = new Dimensions(5.0, 5.0);
    private static final double SPEED = 5;
    private final Caster caster;

    private final Set<Class<? extends GameObject>> ignoreCollisionsObjects = Set.of(
            Character.class,
            Weapon.class,
            Projectile.class,
            BaseEntrance.class,
            Blast.class
    );

    /**
     * Creates a fireball with a certain speed.
     *
     * @param direction of the fireball
     * @param caster   associated with this projectile
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The fireball should know the attack of the caster to scale damage"
    )
    public Fireball(final Vector2 direction, final Caster caster) {
        super(caster.getLevel(), caster.getPosition(), direction.multiply(SPEED), new BoxCollider(DIMENSIONS), "fireball.png");
        this.caster = caster;
    }

    /**
     * {@inheritDoc}
     * If the other object of the collision has not to be ignored
     * the fireball will hit.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        final boolean isOtherCollidable = ignoreCollisionsObjects.stream()
                .noneMatch(t -> t.isInstance(other));
        if (isOtherCollidable && other.isSolid() && !this.isHit()) {
            this.setHit(true);
            this.onProjectileHit(other);
        }
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
    }

}
