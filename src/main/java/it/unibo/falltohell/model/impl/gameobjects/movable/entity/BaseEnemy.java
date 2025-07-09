package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.util.Vector2;

/**
 * Abstract base class for all {@link Enemy} implementations.
 * <p>
 * Sets up the fundamental structure for enemy behavior, including movement,
 * collision, attacking logic, and stat management.
 * </p>
 *
 * @see Enemy
 * @see BaseEnemyStatistics
 * @see BoxCollider
 * @see Character
 * @see Vector2
 * @author Sara Visani
 */
public abstract class BaseEnemy extends EntityImpl implements Enemy {

    private BaseEnemyStatistics stats;

    /**
     * Constructs a base enemy entity with the given {@link Level} and
     * {@link BaseEnemyStatistics}.
     * <p>
     *
     * @param level the level the enemy belongs to
     * @param stats the statistical data defining the enemy's behavior and
     *              characteristics
     */
    public BaseEnemy(final Level level, final BaseEnemyStatistics stats) {
        super(level, stats.getInitialPos(), new BoxCollider(Vector2.zero(), stats.getDimensions()), stats);
        this.stats = (BaseEnemyStatistics) super.getStats();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacter(final Character character) {
        this.stats.setCharacter(character);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Also restarts the no-aggro timer upon being damaged.
     * </p>
     */
    @Override
    public void setDamagedLife(final double damage) {
        super.setDamagedLife(damage);
        this.stats.getTm().stopTimer(stats.getNoAggroName());
        this.stats.getTm().restartTimer(stats.getNoAggroName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void update(double deltaTime);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void onCollision(GameObject other, Vector2 direction);

    /**
     * Checks whether the enemy is currently at full health.
     * <p>
     *
     * @return {@code true} if the enemy is at maximum health, {@code false}
     *         otherwise
     */
    protected abstract boolean isFull();

    /**
     * Executes the attack behavior specific to the enemy.
     */
    protected abstract void attack();

    /**
     * Defines how the enemy moves each frame.
     * <p>
     *
     * @param deltaTime time elapsed since the last update, in seconds
     */
    protected abstract void move(double deltaTime);
}
