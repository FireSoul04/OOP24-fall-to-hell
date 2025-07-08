package it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy;

import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.BaseEnemy;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticFactoryImpl;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Concrete implementation of {@link BaseEnemy}, representing a specific type of
 * enemy called Monster1.
 * This enemy has predefined stats like life, damage, velocity, and reacts to
 * collisions with blocks and characters.
 * <p>
 * Includes a regeneration timer for life when not aggressive.
 * </p>
 *
 * @author Sara Visani
 */
public class Monster1 extends BaseEnemy {
    private static final int CHAR_DISTANCE = 70;
    private static final double REGEN_STAT = 0.1;
    private static final Dimensions DIMENSIONS = new Dimensions(20, 20);
    private static final double FULL_LIFE = 20;
    private static final double DAMAGE = 20;
    private static final Vector2 VELOCITY = new Vector2(2, 20);
    private static final int NO_AGGRO = 10;

    private BaseEnemyStatistics stats;
    private int direction = 1;

    /**
     * Constructs a Monster1 enemy in the specified {@link Level} at a given
     * position.
     * It also registers a regeneration timer on the enemy's stats.
     * <p>
     *
     * @param level       the game {@link Level} where this enemy exists
     * @param initialCord the initial {@link Vector2} position of the enemy
     * @param character   the {@link Character} instance this enemy is linked to or
     *                    targets
     */
    public Monster1(final Level level, final Vector2 initialCord, final Character character) {
        super(level, new StatisticFactoryImpl().createBaseEnemyStatistic(FULL_LIFE, DAMAGE, VELOCITY, DIMENSIONS,
                initialCord, NO_AGGRO, character));

        this.stats = (BaseEnemyStatistics) super.getStats();

        this.stats.getTm().addTimer(this.stats.getNoAggroName(), new CustomTimerImpl(NO_AGGRO, () -> {
            if (this.isFull()) {
                if (this.stats.getLife() + this.stats.getLife() * REGEN_STAT > this.stats.getFullLife()) {
                    this.stats.setLife(this.stats.getFullLife());
                } else {
                    this.stats.addLife(this.stats.getLife() * REGEN_STAT);
                }
            }
            this.stats.getTm().restartTimer(this.stats.getNoAggroName());
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
                this.direction *= -1;
            }
        } else if (other instanceof Character) {
            attack();
            // super.getTm().restart(getNo_aggro());
        }
        // TODO delete when the tests works without this
        this.direction *= -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDamagedLife(final double damage) {
        super.setDamagedLife(damage);
        // super.getTm().restart(getNo_aggro());
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

        if (chara.distance(super.getPosition()) > CHAR_DISTANCE) {
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
