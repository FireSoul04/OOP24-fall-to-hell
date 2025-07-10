package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;

import java.util.Optional;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
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

    private BaseEnemyStatistics stats;
    private int direction = 1;

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
     */
    public Centaur(final Level level, final Vector2 initialCord, final Character character) {
        super(level, new StatisticFactoryImpl().createBaseEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                initialCord, Optional.empty(), character, Optional.empty(), Optional.empty(), 10));

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
        if (other instanceof Block) {
            if (direction.y() != 0) {
                this.direction *= -1;
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
    protected boolean isFull() {
        return this.stats.getLife() == this.stats.getFullLife();
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
                }
            } else {
                if (this.direction > 0) {
                    super.setPosition(super.getPosition()
                            .add((new Vector2(-deltaTime * this.stats.getSpeed().x(), super.getPosition().y()))));
                }
            }
        }
    }

}
