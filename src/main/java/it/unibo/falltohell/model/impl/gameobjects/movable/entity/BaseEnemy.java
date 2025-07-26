package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.ManagerIngage;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder.BuffBuilderImpl;
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

    /**
     * <p>
     * Represents types of timers used in game logic for characters and enemies.
     * </p>
     */
    public enum TimerType {
        /**
         * <p>
         * Timer related to attack cooldowns.
         * </p>
         */
        ATTACK,
        /**
         * <p>
         * Timer preventing aggro (aggression or targeting behavior) for a period.
         * </p>
         */
        NO_AGGRO
    }

    /**
     * <p>
     * Enumerates all the possible types of buffs that can be applied to a character
     * or entity.
     * </p>
     *
     * <p>
     * Each value corresponds to a stat-enhancing effect:
     * </p>
     * <ul>
     * <li><b>ATTACK</b>: Increases attack power</li>
     * <li><b>ATTACK_SPEED</b>: Reduces delay between attacks</li>
     * <li><b>LIFE</b>: Increases maximum or current life points</li>
     * <li><b>MANA</b>: Increases maximum or current mana</li>
     * <li><b>SPEED</b>: Boosts movement velocity</li>
     * </ul>
     */
    public enum BuffNames {
        /**
         * <p>
         * Increases damage dealt by the entity's attacks.
         * </p>
         */
        ATTACK,

        /**
         * <p>
         * Decreases attack cooldown for faster attack execution.
         * </p>
         */
        ATTACK_SPEED,

        /**
         * <p>
         * Increases current or maximum life.
         * </p>
         */
        LIFE,

        /**
         * <p>
         * Increases current or maximum mana.
         * </p>
         */
        MANA,

        /**
         * <p>
         * Increases movement speed.
         * </p>
         */
        SPEED
    }

    private final BaseEnemyStatistics stats;
    private final EnemyTimerManager manager;
    private final ManagerIngage ingageManager;
    private boolean ingage = true;

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
     * @param ingageManager  the {@link ManagerIngage} used to handle if the player enter a
     *                safe zone
     * @param fileName is the name of the image file associated to the enemy
     */
    public BaseEnemy(final Level level, final BaseEnemyStatistics stats, final EnemyTimerManager manager,
            final ManagerIngage ingageManager, final String fileName) {
        super(level, stats.getInitialPos(), stats);
        this.stats = (BaseEnemyStatistics) super.getStats();
        this.manager = manager;
        this.manager.createNoAggroTimer(level, this, this.stats.getNoAggro());
        this.ingageManager = ingageManager;
        this.initDrawable(Priority.MEDIUM, fileName);
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
        this.removeEntity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void removeEntity() {
        if (super.isDead()) {
            if (this.stats.getCharacter() instanceof Druid) {
                ((Druid) this.stats.getCharacter()).addKill();
            }
            this.manager.removeTimersFor(this, super.getLevel());
            this.ingageManager.removeEnemy(this);
            super.getLevel().getGameData().addPoints(this.stats.getPoints());
            this.dropBuff();
            super.getLevel().removeGameObject(this);
        }
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
     * Returns the instance of the {@link ManagerIngage} responsible for
     * managing
     * if the player enter or exit a safe zone.
     *
     * @return the {@link ManagerIngage} instance
     */
    protected ManagerIngage getManagerIngage() {
        return this.ingageManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIngage() {
        this.ingage = !this.ingage;
    }

    /**
     * Returns the current engagement state of the entity.
     *
     * @return {@code true} if the entity is currently engaged (e.g., in combat or
     *         alerted),
     *         {@code false} otherwise.
     */
    protected boolean getIngage() {
        return this.ingage;
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
        final List<Map.Entry<BuffNames, Double>> sorted = this.stats.getBuffMap().entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .toList();
        // Find the key, if it exists, of said percentage
        final Optional<BuffNames> typeBuff = IntStream.range(0, sorted.size() - 1)
                .filter(i -> {
                    final double lower = sorted.get(i).getValue();
                    final double upper = sorted.get(i + 1).getValue();
                    return number > lower && number <= upper;
                })
                .mapToObj(i -> sorted.get(i + 1).getKey())
                .findFirst();
        // Create the said buff if key was founded
        if (typeBuff.isPresent()) {
            new BuffBuilderImpl()
                    .withLevel(super.getLevel()).withPosition(super.getPosition()).withBuff(typeBuff.get(),
                            (CharacterStatistics) this.stats.getCharacter().getStats(), this.stats.getMultiplier())
                    .build();
        }
    }
}
