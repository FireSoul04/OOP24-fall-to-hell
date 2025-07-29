package it.unibo.falltohell.model.impl.gameobject.movable.projectile;

import java.util.UUID;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * A projectile that dynamically tracks a {@link Character} by applying
 * directional
 * acceleration toward it until it reaches a specified distance threshold.
 * <p>
 * If the target is beyond this distance, the projectile accelerates toward the
 * target,
 * gradually adjusting its velocity. Once the projectile enters the distance
 * range,
 * it reverts to the base projectile behavior defined in
 * {@link BaseEnemyProjectile}.
 * <p>
 * The distance threshold decreases over time using a timer, which creates
 * increasing
 * pressure on the player to evade.
 *
 * @author Sara Visani
 * @see BaseEnemyProjectile
 * @see Character
 */
public class TrackEnemyProjectile extends BaseEnemyProjectile {

    private static final int DISTANCE_BUFF = (int) (3 * TILE_SIZE);
    private static final int DISTANCE_DEBUFF = (int) (5 * TILE_SIZE);
    private static final long DISTANCE_TIME = 500;
    private static final double DISTANCE_MIN = TILE_SIZE;
    private static final double MAX_ACCEL = 1.5;
    private static final double MAX_SPEED = 2;
    private double distance;
    private final String name = "SubDistance" + UUID.randomUUID();

    /**
     * Constructs a homing projectile that targets a specific character and
     * dynamically adjusts
     * its trajectory toward the target.
     *
     * @param level    the level this projectile belongs to
     * @param position the initial position of the projectile
     * @param speed    the initial velocity
     * @param collider the collider used for hit detection
     * @param damage   the damage this projectile deals on impact
     * @param distance the initial tracking range before switching to default
     *                 behavior
     *
     * @see Level
     * @see Vector2
     * @see Collider
     */
    public TrackEnemyProjectile(final Level level, final Vector2 position, final Vector2 speed,
            final Collider collider, final double damage,
            final double distance) {
        super(level, position, speed, collider, damage, "track_enemy_projectile.png");
        this.distance = (distance + DISTANCE_BUFF) * TILE_SIZE;

        super.getLevel().getTimerManager().addTimer(this.name, new CustomTimerImpl(DISTANCE_TIME, () -> {
            if (!super.getLevel().getTimerManager().searchTimer(this.name)) {
                return;
            }

            if (this.distance - DISTANCE_DEBUFF < DISTANCE_MIN) {
                this.distance = DISTANCE_MIN;
                super.getLevel().getTimerManager().removeTimer(this.name);
            } else {
                this.distance -= DISTANCE_DEBUFF;
                super.getLevel().getTimerManager().restartTimer(this.name);
            }
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onUpdate(final double deltaTime) {
        final var characterPos = super.getLevel().getGameData().getCurrentCharacter().getPosition();
        if (characterPos.distance(super.getPosition()) <= this.distance) {
            final Vector2 currentPos = super.getPosition();
            final Vector2 toTarget = characterPos.subtract(currentPos).normalize();
            final Vector2 acceleration = toTarget.multiply(MAX_ACCEL);
            Vector2 currentVelocity = super.getSpeed().add(acceleration.multiply(deltaTime));
            if (currentVelocity.magnitude() > MAX_SPEED) {
                currentVelocity = currentVelocity.normalize().multiply(MAX_SPEED);
            }
            super.setSpeed(currentVelocity);
            super.setPosition(currentPos.add(currentVelocity.multiply(deltaTime)));
        } else {
            super.onUpdate(deltaTime);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onProjectileHit(final GameObject other) {
        if (super.getLevel().getTimerManager().searchTimer(name)) {
            super.getLevel().getTimerManager().removeTimer(name);
        }
        super.onProjectileHit(other);
    }
}
