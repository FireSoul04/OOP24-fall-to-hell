package it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy;

import java.util.Map;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.manager.SafeZoneManager;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.projectile.TrackEnemyProjectile;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Represents a ranged enemy ("Lotawiec") that moves horizontally,
 * attacks by shooting projectiles, and reacts to collisions with blocks and
 * characters.
 * <p>
 * This enemy periodically attacks the target {@link Character} by firing
 * {@link TrackEnemyProjectile}s if the character is within sensing distance.
 * <p>
 * It inherits from {@link BaseEnemy} and uses {@link LongRangeEnemyStatistics}
 * for managing its stats and behavior.
 *
 * @author Sara Visani
 * @see BaseEnemy
 * @see TrackEnemyProjectile
 * @see LongRangeEnemyStatistics
 * @see Character
 */
public class Lotawiec extends BaseEnemy {

    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final double FULL_LIFE = 20;
    private static final double DAMAGE = 20;
    private static final Vector2 VELOCITY = new Vector2(2, 20);
    private static final Dimensions DIMENSIONS_ARROW = new Dimensions(10, 10);
    private static final double DAMAGE_A = 10;
    private static final Vector2 VELOCITY_ARROW = new Vector2(1, 10);
    private static final int ATTACK_TIME = 4000;
    private static final Map<BuffNames, Double> BUFF = Map.of(
            BuffNames.ATTACK, 10.0,
            BuffNames.ATTACK_SPEED, 20.0,
            BuffNames.LIFE, 30.0,
            BuffNames.MANA, 40.0,
            BuffNames.SPEED, 50.0);

    private LongRangeEnemyStatistics stats;
    private int direction = 1;
    private Vector2 jump = Vector2.zero();

    /**
     * Constructs a new Lotawiec enemy with specified initial position, target
     * character,
     * and a timer manager for attack scheduling.
     * <p>
     * Initializes its statistics, dimensions, damage, velocity, and attack timers.
     *
     * @param level       the level this enemy belongs to
     * @param initialCord the initial position of the enemy
     * @param character   the target character this enemy tracks and attacks
     * @param manager     the timer manager handling enemy-specific timers
     * @param ingage     the {@link SafeZoneManager} used to handle if the player enter a safe zone
     *
     * @see LongRangeEnemyStatistics
     * @see CustomTimerImpl
     */
    public Lotawiec(final Level level, final Vector2 initialCord, final Character character,
            final EnemyTimerManager manager, final SafeZoneManager ingage) {
        super(level,
                new StatisticFactoryImpl().createLongRangeEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                        initialCord, character, 10, new StatisticFactoryImpl().createOptional().withBuff(BUFF),
                        DAMAGE_A,
                        VELOCITY_ARROW, DIMENSIONS_ARROW, ATTACK_TIME),
                manager, ingage, "lotawiec.png");

        stats = (LongRangeEnemyStatistics) super.getStats();

        final String name = super.getEnemyTimerManager().getNextAttackName(this);
        super.getLevel().getTimerManager().addTimer(name, new CustomTimerImpl(this.stats.getTimeAttack(), () -> {
            this.attack();
            super.getLevel().getTimerManager().restartTimer(name);
        }));
        ingage.addEnemy(this);
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
        if (other instanceof BaseCollidableBlock || other instanceof BaseEntrance) {
            if (direction.y() != 0) {
                this.direction *= -1;
            }
            if (direction.y() > 0) {
                this.jump = Vector2.down();
            } else {
                this.jump = Vector2.up();
            }
        } else if (other instanceof Character) {
            this.stats.getCharacter().setDamagedLife(DAMAGE);
        }
        // TODO delete when the tests works without this
        this.direction *= -1;
        this.setFacingRight(this.direction > 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void attack() {
        if (this.stats.getCharacter().getPosition().distance(super.getPosition()) < this.stats.getSenseDistance()) {
            new TrackEnemyProjectile(super.getLevel(),
                    super.getPosition().subtract(new Vector2(0, this.stats.getDimensions().width() + 1)),
                    this.stats.getProjectileSpeed(),
                    new BoxCollider(Vector2.zero(), this.stats.getProjectileDimensions()), DAMAGE_A,
                    this.stats.getCharacter(), this.stats.getSenseDistance());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void move(final double deltaTime) {
        final Vector2 chara = this.stats.getCharacter().getPosition();
        final double charX = this.stats.getCharacter().getPosition().x();

        if (chara.distance(super.getPosition()) > this.stats.getSenseDistance()) {
            super.setPosition(super.getPosition().add(
                    (new Vector2(deltaTime * this.stats.getSpeed().x() * this.direction, super.getPosition().y()))));
        } else {
            if (charX - super.getPosition().x() > 0) {
                if (this.direction > 0) {
                    super.setPosition(super.getPosition()
                            .add((new Vector2(deltaTime * this.stats.getSpeed().x(), super.getPosition().y()))));
                } else {
                    if (this.jump.y() > 0) {
                        super.setPosition(super.getPosition()
                                .add((new Vector2(this.stats.getSpeed().x(), deltaTime * super.getPosition().y()))));
                    } else if (this.jump.y() < 0) {
                        super.setPosition(super.getPosition()
                                .add((new Vector2(this.stats.getSpeed().x(), -deltaTime * super.getPosition().y()))));
                    }
                }
            } else {
                if (this.direction > 0) {
                    super.setPosition(super.getPosition()
                            .add((new Vector2(-deltaTime * this.stats.getSpeed().x(), super.getPosition().y()))));
                } else {
                    if (this.jump.y() > 0) {
                        super.setPosition(super.getPosition()
                                .add((new Vector2(this.stats.getSpeed().x(), deltaTime * super.getPosition().y()))));
                    } else if (this.jump.y() < 0) {
                        super.setPosition(super.getPosition()
                                .add((new Vector2(this.stats.getSpeed().x(), -deltaTime * super.getPosition().y()))));
                    }
                }
            }
        }
    }
}
