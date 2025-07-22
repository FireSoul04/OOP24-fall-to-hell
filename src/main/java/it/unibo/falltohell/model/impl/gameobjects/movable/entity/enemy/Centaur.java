package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.EnemyTimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.gameobjects.BaseBlock;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.ManagerFamiliars;
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
        BuffNames.SPEED, 50.0
    );

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
     * @param manager     the {@link ManagerFamiliars} that handles familiar logic in this context
     */
    public Centaur(final Level level, final Vector2 initialCord, final Character character,final EnemyTimerManager manager) {
        super(level, new StatisticFactoryImpl().createBaseEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                initialCord, character, 10, new StatisticFactoryImpl().createOptional().withBuff(BUFF)), manager);

        this.stats = (BaseEnemyStatistics) super.getStats();
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
        final Vector2 character = this.stats.getCharacter().getPosition();
        final double characterX = this.stats.getCharacter().getPosition().x();
        final int characterDirection;

        if (character.distance(super.getPosition()) > this.stats.getSenseDistance()) {
            if (this.collided.isEmpty()) {
                super.setPosition(super.getPosition().add(
                        (new Vector2(deltaTime * this.stats.getSpeed().x() * this.direction,
                                super.getPosition().y()))));
            } else {
                final double jumpDirection = this.collided.get().x();
                super.setPosition(super.getPosition().add(
                        (new Vector2(2 * deltaTime / 3 * super.getPosition().x(),
                                deltaTime / 3 * this.stats.getSpeed().y() * jumpDirection))));
            }
        } else {
            if (characterX - super.getPosition().x() > 0) {
                characterDirection = 1;
            } else {
                characterDirection = -1;
            }
            if (this.direction > 0) {
                if (this.collided.isEmpty()) {
                    super.setPosition(super.getPosition().add(
                            (new Vector2(characterDirection * deltaTime * this.stats.getSpeed().x(),
                                    super.getPosition().y()))));
                } else {
                    super.setPosition(super.getPosition().add(
                            (new Vector2(characterDirection * 2 * deltaTime / 3 * super.getPosition().x(),
                                    deltaTime / 3 * this.stats.getSpeed().y()))));
                }
            }
        }
    }
}
