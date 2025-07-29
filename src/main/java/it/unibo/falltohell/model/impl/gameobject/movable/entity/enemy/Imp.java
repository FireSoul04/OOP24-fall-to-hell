package it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;

import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;

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

    private static final int POINTS = 15;
    private static final double CHAR_DISTANCE = 15 * TILE_SIZE;
    private static final double REGEN_STAT = 0.1;
    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final double FULL_LIFE = 30;
    private static final double DAMAGE = 3;
    private static final Vector2 VELOCITY = new Vector2(0.1, 0.1);
    private static final double DISTANCE = 7 * TILE_SIZE;

    private final RestrictedBaseEnemyStatistics stats;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();
    private boolean check;

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
     * @param manager     the {@link EnemyTimerManager} responsible for managing
     *                    enemy timers
     * @param ingage      the {@link SafeZoneManager} used to handle if the player
     *                    enter a safe zone
     */
    public Imp(final Level level, final Vector2 initialCord,
            final EnemyTimerManager manager, final SafeZoneManager ingage) {
        super(level,
                new StatisticFactoryImpl().createGroundRestrictedEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                        initialCord, POINTS, new StatisticFactoryImpl()
                                .createOptional().withRegen(REGEN_STAT).withSenseDistance(CHAR_DISTANCE),
                        DISTANCE),
                manager, ingage, "imp.png");

        this.stats = (RestrictedBaseEnemyStatistics) super.getStats();
        ingage.addEnemy(this, "imp.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        super.onCollision(other, direction);
        if (other instanceof BaseCollidableBlock || other instanceof BaseEntrance) {
            if (direction.equals(Vector2.left()) || direction.equals(Vector2.right())) {
                this.collided = Optional.of(other.getPosition());
                if (this.isGlitched()) {
                    this.direction *= -1;
                }
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
        super.attack();
        super.getCharacter().setDamagedLife(this.stats.getAttack());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void patrol(final Vector2 currentPos, final Vector2 speed) {
        final double speedX = speed.x();
        final double y = currentPos.y();
        final Vector2 target = currentPos.add(new Vector2(speedX * this.direction, 0));

        if (target.distance(this.stats.getInitialPos()) < DISTANCE) {
            this.check = false;
            if (isBlocked(target)) {
                this.setPositionToCollision();
                this.direction *= -1;
            } else {
                super.setPosition(target);
            }
        } else {
            if (!this.isGlitched()) {
                final double newX = this.stats.getInitialPos().x() + DISTANCE * this.direction;
                super.setPosition(new Vector2(newX, y));
                this.direction *= -1;
                this.check = true;
            } else {
                super.setPosition(target);
            }
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
        final boolean withinAggroRange = charaPos.x() <= this.stats.getInitialPos().x() + DISTANCE
                && charaPos.x() >= this.stats.getInitialPos().x() - DISTANCE;
        if (withinAggroRange) {
            if (charaPos.distance(currentPos) > speedX) {
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
            final double limitX = this.stats.getInitialPos().x() + DISTANCE * dir;
            final Vector2 target = currentPos.add(new Vector2(speedX * dir, 0));
            final Vector2 patrolLimit = new Vector2(limitX, y);

            if (isBlocked(target)) {
                this.setPositionToCollision();
            } else {
                if (this.stats.getInitialPos().distance(target) <= DISTANCE) {
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
                && ((direction > 0 && target.x() >= this.collided.get().x() && target.x() < this.collided.get().x())
                        || (direction < 0 && target.x() <= this.collided.get().x()
                                && target.x() > this.collided.get().x()));
    }

    /**
     * Sets the enemy's position to the last known collision point, if present.
     */
    private void setPositionToCollision() {
        this.collided.ifPresent(super::setPosition);
    }

    /**
     * @return return if the Enemy has fallen out of patrol
     */
    private boolean isGlitched() {
        return this.check;
    }
}
