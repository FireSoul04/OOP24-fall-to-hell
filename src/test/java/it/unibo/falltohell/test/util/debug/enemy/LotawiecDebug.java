package it.unibo.falltohell.test.util.debug.enemy;

import java.util.Map;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.LongRangeEnemy;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;
import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.TimerType;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.TrackEnemyProjectile;
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
public class LotawiecDebug extends BaseEnemyDebug implements LongRangeEnemy {

    private static final int POINTS = 25;
    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final double FULL_LIFE = 50;
    private static final double DAMAGE = 4;
    private static final Vector2 VELOCITY = new Vector2(0.5, 0.5);
    private static final Dimensions DIMENSIONS_ARROW = new Dimensions(20, 20);
    private static final double DAMAGE_A = 9;
    private static final Vector2 VELOCITY_ARROW = new Vector2(1e-17, 1e-14);
    private static final int ATTACK_TIME = 4000;
    private static final Map<BuffNames, Double> BUFF = Map.of(
            BuffNames.ATTACK, 10.0,
            BuffNames.ATTACK_SPEED, 20.0,
            BuffNames.LIFE, 30.0,
            BuffNames.MANA, 40.0,
            BuffNames.SPEED, 50.0);
    private final LongRangeEnemyStatistics stats;
    private int direction = 1;
    private int numberAttack;

    /**
     * Constructs a new Lotawiec enemy with specified initial position, target
     * character,
     * and a timer manager for attack scheduling.
     * <p>
     * Initializes its statistics, dimensions, damage, velocity, and attack timers.
     *
     * @param level       the level this enemy belongs to
     * @param initialCord the initial position of the enemy
     * @param manager     the timer manager handling enemy-specific timers
     * @param ingage      the {@link SafeZoneManager} used to handle if the player
     *                    enter a safe zone
     *
     * @see LongRangeEnemyStatistics
     * @see CustomTimerImpl
     */
    @SuppressFBWarnings(value = "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR",
    justification = "attack() is safe in constructor; object fully initialized")
    public LotawiecDebug(final Level level, final Vector2 initialCord, final EnemyTimerManager manager,
            final SafeZoneManager ingage) {
        super(level,
                new StatisticFactoryImpl().createLongRangeEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                        initialCord, POINTS, new StatisticFactoryImpl().createOptional().withBuff(BUFF),
                        DAMAGE_A, VELOCITY_ARROW, DIMENSIONS_ARROW, ATTACK_TIME),
                manager, ingage, "lotawiec.png");

        stats = (LongRangeEnemyStatistics) super.getStats();

        super.getEnemyTimerManager().createAttackTimer(this, Optional.of(this::attack));
        ingage.addEnemy(this, "lotawiec.png");
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
                this.direction *= -1;
            }
        } else if (other instanceof Character) {
            super.getCharacter().setDamagedLife(DAMAGE);
            super.getEnemyTimerManager().restartEnemyTimer(this, TimerType.NO_AGGRO);
        }
        this.setFacingRight(this.direction > 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attack() {
        if (super.getCharacter().getPosition().distance(super.getPosition()) < this.stats.getSenseDistance()) {
            new TrackEnemyProjectile(super.getLevel(),
                    super.getPosition(),
                    this.stats.getProjectileSpeed(),
                    new BoxCollider(Vector2.zero(), this.stats.getProjectileDimensions()), DAMAGE_A,
                    this.stats.getSenseDistance());
            this.numberAttack++;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void patrol(final Vector2 current, final Vector2 speed) {
        final double speedX = speed.x();
        final Vector2 step = new Vector2(speedX * direction, 0);
        final Vector2 target = current.add(step);

        this.setPosition(target);
        super.setFacingRight(direction > 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chase(final Vector2 target, final Vector2 current, final Vector2 speed) {
        final Vector2 diff = target.subtract(current).normalize();
        final double stoppingDistance = 4 * TILE_SIZE;
        final double verticalOffset = 2 * TILE_SIZE;
        final Vector2 moveStep = diff.multiply(speed);
        Vector2 tryMove = current.add(moveStep);
        final var manager = super.getLevel().getJumpCollisionManager();
        if (target.distance(current) <= stoppingDistance) {
            final Vector2 aboveTarget = new Vector2(target.x(), target.y() - verticalOffset);
            if (!manager.isBlocked(aboveTarget, this.stats.getDimensions().width(),
                    this.stats.getDimensions().height())) {

                final double desiredY = aboveTarget.y();
                final double currentY = current.y();
                double nextY = currentY;

                if (currentY > desiredY) {
                    nextY = Math.max(desiredY, currentY - speed.y());
                } else if (currentY < desiredY) {
                    nextY = Math.min(desiredY, currentY + speed.y());
                }

                this.setPosition(new Vector2(current.x(), nextY));
            }
            return;
        }

        if (manager.isBlocked(tryMove, this.stats.getDimensions().width(), this.stats.getDimensions().height())) {
            final Vector2 up = current.add(new Vector2(0, -speed.y()));
            final Vector2 down = current.add(new Vector2(0, speed.y()));

            if (!manager.isBlocked(up, this.stats.getDimensions().width(), this.stats.getDimensions().height())) {
                tryMove = up;
            } else if (!manager.isBlocked(down, this.stats.getDimensions().width(),
                    this.stats.getDimensions().height())) {
                tryMove = down;
            } else {
                return;
            }
        }

        this.setPosition(tryMove);
        super.setFacingRight(moveStep.x() > 0);
    }

    /**
     * Returns the current movement direction of the enemy.
     *
     * @return an integer representing the enemy's direction
     */
    public int getDirection() {
        return direction;
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
