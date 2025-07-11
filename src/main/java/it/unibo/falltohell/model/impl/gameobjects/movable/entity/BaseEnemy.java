package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.EnemyTimerManager;
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
    private EnemyTimerManager manager;

    /**
     * Constructs a BaseEnemy instance with the specified {@link Level},
     * {@link BaseEnemyStatistics}, and {@link EnemyTimerManager}.
     * <p>
     * Initializes the enemy's position, collider, statistics, and registers the
     * "NoAggro" timer using the provided timer manager.
     * </p>
     *
     * @param level   the level the enemy belongs to
     * @param stats   the statistics defining the enemy's behavior and attributes
     * @param manager the timer manager responsible for managing enemy timers
     */
    public BaseEnemy(final Level level, final BaseEnemyStatistics stats, final EnemyTimerManager manager) {
        super(level, stats.getInitialPos(), new BoxCollider(Vector2.zero(), stats.getDimensions()), stats);
        this.stats = (BaseEnemyStatistics) super.getStats();
        this.manager = manager;
        this.manager.createNoAggroTimer(level, this, this.stats.getNoAggro());
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
        final String name = this.manager.getNoAggroTimerName(this);
        super.getLevel().getTimerManager().stopTimer(name);
        super.getLevel().getTimerManager().restartTimer(name);
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
     * {@inheritDoc}
     */
    @Override
    public boolean isDead() {
        if (this.stats.getLife() <= 0) {
            if (this.stats.getCharacter() instanceof Druid) {
                ((Druid) this.stats.getCharacter()).addKill();
            }
            this.manager.removeTimersFor(this, super.getLevel());
            super.getLevel().getGameData().addPoints(this.stats.getPoints());
            super.getLevel().removeGameObject(this);
            return true;
        }
        return false;
    }

    /**
     * Checks whether the enemy is currently at full health.
     * <p>
     *
     * @return {@code true} if the enemy is at maximum health, {@code false}
     *         otherwise
     */
    protected boolean isFull() {
        return this.stats.getLife() == this.stats.getFullLife();
    }

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

    /**
     * Returns the instance of the {@link EnemyTimerManager} responsible for
     * managing
     * enemy timers and their counters.
     *
     * @return the {@link EnemyTimerManager} instance
     */
    protected EnemyTimerManager getEnemyTimerManager() {
        return this.manager;
    }
}
