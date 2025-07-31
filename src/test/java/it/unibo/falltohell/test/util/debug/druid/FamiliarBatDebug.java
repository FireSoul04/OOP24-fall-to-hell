package it.unibo.falltohell.test.util.debug.druid;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.enemy.Enemy;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.MovableImpl;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.test.util.debug.AttackFinishListenerDebug;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * <p>
 * A debug implementation of a familiar bat that assists a character (typically
 * a druid) in combat.
 * </p>
 *
 * <p>
 * The FamiliarBatDebug follows the associated character, attacks enemies in
 * range, and regenerates
 * the character's life and mana upon hitting enemies.
 * </p>
 *
 * <p>
 * Features include:
 * <ul>
 * <li>Periodic attack timer (every 1000ms)</li>
 * <li>Randomized number of attack hits per attack with weighted
 * probabilities</li>
 * <li>Movement following the character when idle</li>
 * <li>Collision detection with blocks and enemies to stop attacks or apply
 * damage</li>
 * <li>Regeneration of the character's mana and life based on hits</li>
 * <li>Callback listener to notify when attack finishes</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class is used primarily for debugging and testing purposes.
 * </p>
 *
 * @author Sara Visani
 */
public class FamiliarBatDebug extends MovableImpl {

    private static final int P_30 = 30;
    private static final int P_40 = 70;
    private static final int CASE_5 = 5;
    private static final double REGEN_RATE = 0.30;
    private static final int OFFSET_B_TO_C = -(int) TILE_SIZE;
    private static final double DAMAGE = 15;
    private static final double DISTANCE = 15 * TILE_SIZE;
    private static final Vector2 VELOCITY = new Vector2(2, 3);
    private static final Dimensions DIMENSIONS = new Dimensions(5, 5);
    private static final BoxCollider COLLIDER = new BoxCollider(Vector2.zero(), DIMENSIONS);
    private final String name = "Bat-" + UUID.randomUUID();
    private final CustomTimerImpl timer = new CustomTimerImpl(1000, () -> this.canAttack = true);
    private final Random random = new Random();
    private final Character character;
    private Optional<Enemy> enemy = Optional.empty();
    private int numberAttack;
    private Vector2 attackDirection;
    private boolean isAttacking;
    private boolean canAttack = true;

    private AttackFinishListenerDebug attackFinishListener;

