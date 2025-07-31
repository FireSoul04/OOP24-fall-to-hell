package it.unibo.falltohell.test.util.debug.enemy;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.LongRangeEnemy;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;
import it.unibo.falltohell.model.api.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.TimerType;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.BaseEnemyProjectile;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * A debug implementation of an enemy for testing purposes.
 * <p>
 * Provides visual representation, movement patterns (patrol and chase),
 * collision handling, and interaction with the {@link EnemyTimerManager}
 * and {@link SafeZoneManager}.
 * <p>
 * Used in test environments to simulate enemy behavior without affecting
 * the core game logic.
 *
 * @author Sara Visani
 */
public class TenguDebug extends BaseEnemyDebug implements LongRangeEnemy {

    private static final int POINTS = 10;
    private static final double CHAR_DISTANCE = 15 * TILE_SIZE;
    private static final int ATTACK_TIME = 1000;
    private static final double REGEN_STAT = 0.1;
    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final Dimensions DIMENSIONS_ARROW = new Dimensions(15, 15);
    private static final double FULL_LIFE = 20;
    private static final double DAMAGE = 1; // Physical damage
    private static final double DAMAGE_A = 3; // Damage of projectile
    private static final Vector2 VELOCITY = new Vector2(0.1, 0.1);
    private static final Vector2 VELOCITY_ARROW = new Vector2(0, 0.001);
    private static final double DISTANCE = 10 * TILE_SIZE;

    private final RestrictedLongRangeEnemyStatistics stats;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();
    private int numberAttack;

    /**
     * Constructs a new Tengu enemy with configured statistics, timers, and target
     * character.
     * <p>
     * The enemy's attack timer is started immediately and configured to repeat
     * attacks
     * based on {@link RestrictedLongRangeEnemyStatistics#getTimeAttack()}.
     * </p>
     *
     * @param level       the {@link Level} this enemy belongs to
     * @param initialCord the initial spawn position of this enemy
     * @param manager     the {@link EnemyTimerManager} used to handle enemy timers
     * @param ingage      the {@link SafeZoneManager} used to handle if the player
     *                    enter a safe zone
     *
     * @see RestrictedLongRangeEnemyStatistics
     * @see CustomTimerImpl
     */
    @SuppressFBWarnings(value = "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR",
    justification = "attack() is safe in constructor; object fully initialized")
    public TenguDebug(final Level level, final Vector2 initialCord,
            final EnemyTimerManager manager, final SafeZoneManager ingage) {
        super(level,
                new StatisticFactoryImpl().createLongRangeRestrictedStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                        initialCord, POINTS, new StatisticFactoryImpl()
                                .createOptional().withRegen(REGEN_STAT).withSenseDistance(CHAR_DISTANCE),
                        DAMAGE_A, VELOCITY_ARROW, DIMENSIONS_ARROW, DISTANCE, ATTACK_TIME),
                manager, ingage, "tengu.png");

        stats = (RestrictedLongRangeEnemyStatistics) super.getStats();

