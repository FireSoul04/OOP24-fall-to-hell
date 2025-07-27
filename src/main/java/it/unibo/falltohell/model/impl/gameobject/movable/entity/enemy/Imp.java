package it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.manager.SafeZoneManager;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Represents an enemy type "Imp" that moves horizontally within a restricted
 * range,
 * can detect and attack a target {@link Character}, and reacts to collisions
 * with blocks.
 * <p>
 * The Imp moves back and forth between defined distance boundaries or chases
 * the target
 * if it is within sensing distance.
 * <p>
 * Collision with blocks stops movement in the vertical direction and reverses
 * movement
 * direction when hitting horizontal boundaries.
 * <p>
 * Movement logic ensures the Imp does not pass through obstacles or move beyond
 * its allowed range.
 *
 * @author Sara Visani
 * @see BaseEnemy
 * @see Character
 * @see RestrictedBaseEnemyStatistics
 * @see Vector2
 */
public class Imp extends BaseEnemy {

    private static final double CHAR_DISTANCE = 20;
    private static final double REGEN_STAT = 0.1;
    private static final Dimensions DIMENSIONS = new Dimensions(10, 10);
    private static final double FULL_LIFE = 10;
    private static final double DAMAGE = 10;
    private static final Vector2 VELOCITY = new Vector2(1, 1);
    private static final double DISTANCE = 10;

    private final RestrictedBaseEnemyStatistics stats;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();

    /**
     * Constructs a new Imp enemy with default stats, initial position, and target
     * character.
     * <p>
     * The Imp will use {@link RestrictedBaseEnemyStatistics} for movement limits,
     * sensing,
     * and regeneration.
     *
     * @param level       the game level the Imp belongs to
     * @param initialCord the initial spawn position of the Imp
     * @param character   the target {@link Character} to track and attack
     * @param manager     the {@link EnemyTimerManager} responsible for managing
     *                    enemy timers
     * @param ingage      the {@link SafeZoneManager} used to handle if the player
     *                    enter a safe zone
     */
    public Imp(final Level level, final Vector2 initialCord, final Character character,
            final EnemyTimerManager manager, final SafeZoneManager ingage) {
        super(level,
                new StatisticFactoryImpl().createGroundRestrictedEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                        initialCord, character, 10, new StatisticFactoryImpl()
                                .createOptional().withRegen(REGEN_STAT).withSenseDistance(CHAR_DISTANCE),
                        DISTANCE),
                manager, ingage, "imp.png");

        this.stats = (RestrictedBaseEnemyStatistics) super.getStats();
        ingage.addEnemy(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        super.onCollision(other, direction);
        if (other instanceof BaseCollidableBlock || other instanceof BaseEntrance) {
            if (direction == Vector2.left() || direction == Vector2.right()) {
                this.collided = Optional.of(super.getPosition());
            }
        } else if (other instanceof Character) {
            this.attack();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void attack() {
        this.stats.getCharacter().setDamagedLife(this.stats.getAttack());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void patrol(final Vector2 currentPos, final Vector2 speed) {
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
    protected void chase(final Vector2 charaPos, final Vector2 currentPos, final Vector2 speed) {
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
                    super.setPosition(charaPos);
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
    private boolean isBlocked(final Vector2 target) {
        return this.collided.isPresent()
                && ((this.direction > 0
                        && target.x() > this.collided.get().x())
                        || (this.direction < 0 && target.x() < this.collided.get().x()));
    }

    /**
     * Sets the enemy's position to the last known collision point, if present.
     */
    private void setPositionToCollision() {
        this.collided.ifPresent(super::setPosition);
    }
}
