package it.unibo.falltohell.test.util.debug.enemy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.LongRangeEnemy;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.EnemyTimerManager;
import it.unibo.falltohell.model.api.manager.SafeZoneManager;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.EntityImpl;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.TimerType;
import it.unibo.falltohell.test.util.debug.DropBuilderDebug;
import it.unibo.falltohell.test.util.debug.DropDebug;
import it.unibo.falltohell.test.util.debug.druid.DruidDebug;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * Abstract base class representing a generic enemy in the game.
 * <p>
 * Extends {@link EntityImpl} and implements the {@link Enemy} interface.
 * Provides core functionalities such as movement, attack handling,
 * timer management, regeneration, and interactions with both the player
 * character and the safe zone system.
 * </p>
 * <p>
 * This class also manages the enemy's removal state, handles resets
 * when leaving a safe zone, and supports dropping random buffs
 * based on weighted probabilities.
 * </p>
 * <p>
 * Designed to be extended by specific enemy implementations that define
 * custom patrol and chase behaviors.
 * </p>
 *
 * @author Sara Visani
 */
public abstract class BaseEnemyDebug extends EntityImpl implements Enemy {
    private static final double CHARACTER_REGEN = 0.1;
    private static final double EPSILON = 1e-6;

    private final BaseEnemyStatistics stats;
    private final EnemyTimerManager manager;
    private final SafeZoneManager safeZoneManager;
    private boolean removed;
    private Optional<DropDebug> drop;