        super.getEnemyTimerManager().createAttackTimer(this, Optional.of(this::attack));
        ingage.addEnemy(this, "tengu.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        super.move(deltaTime);
        super.getDrawable().ifPresent(d -> d.mirror(!super.isFacingRight()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof BaseCollidableBlock || other instanceof BaseEntrance) {
            if (direction.x() != 0) {
                this.collided = Optional.of(other.getPosition());
            }
        } else if (other instanceof Character) {
            super.getCharacter().setDamagedLife(DAMAGE);
            super.getEnemyTimerManager().restartEnemyTimer(this, TimerType.NO_AGGRO);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack() {
        if (super.getCharacter().getPosition().distance(super.getPosition()) < this.stats.getSenseDistance()) {
            new BaseEnemyProjectile(super.getLevel(),
                    super.getPosition().add(Vector2.down().multiply(this.stats.getDimensions().height() / 2)),
                    this.stats.getProjectileSpeed(),
                    new BoxCollider(Vector2.zero(), this.stats.getProjectileDimensions()), DAMAGE_A,
                    "base_enemy_projectile.png");
            this.numberAttack++;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void patrol(final Vector2 currentPos, final Vector2 speed) {
        final double speedX = speed.x();
        final double y = currentPos.y();
        final Vector2 target = currentPos.add(new Vector2(speedX * this.direction, 0));
        final double distanceFromInitial = this.stats.getInitialPos().distance(target);

        if (distanceFromInitial <= this.stats.getDistance()) {
            if (isBlocked(target)) {
                this.setPositionToCollision();
                this.direction *= -1;
            } else {
                super.setPosition(target);
            }
        } else {
            final double newX = this.stats.getInitialPos().x() + this.stats.getDistance() * this.direction;
            super.setPosition(new Vector2(newX, y));
            this.direction *= -1;
        }

        super.setFacingRight(this.direction > 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chase(final Vector2 charaPos, final Vector2 currentPos, final Vector2 speed) {
        final double speedX = speed.x();
        final double y = currentPos.y();
        super.setFacingRight(charaPos.x() - currentPos.x() > 0);

        final double deltaToChara = charaPos.x() - currentPos.x();
        final boolean withinAggroRange = charaPos.x() <= this.stats.getInitialPos().x() + this.stats.getDistance()
                && charaPos.x() >= this.stats.getInitialPos().x() - this.stats.getDistance();

        if (withinAggroRange) {
            if (Math.abs(deltaToChara) > speedX) {
                final double step = Math.signum(deltaToChara) * speedX;
                final Vector2 target = currentPos.add(new Vector2(step, 0));
                if (isBlocked(target)) {
                    this.setPositionToCollision();

                } else {
                    super.setPosition(target);

                }
            } else {
                if (!isBlocked(charaPos)) {
                    super.setPosition(new Vector2(charaPos.x(), super.getPosition().y()));

                } else {
                    this.setPositionToCollision();

                }
            }
        } else {
            // Player out of aggro range: move toward edge of patrol range
            final double dir = Math.signum(deltaToChara);
            final double limitX = this.stats.getInitialPos().x() + this.stats.getDistance() * dir;
            final Vector2 target = currentPos.add(new Vector2(speedX * dir, 0));
            final Vector2 patrolLimit = new Vector2(limitX, y);

            if (isBlocked(target)) {
                this.setPositionToCollision();
            } else {
                if (this.stats.getInitialPos().distance(target) <= this.stats.getDistance()) {
                    super.setPosition(target);
                } else {
                    super.setPosition(patrolLimit);
                    this.direction *= -1;
                }
            }
        }
    }

    /**
     * Checks if a given position would result in a collision with a known barrier.
     *
     * @param target the position to check for collision
     * @return true if the enemy would collide at the given position, false
     *         otherwise
     */
    public boolean isBlocked(final Vector2 target) {
        return this.collided.isPresent()
                && ((this.direction > 0
                        && target.x() > this.collided.get().x())
                        || (this.direction < 0 && target.x() < this.collided.get().x()));
    }

    /**
     * Sets the enemy's position to the last known collision point, if present.
     */
    public void setPositionToCollision() {
        this.collided.ifPresent(super::setPosition);
    }

    /**
     * Returns the current movement direction of the enemy.
     *
     * @return an integer representing the enemy's direction
     */
    public int getDirection() {
        return this.direction;
    }

    /**
     * Returns the position where the enemy last collided, if any.
     *
     * @return an {@link Optional} containing the collision position as a
     *         {@link Vector2},
     *         or an empty Optional if no collision has occurred
     */
    public Optional<Vector2> getCollided() {
        return this.collided;
    }

    /**
     * Returns the total number of attacks performed by this enemy.
     *
     * <p>This counter is typically incremented each time the enemy executes
     * an attack action (e.g., during its attack timer cycle).
     *
     * @return the number of attacks executed so far
     */
    public int getNumberAttack() {
        return this.numberAttack;
    }
}
