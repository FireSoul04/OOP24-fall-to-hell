package it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.factory.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.manager.SafeZoneManager;
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
    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final double FULL_LIFE = 20;
    private static final double DAMAGE = 20;
    private static final Vector2 VELOCITY = new Vector2(2, 20);
    private static final Map<BuffNames, Double> BUFF = Map.of(
            BuffNames.ATTACK, 10.0,
            BuffNames.ATTACK_SPEED, 20.0,
            BuffNames.LIFE, 30.0,
            BuffNames.MANA, 40.0,
            BuffNames.SPEED, 50.0);

    private BaseEnemyStatistics stats;
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
     * @param character   the target {@link Character} this enemy reacts to
     * @param manager     the {@link EnemyTimerManager} that handles familiar logic
     *                    in this context
     * @param ingage     the {@link SafeZoneManager} used to handle if the player enter a safe zone
     */
    public Centaur(final Level level, final Vector2 initialCord, final Character character,
            final EnemyTimerManager manager, final SafeZoneManager ingage) {
        super(level, new StatisticFactoryImpl().createBaseEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                initialCord, character, 10, new StatisticFactoryImpl().createOptional().withBuff(BUFF)), manager,
                ingage, "centaur.png");

        this.stats = (BaseEnemyStatistics) super.getStats();
        ingage.addEnemy(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        super.update(deltaTime);
        this.move(deltaTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        super.onCollision(other, direction);
        if (other instanceof BaseCollidableBlock || other instanceof BaseEntrance) {
            if (direction == Vector2.right() || direction == Vector2.left()) {
                if (this.collided.isEmpty() || this.collided.get().x() != direction.x()) {
                    this.collided = Optional.ofNullable(direction);
                } else {
                    this.direction *= -1;
                }
            }
        } else if (other instanceof Character) {
            attack();
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
        this.stats.getCharacter().setDamagedLife(this.stats.getAttack());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void move(final double deltaTime) {
        if (canSeePlayer()) {
            chasePlayer(deltaTime);
        } else {
            patrol(deltaTime);
        }
    }

    /**
     * Moves the enemy back and forth horizontally as a patrol behavior.
     *
     * @param deltaTime time elapsed since last update
     */
    private void patrol(final double deltaTime) {
        final double speed = this.stats.getSpeed().x();
        final Vector2 step = new Vector2(speed * direction, 0);
        final Vector2 target = this.getPosition().add(step);

        this.setPosition(target);
        setFacingRight(direction > 0);
    }

    /**
     * Moves the enemy toward the player position, attempting to navigate obstacles.
     *
     * @param deltaTime time elapsed since last update
     */
    private void chasePlayer(final double deltaTime) {
        final Vector2 current = this.getPosition();
        final Vector2 target = this.stats.getCharacter().getPosition();
        final Vector2 diff = target.subtract(current).normalize();
        final Vector2 moveStep = diff.multiply(this.stats.getSpeed());
        final var manager = super.getLevel().getJumpCollisionManager();

        Vector2 tryMove = current.add(moveStep);

        if (manager.isBlocked(tryMove, stats.getDimensions().width(), stats.getDimensions().height())) {

            final Vector2 up = current.add(new Vector2(0, -this.stats.getSpeed().y()));
            final Vector2 down = current.add(new Vector2(0, this.stats.getSpeed().y()));

            if (!manager.isBlocked(up, stats.getDimensions().width(), stats.getDimensions().height())) {
                tryMove = up;
            } else if (!manager.isBlocked(down, stats.getDimensions().width(), stats.getDimensions().height())) {
                tryMove = down;
            } else {
                return;
            }
        }

        this.setPosition(tryMove);
        setFacingRight(moveStep.x() > 0);
    }

    /**
     * Checks if the enemy can detect the player within its sensing distance.
     *
     * @return true if player is within sensing distance, false otherwise
     */
    private boolean canSeePlayer() {
        return this.getPosition().distance(this.stats.getCharacter().getPosition()) <= this.stats.getSenseDistance();
    }
}