    /**
     * Constructs a BaseEnemy instance with the specified {@link Level},
     * {@link BaseEnemyStatistics}, and {@link EnemyTimerManager}.
     * <p>
     * Initializes the enemy's position, collider, statistics, and registers the
     * "NoAggro" timer using the provided timer manager.
     * </p>
     *
     * @param level           the level the enemy belongs to
     * @param stats           the statistics defining the enemy's behavior and
     *                        attributes
     * @param manager         the timer manager responsible for managing enemy
     *                        timers
     * @param safeZoneManager the {@link SafeZoneManager} used to handle if the
     *                        player enter a
     *                        safe zone
     * @param fileName        is the name of the image file associated to the enemy
     */
    @SuppressFBWarnings(value = { "EI_EXPOSE_REP2",
            "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR" },
            justification = "EnemyTimerManager is immutable;createNoAggroTimer and resetEnemy are safe in constructor")
    public BaseEnemyDebug(final Level level, final BaseEnemyStatistics stats, final EnemyTimerManager manager,
            final SafeZoneManager safeZoneManager, final String fileName) {
        super(level, stats.getInitialPos(), stats);
        this.stats = (BaseEnemyStatistics) super.getStats();
        this.manager = manager;
        this.manager.createNoAggroTimer(this);
        this.safeZoneManager = safeZoneManager;
        this.safeZoneManager.addEnemyCall(this::resetEnemy);
        this.initDrawable(Priority.VERY_LOW, fileName);
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
        if (this.removed) {
            return;
        }
        super.setDamagedLife(damage);
        this.removeEntity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntity() {
        if (super.isDead()) {
            this.removed = true;
            if (this.getCharacter() instanceof Druid) {
                ((DruidDebug) this.getCharacter()).addKill();
            }
            ((CharacterStatistics) this.getCharacter().getStats())
                    .addMana(((CharacterStatistics) this.getCharacter().getStats()).getInitialMana() * CHARACTER_REGEN);
            this.manager.removeTimersFor(this);
            super.getLevel().getGameData().addPoints(this.stats.getPoints());
            this.dropBuff();
            super.removeEntity();
        } else {
            this.manager.restartEnemyTimer(this, TimerType.NO_AGGRO);
        }
    }

    /**
     * Checks whether the enemy is currently at full health.
     * <p>
     *
     * @return {@code true} if the enemy is at maximum health, {@code false}
     *         otherwise
     */
    public boolean isFull() {
        return Math.abs(this.stats.getLife() - this.stats.getFullLife()) < EPSILON;
    }

    /**
     * Executes the attack behavior specific to the enemy.
     */
    public void attack() {
        this.manager.restartEnemyTimer(this, TimerType.NO_AGGRO);
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
     * Defines how the enemy moves each frame.
     * <p>
     *
     * @param deltaTime time elapsed since the last update, in seconds
     */
    public void move(final double deltaTime) {
        final Vector2 speed = this.stats.getSpeed().multiply(deltaTime);
        final Vector2 currentPos = super.getPosition();
        final Vector2 charaPos = this.getCharacter().getPosition();

        if (this.canSeePlayer()) {
            this.chase(charaPos, currentPos, speed);
        } else {
            this.patrol(currentPos, speed);
        }
    }

    /**
     * Executes patrol behavior for the enemy. The enemy moves back and forth
     * within a defined distance from its initial position, reversing direction
     * when reaching a boundary or a collision.
     *
     * @param currentPos the current position of the enemy
     * @param speed      the movement amount for this frame
     */
    public abstract void patrol(Vector2 currentPos, Vector2 speed);

    /**
     * Executes chase behavior when the player is within the enemy's detection
     * range.
     * The enemy attempts to move toward the player while respecting patrol
     * boundaries
     * and potential obstacles.
     *
     * @param charaPos   the position of the player character
     * @param currentPos the current position of the enemy
     * @param speed      the movement amount for this frame
     */
    public abstract void chase(Vector2 charaPos, Vector2 currentPos, Vector2 speed);

    /**
     * Checks if the enemy can detect the player within its sensing distance.
     *
     * @return true if player is within sensing distance, false otherwise
     */
    public boolean canSeePlayer() {
        return this.getPosition().distance(this.getCharacter().getPosition()) <= this.stats.getSenseDistance();
    }

    /**
     * @return the current character in the level
     */
    public Character getCharacter() {
        return super.getLevel().getGameData().getCurrentCharacter();
    }

    /**
     * Returns the instance of the {@link EnemyTimerManager} responsible for
     * managing
     * enemy timers and their counters.
     *
     * @return the {@link EnemyTimerManager} instance
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
    justification = "EnemyTimerManager and SafeZoneManager are safe to expose for debug purposes")
    public EnemyTimerManager getEnemyTimerManager() {
        return this.manager;
    }

    /**
     * Returns the {@link SafeZoneManager} that handles safe zone transitions
     * for this enemy.
     *
     * @return the safe zone manager
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
    justification = "EnemyTimerManager and SafeZoneManager are safe to expose for debug purposes")
    public SafeZoneManager getSafeZoneManager() {
        return this.safeZoneManager;
    }

    /**
     * <p>
     * Resets the enemy to its initial state when re-entering the game world
     * after exiting a safe zone.
     * </p>
     *
     * <p>
     * This method performs the following actions:
     * </p>
     * <ul>
     * <li>Restores the enemy's life to its maximum value using
     * {@link BaseEnemyStatistics#getFullLife()}</li>
     * <li>Moves the enemy back to its original spawn position defined by
     * {@link BaseEnemyStatistics#getInitialPos()}</li>
     * </ul>
     *
     * <p>
     * This method is typically invoked by the {@link SafeZoneManager} when
     * reactivating enemies after the player leaves a safe zone.
     * </p>
     *
     * @see SafeZoneManager#resetEnemy()
     * @see BaseEnemyStatistics
     */
    public void resetEnemy() {
        this.stats.setLife(this.stats.getFullLife());
        super.setPosition(this.stats.getInitialPos());
        this.removed = false;
        this.manager.restartEnemyTimer(this, TimerType.NO_AGGRO);
        if (this instanceof LongRangeEnemy) {
            this.manager.restartEnemyTimer(this, TimerType.ATTACK);
        }
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
    public void dropBuff() {
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
            this.drop = Optional.of(new DropBuilderDebug()
                    .withLevel(super.getLevel())
                    .withPosition(super.getPosition())
                    .withBuff(
                            typeBuff.get(),
                            (CharacterStatistics) this.getCharacter().getStats(),
                            this.stats.getMultiplier())
                    .build());
        } else {
            this.drop = Optional.empty();
        }
    }

    /**
     * Returns the character regeneration rate.
     *
     * @return the character regeneration rate as a double
     */
    public static double getCharacterRegen() {
        return CHARACTER_REGEN;
    }

    /**
     * Indicates whether this object has been removed.
     *
     * @return {@code true} if removed; {@code false} otherwise
     */
    public boolean isRemoved() {
        return this.removed;
    }

    /**
     * Returns the last drop.
     *
     * @return the last drop as a DropDebug in a Optional
     */
    public Optional<DropDebug> getDrop() {
        return this.drop;
    }
}
