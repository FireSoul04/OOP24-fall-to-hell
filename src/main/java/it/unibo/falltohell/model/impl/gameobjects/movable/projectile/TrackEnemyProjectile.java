package it.unibo.falltohell.model.impl.gameobjects.movable.projectile;

import java.util.Optional;
import java.util.UUID;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
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

    private static final int DISTANCE_BUFF = 20;
    private static final int DISTANCE_DEBUFF = 10;
    private static final long DISTANCE_TIME = 1000;
    private static final double DISTANCE_MIN = 30;
    private static final double MAX_ACCEL = 800.0;
    private static final double MAX_SPEED = 600.0;
    private final Character character;
    private double distance;
    private final String name = "SubDistance" + UUID.randomUUID();

    /**
     * Constructs a homing projectile that targets a specific character and
     * dynamically adjusts
     * its trajectory toward the target.
     *
     * @param level     the level this projectile belongs to
     * @param position  the initial position of the projectile
     * @param speedX    the initial horizontal velocity
     * @param speedY    the initial vertical velocity
     * @param collider  the collider used for hit detection
     * @param damage    the damage this projectile deals on impact
     * @param character the target character to track
     * @param distance  the initial tracking range before switching to default
     *                  behavior
     *
     * @see it.unibo.falltohell.model.api.Level
     * @see Vector2
     * @see Collider
     * @see Character
     */
    public TrackEnemyProjectile(final Level level, final Vector2 position, final double speedX, final double speedY,
            final Collider collider, final double damage, final Character character, final double distance, Optional<Drawable> drawable) {
        super(level, position, speedX, speedY, collider, damage, drawable);
        this.character = character;
        this.distance = distance + DISTANCE_BUFF;

        super.getLevel().getTimerManager().addTimer(this.name, new CustomTimerImpl(DISTANCE_TIME, () -> {
            if (!super.getLevel().getTimerManager().searchTimer(name)) {
                return;
            }

            if (this.distance - DISTANCE_DEBUFF < DISTANCE_MIN) {
                this.distance = DISTANCE_MIN;
                super.getLevel().getTimerManager().removeTimer(name);
            } else {
                this.distance -= DISTANCE_DEBUFF;
                super.getLevel().getTimerManager().restartTimer(name);
            }
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onUpdate(final double deltaTime) {
        final var characterPos = this.character.getPosition();
        if (characterPos.distance(super.getPosition()) <= this.distance) {
            final Vector2 currentPos = super.getPosition();
            final Vector2 toTarget = characterPos.subtract(currentPos).normalize();
            final Vector2 acceleration = toTarget.multiply(MAX_ACCEL);
            Vector2 currentVelocity = new Vector2(super.getSpeedX(), super.getSpeedY());
            currentVelocity = currentVelocity.add(acceleration.multiply(deltaTime));

            if (currentVelocity.magnitude() > MAX_SPEED) {
                currentVelocity = currentVelocity.normalize().multiply(MAX_SPEED);
            }
            super.setSpeedX(currentVelocity.x());
            super.setSpeedY(currentVelocity.y());

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