    /**
     * <p>
     * Constructs a {@code FamiliarBat} bound to the specified character.
     * </p>
     *
     * <p>
     * This familiar:
     * </p>
     * <ul>
     * <li>Follows and assists the given {@code character}</li>
     * <li>Uses a timer to enable attacks periodically (every 1000ms)</li>
     * <li>Triggers the given {@code listener} when its attack is finished</li>
     * </ul>
     *
     * @param character the character that this FamiliarBat follows and assists
     * @param listener  the callback to invoke when the familiar finishes an attack
     */
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP2",
    justification =
        "Character reference is stored intentionally and treated as immutable in this context. "
    )
    public FamiliarBatDebug(final Character character, final AttackFinishListenerDebug listener) {
        super(character.getLevel(), character.getPosition(), VELOCITY, COLLIDER);
        this.character = character;
        this.attackFinishListener = listener;
        character.getLevel().getTimerManager().addTimer(this.name, this.timer);
        this.initDrawable(Priority.LOW, "familiar.png");
    }

    /**
     * Starts an attack in the given direction.
     * <p>
     * The number of attacks (hits) is determined randomly with weighted
     * probabilities:
     * <ul>
     * <li>1 hit - 10%</li>
     * <li>2 hits - 20%</li>
     * <li>3 hits - 40%</li>
     * <li>4 hits - 20%</li>
     * <li>5 hits - 10%</li>
     * </ul>
     *
     * @param direction the normalized direction vector for the attack
     */
    public void attack(final Vector2 direction) {
        this.isAttacking = true;
        this.attackDirection = direction;
        this.canAttack = true;

        final int rand = random.nextInt(100) + 1; // 1 - 100

        if (rand <= 10) { // 1-10 -> 10%
            this.numberAttack = 1;
        } else if (rand <= P_30) { // 11-30 -> 20%
            this.numberAttack = 2;
        } else if (rand <= P_40) { // 31-70 -> 40%
            this.numberAttack = 3;
        } else if (rand <= 90) { // 71-90 -> 20%
            this.numberAttack = 4;
        } else { // 91-100 -> 10%
            this.numberAttack = CASE_5;
        }
    }

    /**
     * Checks whether the FamiliarBat is currently idle (not attacking).
     *
     * @return {@code true} if idle; {@code false} if attacking
     */
    public boolean isIdle() {
        return !this.isAttacking;
    }

    /**
     * Checks whether the bat is close enough to the character to be considered in
     * range for attack.
     *
     * @return {@code true} if within attack range; {@code false} otherwise
     */
    public boolean isInAttackRange() {
        final Vector2 currentPos = super.getPosition();
        final Vector2 targetPos = this.character.getPosition();
        return currentPos.distance(targetPos) <= DISTANCE;
    }

    /**
     * <p>
     * Updates the bat's movement either towards the character (idle) or in the
     * attack direction (attacking).
     * </p>
     *
     * @param deltaTime time elapsed since the last update, used for smooth movement
     * @see #move(double)
     */
    @Override
    public void update(final double deltaTime) {
        this.move(deltaTime);
    }

    /**
     * <p>
     * Handles collisions with blocks and enemies.
     * Stops the attack if hitting a block, or applies damage and decrements attacks
     * if hitting an enemy.
     * </p>
     *
     * @param other     the game object collided with
     * @param direction the direction vector of the collision
     * @see #action(Enemy)
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (this.isAttacking) {
            if (other instanceof BaseCollidableBlock) {
                this.isAttacking = false;
                this.attackFinishListener.onAttackFinished(this);
            }
            if (other instanceof Enemy enemy) {
                this.enemy = Optional.of(enemy);
                if (this.numberAttack == 0) {
                    this.enemy = Optional.empty();
                    this.isAttacking = false;
                    this.attackFinishListener.onAttackFinished(this);
                } else if (this.canAttack && !enemy.isInvincible()) {
                    this.numberAttack--;
                    this.canAttack = false;
                    this.attackEffect(enemy);
                    if (this.character.getLevel().getTimerManager().searchTimer(this.name)) {
                        this.character.getLevel().getTimerManager().restartTimer(this.name);
                    } else {
                        this.character.getLevel().getTimerManager().addTimer(this.name, this.timer);
                    }
                }
            }
        }
    }

    /**
     * Applies damage to the enemy and regenerates the character's mana and life.
     * <p>
     *
     * Mana and life regeneration are 10% of the character's initial mana and full
     * life per hit, capped at maximum values.
     * </p>
     *
     * @param enemy the enemy to apply damage to
     * @see CharacterStatistics
     */
    public void attackEffect(final Enemy enemy) {
        enemy.setDamagedLife(DAMAGE);

        final var stats = (CharacterStatistics) this.character.getStats();

        final double manaIncrease = REGEN_RATE * stats.getInitialMana();
        final double lifeIncrease = REGEN_RATE * stats.getFullLife();

        stats.setMana(Math.min(stats.getMana() + manaIncrease, stats.getInitialMana()));
        stats.setLife(Math.min(stats.getLife() + lifeIncrease, stats.getFullLife()));

        this.checkIfEnemyisDead();
    }

    /**
     * Moves the FamiliarBat towards the character if not attacking,
     * or moves it in the attack direction while attacking.
     * <p>
     * When following the character, it tries to stay slightly above (5 units).
     * While attacking, it moves according to {@link #attackDirection} scaled by
     * velocity and delta time.
     * </p>
     *
     * @param deltaTime time elapsed since the last update, used to scale movement
     * @see #attack(Vector2)
     */
    public void move(final double deltaTime) {
        final Vector2 currentPos = super.getPosition();
        final Vector2 targetPos = this.character.getPosition();
        if (!this.isAttacking) {
            if (!targetPos.equals(currentPos)) {

                final double nextX;
                if (targetPos.x() - currentPos.x() > 0) {
                    nextX = Math.min(currentPos.x() + VELOCITY.x() * deltaTime, targetPos.x());
                } else {
                    nextX = Math.max(currentPos.x() - VELOCITY.x() * deltaTime, targetPos.x());
                }

                final double desiredY = targetPos.y() + OFFSET_B_TO_C;
                final double nextY;
                if (desiredY - currentPos.y() > 0) {
                    nextY = Math.min(currentPos.y() + VELOCITY.y() * deltaTime, desiredY);
                } else {
                    nextY = Math.max(currentPos.y() - VELOCITY.y() * deltaTime, desiredY);
                }

                super.setPosition(new Vector2(nextX, nextY));
            }
        } else if (this.enemy.isEmpty()) {
            final Vector2 velocity = VELOCITY.multiply(this.attackDirection).multiply(deltaTime);

            Vector2 attackPos = currentPos;

            final boolean sameRow = attackDirection.y() == 0
                    && Math.abs(currentPos.y() - targetPos.y()) <= Math.abs(OFFSET_B_TO_C);

            final boolean sameColumn = attackDirection.y() == 1
                    && Math.abs(currentPos.x() - targetPos.x()) <= Math.abs(OFFSET_B_TO_C);

            if (sameRow) {
                attackPos = new Vector2(currentPos.x(), targetPos.y());
            } else if (sameColumn) {
                final int facing = character.isFacingRight() ? 1 : -1;
                attackPos = new Vector2(
                        currentPos.x() + facing * Math.abs(OFFSET_B_TO_C),
                        currentPos.y());
            }
            super.setPosition(attackPos.add(velocity));
        } else {
            final Vector2 toEnemy = this.enemy.get().getPosition().subtract(currentPos).normalize();
            attackDirection = toEnemy;

            final Vector2 velocity = VELOCITY.multiply(this.attackDirection).multiply(deltaTime);
            final var attackPos = currentPos.add(velocity);

            if (currentPos.distance(attackPos) > currentPos.distance(this.enemy.get().getPosition())) {
                super.setPosition(this.enemy.get().getPosition());
            } else {
                super.setPosition(attackPos);
            }
            this.checkIfEnemyisDead();
        }
    }

    /**
     * Clears the attack finish listener reference.
     * Should be called when the FamiliarBat is removed.
     */
    public void clearListener() {
        this.attackFinishListener = null;
    }

    /**
     * Returns the unique name of the FamiliarBat, used for timer identification.
     *
     * @return the unique name of the bat
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the character that owns this FamiliarBat, cast as a
     * {@link DruidDebug}.
     *
     * @return the owning druid character
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Character is effectively immutable in this context")
    public DruidDebug getCharacter() {
        return (DruidDebug) this.character;
    }

    /**
     * Checks if the targeted enemy is dead.
     * If he is it will resent to idle mode.
     */
    public void checkIfEnemyisDead() {
        this.enemy.filter(Enemy::isDead).ifPresent(deadEnemy -> {
            this.enemy = Optional.empty();
            this.isAttacking = false;
            this.numberAttack = 0;
            this.attackFinishListener.onAttackFinished(this);
        });
    }

    /**
     * Returns the listener that is notified when an attack finishes.
     *
     * @return the attack finish listener
     */
    public AttackFinishListenerDebug getAttackFinishListener() {
        return this.attackFinishListener;
    }

    /**
     * Sets whether the FamiliarBat is currently attacking.
     *
     * @param isAttacking true if attacking, false otherwise
     */
    public void setAttacking(final boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    /**
     * Returns the current attack direction vector.
     *
     * @return the normalized attack direction
     */
    public Vector2 getAttackDirection() {
        return this.attackDirection;
    }

    /**
     * Returns the number of remaining attacks (hits) in the current attack
     * sequence.
     *
     * @return the number of attacks left
     */
    public int getNumberAttack() {
        return numberAttack;
    }

    /**
     * Sets the number of remaining attacks (hits) in the current attack sequence.
     *
     * @param numberAttack the number of attacks to set
     */
    public void setNumberAttack(final int numberAttack) {
        this.numberAttack = numberAttack;
    }

    /**
     * Returns the current enemy targeted by the FamiliarBat, if any.
     *
     * @return an Optional containing the targeted enemy, or empty if none
     */
    public Optional<Enemy> getEnemy() {
        return enemy;
    }

    /**
     * Returns the regeneration rate (percentage of mana and life restored per hit).
     *
     * @return the regeneration rate as a double
     */
    public static double getRegenRate() {
        return REGEN_RATE;
    }

    /**
     * Returns the amount of damage dealt per attack hit.
     *
     * @return the damage value as a double
     */
    public static double getDamage() {
        return DAMAGE;
    }
}
