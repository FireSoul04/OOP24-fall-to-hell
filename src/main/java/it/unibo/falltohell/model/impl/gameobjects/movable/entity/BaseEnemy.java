package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.AttackBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.AttackSpeedBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.LifeBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.ManaBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.SpeedBuff;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.EnemyTimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
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

    public enum TimerType {
        ATTACK,
        NO_AGGRO
    }

    protected enum BuffNames {
        ATTACK,
        ATTACK_SPEED,
        LIFE,
        MANA,
        SPEED
    }

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
     * And calls the death checker.
     * </p>
     */
    @Override
    public void setDamagedLife(final double damage) {
        super.setDamagedLife(damage);
        this.manager.restartEnemyTimer(super.getLevel(), this, TimerType.NO_AGGRO);
        this.isDead();
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
            this.dropBuff();
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

    /**
     * <p>
     * Randomly applies a buff to the character based on weighted probability
     * thresholds.
     * </p>
     *
     * <p>
     * Steps:
     * </p>
     * <ul>
     * <li>Generates a random number between 0.0 and 100.0 (with one decimal place)
     * using ThreadLocalRandom.</li>
     * <li>Sorts the buff probability map entries by their threshold values.</li>
     * <li>Finds which interval the random number falls into, returning the
     * associated buff key.</li>
     * <li>Creates and adds the corresponding Buff object to the character's
     * BuffManager.</li>
     * </ul>
     *
     * <p>
     * This uses Java Streams, Optionals, and IntStream for efficient
     * functional-style operations.
     * </p>
     */
    protected void dropBuff() {
        // Casual Percentage
        final double number = Math.round(ThreadLocalRandom.current().nextDouble(0, 100) * 10.0) / 10.0;
        // Sort the map to have the percentage intervals in order
        final List<Map.Entry<String, Double>> sorted = this.stats.getBuffMap().entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .toList();
        // Find the key, if it exist, of said percentage
        final Optional<String> typeBuff = IntStream.range(0, sorted.size() - 1)
                .filter(i -> {
                    double lower = sorted.get(i).getValue();
                    double upper = sorted.get(i + 1).getValue();
                    return number > lower && number <= upper;
                })
                .mapToObj(i -> sorted.get(i + 1).getKey())
                .findFirst();
        // Create the said buff if key was founded
        if (typeBuff.isPresent()) {
            if (typeBuff.get().equals(BuffNames.ATTACK.name())) {
                this.stats.getCharacter().getBuffManager().addBuff(new AttackBuff(
                        (CharacterStatistics) this.stats.getCharacter().getStats(), this.stats.getMultiplier()));
            } else if (typeBuff.get().equals(BuffNames.ATTACK_SPEED.name())) {
                this.stats.getCharacter().getBuffManager().addBuff(new AttackSpeedBuff(
                        (CharacterStatistics) this.stats.getCharacter().getStats(), this.stats.getMultiplier()));
            } else if (typeBuff.get().equals(BuffNames.LIFE.name())) {
                this.stats.getCharacter().getBuffManager().addBuff(new LifeBuff(
                        (CharacterStatistics) this.stats.getCharacter().getStats(), this.stats.getMultiplier()));
            } else if (typeBuff.get().equals(BuffNames.MANA.name())) {
                this.stats.getCharacter().getBuffManager().addBuff(new ManaBuff(
                        (CharacterStatistics) this.stats.getCharacter().getStats(), this.stats.getMultiplier()));
            } else {
                this.stats.getCharacter().getBuffManager().addBuff(new SpeedBuff(
                        (CharacterStatistics) this.stats.getCharacter().getStats(), this.stats.getMultiplier()));
            }
        }
    }
}
