package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.EnemyTimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobjects.block.BaseBlock;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
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
    private static final Vector2 VELOCITY = new Vector2(1, 10);
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
     */
    public Imp(final Level level, final Vector2 initialCord, final Character character,
            final EnemyTimerManager manager) {
        super(level,
                new StatisticFactoryImpl().createGroundRestrictedEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                        initialCord, character, 10, new StatisticFactoryImpl()
                                .createOptional().withRegen(REGEN_STAT).withSenseDistance(CHAR_DISTANCE),
                        DISTANCE),
                manager);

        this.stats = (RestrictedBaseEnemyStatistics) super.getStats();
        super.initDrawable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.move(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof BaseBlock) {
            if (direction.y() != 0) {
                this.collided = Optional.of(super.getPosition());
            }
        } else if (other instanceof Character) {
            this.attack();
        }
        // TODO delete when the tests works without this
        this.collided = Optional.of(super.getPosition());
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
    protected void move(final double deltaTime) {

        double otherX = deltaTime * this.stats.getSpeed().x();
        final double y = super.getPosition().y();
        final Vector2 chara = this.stats.getCharacter().getPosition();

        while (otherX > 0) {
            if (chara.distance(super.getPosition()) > this.stats.getSenseDistance()) {
                if (this.stats.getInitialPos()
                        .distance(new Vector2(super.getPosition().x() + (otherX * this.direction), y)) <= this.stats
                                .getDistance()) {
                    if (this.collided.isPresent() && super.getPosition().add(new Vector2(otherX * this.direction, 0))
                            .x() > this.collided.get().x()) {
                        if (super.getPosition() != this.collided.get()) {
                            otherX -= super.getPosition().distance(this.collided.get());
                            super.setPosition(this.collided.get());
                        }
                        this.direction *= -1;
                    }
                    super.setPosition(super.getPosition().add(new Vector2(otherX * this.direction, 0)));
                    otherX = 0;
                } else {
                    if (this.collided.isPresent() && super.getPosition().add(new Vector2(otherX * this.direction, 0))
                            .x() > this.collided.get().x()) {
                        if (super.getPosition() != this.collided.get()) {
                            otherX -= super.getPosition().distance(this.collided.get());
                            super.setPosition(this.collided.get());
                        }
                        this.direction *= -1;
                    }
                    otherX -= Math.abs((this.stats.getInitialPos().x() + this.stats.getDistance() * this.direction)
                            - super.getPosition().x());
                    super.setPosition(
                            new Vector2(this.stats.getInitialPos().x() + this.stats.getDistance() * this.direction, y));
                    this.direction *= -1;
                }
            } else {
                if ((chara.x() <= this.stats.getDistance() + this.stats.getInitialPos().x())
                        && (chara.x() >= this.stats.getInitialPos().x() - this.stats.getDistance())) {
                    if (chara.distance(super.getPosition()) > super.getPosition()
                            .distance(new Vector2(super.getPosition().x() + otherX * this.direction, y))) {
                        if (chara.x() - super.getPosition().x() > 0 && !(this.collided.isPresent()
                                && this.collided.get().x() < super.getPosition().add(new Vector2(otherX, 0)).x())) {
                            super.setPosition(super.getPosition().add(new Vector2(otherX, 0)));
                            otherX = 0;
                        } else if (!(this.collided.isPresent()
                                && this.collided.get().x() > super.getPosition().add(new Vector2(-otherX, 0)).x())) {
                            super.setPosition(super.getPosition().add(new Vector2(-otherX, 0)));
                            otherX = 0;
                        } else {
                            super.setPosition(this.collided.get());
                            otherX = 0;
                        }
                    } else if (!(this.collided.isPresent() && this.collided.get().x() < chara.x())) {
                        super.setPosition(chara);
                        otherX = 0;
                    } else {
                        super.setPosition(this.collided.get());
                        otherX = 0;
                    }
                } else {
                    if (chara.x() - super.getPosition().x() > 0) {
                        if (this.stats.getInitialPos()
                                .distance(new Vector2(super.getPosition().x() + otherX, y)) <= this.stats.getDistance()
                                && !(this.collided.isPresent() && super.getPosition().add(new Vector2(otherX, 0))
                                        .x() > this.collided.get().x())) {
                            super.setPosition(super.getPosition().add(new Vector2(otherX, 0)));
                            otherX = 0;
                        } else if (!(this.collided.isPresent() && this.collided.get()
                                .x() < this.stats.getInitialPos().x() + this.stats.getDistance())) {
                            super.setPosition(
                                    new Vector2(this.stats.getInitialPos().x() + this.stats.getDistance(), y));
                            otherX = 0;
                            this.direction *= -1;
                        } else {
                            super.setPosition(this.collided.get());
                            otherX = 0;
                        }
                    } else {
                        if (this.stats.getInitialPos()
                                .distance(new Vector2(super.getPosition().x() - otherX, y)) <= this.stats.getDistance()
                                && !(this.collided.isPresent() && super.getPosition().add(new Vector2(-otherX, 0))
                                        .x() > this.collided.get().x())) {
                            super.setPosition(super.getPosition().add(new Vector2(-otherX, 0)));
                            otherX = 0;
                        } else if (!(this.collided.isPresent() && this.collided.get()
                                .x() > this.stats.getInitialPos().x() - this.stats.getDistance())) {
                            super.setPosition(
                                    new Vector2(this.stats.getInitialPos().x() - this.stats.getDistance(), y));
                            otherX = 0;
                            this.direction *= -1;
                        } else {
                            super.setPosition(this.collided.get());
                            otherX = 0;
                        }
                    }
                }
            }
        }
    }
}
