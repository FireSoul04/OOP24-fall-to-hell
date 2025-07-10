package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.LevelImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.ProjectileImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Concrete implementation of a long-range enemy type.
 * Extends {@link BaseEnemy} to provide specific behaviors for Monster2 enemies.
 * <p>
 * This class manages movement, attacks, collision behavior, and health
 * regeneration timers.
 * </p>
 *
 * @author Sara Visani
 */
public class Tengu extends BaseEnemy {
    private static final double CHAR_DISTANCE = 20;
    private static final int ATTACK_TIME = 4000;
    private static final double REGEN_STAT = 0.1;
    private static final Dimensions DIMENSIONS = new Dimensions(10, 10);
    private static final Dimensions DIMENSIONS_ARROW = new Dimensions(10, 10);
    private static final double FULL_LIFE = 10;
    private static final double DAMAGE = 10; // Physical damage
    private static final double DAMAGE_A = 10; // Damage of projectile
    private static final Vector2 VELOCITY = new Vector2(1, 10);
    private static final Vector2 VELOCITY_ARROW = new Vector2(1, 10);
    private static final double DISTANCE = 10;

    private final RestrictedLongRangeEnemyStatistics stats;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();

    /**
     * Constructs a new Monster2 enemy instance.
     * <p>
     *
     * @param level       the {@link Level} this enemy belongs to
     * @param initialCord the initial position of the enemy in the level
     * @param character   the {@link Character} this enemy targets or is associated
     *                    with
     */
    public Tengu(final Level level, final Vector2 initialCord, final Character character) {
        super(level, new StatisticFactoryImpl().createLongRangeRestrictedStatistic(FULL_LIFE, DAMAGE, VELOCITY,
                DIMENSIONS, initialCord, Optional.empty(), character, Optional.of(REGEN_STAT),
                Optional.of(CHAR_DISTANCE), 10, DAMAGE_A, VELOCITY_ARROW, DIMENSIONS_ARROW, DISTANCE, ATTACK_TIME));

        stats = (RestrictedLongRangeEnemyStatistics) super.getStats();

        this.stats.getTm().addTimer(this.stats.getAttackName(), new CustomTimerImpl(this.stats.getTimeAttack(), () -> {
            this.attack();
            this.stats.getTm().restartTimer(this.stats.getAttackName());
        }));
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
        if (other instanceof Block) {
            if (direction.y() != 0) {
                this.collided = Optional.of(super.getPosition());
            }
        } else if (other instanceof Character) {
            this.stats.getCharacter().setDamagedLife(DAMAGE);
        }
        // TODO delete when the tests works without this
        this.collided = Optional.of(super.getPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isFull() {
        return this.stats.getLife() == FULL_LIFE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void attack() {
        if (this.stats.getCharacter().getPosition().distance(super.getPosition()) < this.stats.getSenseDistance()) {
            new ProjectileImpl(new LevelImpl(),
                    super.getPosition().subtract(new Vector2(0, this.stats.getDimensions().width() + 1)),
                    this.stats.getProjectileDimensions().width(), this.stats.getProjectileDimensions().height(),
                    this.stats.getProjectileSpeed().x(), this.stats.getProjectileSpeed().y(),
                    new BoxCollider(Vector2.zero(), this.stats.getProjectileDimensions()));
        }
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
