package it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Concrete implementation of {@link BaseEnemy}, representing a specific type of
 * enemy: a {@code Centaur}.
 * <p>
 * This enemy has predefined statistics such as:
 * <ul>
 * <li>{@link #FULL_LIFE}</li>
 * <li>{@link #DAMAGE}</li>
 * <li>{@link #VELOCITY}</li>
 * <li>{@link #DIMENSIONS}</li>
 * <li>others specified into {@link #stats}</li>
 * </ul>
 * It can detect and attack a {@link Character} and regenerates health when not
 * in combat.
 * </p>
 *
 * @author Sara Visani
 */
public class Centaur extends BaseEnemy {
    private static final int POINTS = 30;
    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final double FULL_LIFE = 60;
    private static final double DAMAGE = 7;
    private static final Vector2 VELOCITY = new Vector2(1, 1);
    private static final Map<BuffNames, Double> BUFF = Map.of(
            BuffNames.ATTACK, 10.0,
            BuffNames.ATTACK_SPEED, 20.0,
            BuffNames.LIFE, 30.0,
            BuffNames.MANA, 40.0,
            BuffNames.SPEED, 50.0);
    private static final double EPSILON = 1e-9;

    private final BaseEnemyStatistics stats;
    private int direction = 1;
    private Optional<Vector2> collided = Optional.empty();

    /**
     * Constructs a {@link Centaur} enemy in the given {@link Level} at a given
     * {@link Vector2} position,
     * and associates it with a target {@link Character}.
     * <p>
     * Also registers a custom regeneration timer based on the enemy's aggression
     * state.
     * </p>
     *
     * @param level       the game {@link Level} where the enemy exists
     * @param initialCord the initial {@link Vector2} position of the enemy
     * @param manager     the {@link EnemyTimerManager} that handles familiar logic
     *                    in this context
     * @param ingage      the {@link SafeZoneManager} used to handle if the player
     *                    enter a safe zone
     */
    public Centaur(final Level level, final Vector2 initialCord,
            final EnemyTimerManager manager, final SafeZoneManager ingage) {
        super(level, new StatisticFactoryImpl().createBaseEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                initialCord, POINTS, new StatisticFactoryImpl().createOptional().withBuff(BUFF)), manager,
                ingage, "centaur.png");

        this.stats = (BaseEnemyStatistics) super.getStats();
        ingage.addEnemy(this, "centaur.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        super.onCollision(other, direction);
        if (other instanceof BaseCollidableBlock || other instanceof BaseEntrance) {
            if (direction.equals(Vector2.right()) || direction.equals(Vector2.left())) {
                if (this.collided.isEmpty() || Math.abs(this.collided.get().x() - direction.x()) > EPSILON) {
                    this.collided = Optional.ofNullable(direction);
                } else {
                    this.direction *= -1;
                }
            }
        } else if (other instanceof Character) {
            attack();
        }
        this.setFacingRight(this.direction > 0);
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
    protected void patrol(final Vector2 current, final Vector2 speed) {
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
    protected void chase(final Vector2 target, final Vector2 current, final Vector2 speed) {
        final Vector2 diff = target.subtract(current).normalize();
        final Vector2 moveStep = diff.multiply(speed);
        final var manager = super.getLevel().getJumpCollisionManager();

        Vector2 tryMove = current.add(moveStep);

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
}
