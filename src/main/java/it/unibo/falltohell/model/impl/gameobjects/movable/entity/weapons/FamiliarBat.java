package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.weapons.AttackFinishListener;
import it.unibo.falltohell.model.impl.CustomTimerImpl;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Represents a FamiliarBat weapon that follows and attacks the associated
 * character.
 * <p>
 * The FamiliarBat moves towards the character when idle and performs attacks in
 * a specified direction.
 * When colliding with enemies, it applies damage based on a weighted random
 * number of hits.
 * It also regenerates the character's mana and life proportionally to the
 * attack count.
 * </p>
 *
 * @author Sara Visani
 * @see Character
 * @see Enemy
 */
public class FamiliarBat extends MovableImpl {
    private static final double DAMAGE = 15;
    private static final double DISTANCE = 20;
    private static final Vector2 VELOCITY = new Vector2(20, 10);
    private static final Dimensions DIMENSIONS = new Dimensions(5, 5);
    private static final BoxCollider COLLIDER = new BoxCollider(Vector2.zero(), DIMENSIONS);
    private final String name = "Bat-" + UUID.randomUUID();
    private final Random random = new Random();
    private Optional<Enemy> enemy = Optional.empty();
    private int numberAttack;
    private Character character;
    private Vector2 attackDirection;
    private boolean isAttacking = false;
    private boolean canAttack = true;
    private AttackFinishListener attackFinishListener;

    /**
     * Constructs a FamiliarBat bound to the specified character.
     *
     * @param character the character that this FamiliarBat follows and assists
     */
    public FamiliarBat(final Character character, final AttackFinishListener listener) {
        super(character.getLevel(), character.getPosition(), DIMENSIONS.width(), DIMENSIONS.height(), VELOCITY.x(),
                VELOCITY.y(), COLLIDER);
        this.character = character;
        this.attackFinishListener = listener;
        character.getLevel().getTimerManager().addTimer(this.name,
                new CustomTimerImpl(1000, () -> this.canAttack = true));
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

        int rand = random.nextInt(100) + 1; // 1 - 100

        if (rand <= 10) { // 1-10 -> 10%
            this.numberAttack = 1;
        } else if (rand <= 30) { // 11-30 -> 20%
            this.numberAttack = 2;
        } else if (rand <= 70) { // 31-70 -> 40%
            this.numberAttack = 3;
        } else if (rand <= 90) { // 71-90 -> 20%
            this.numberAttack = 4;
        } else { // 91-100 -> 10%
            this.numberAttack = 5;
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
        Vector2 currentPos = super.getPosition();
        Vector2 targetPos = this.character.getPosition();
        return currentPos.distance(targetPos) <= DISTANCE;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
    public void onCollision(GameObject other, Vector2 direction) {
        if (isAttacking) {
            if (other instanceof Block) {
                isAttacking = false;
                attackFinishListener.onAttackFinished(this);
            }
            if (other instanceof Enemy) {
                this.enemy = Optional.of((Enemy) other);
                if (this.numberAttack == 0) {
                    this.enemy = Optional.empty();
                    this.isAttacking = false;
                    attackFinishListener.onAttackFinished(this);
                } else if (this.canAttack) {
                    this.numberAttack--;
                    this.canAttack = false;
                    this.attackEffect((Enemy) other);
                    this.character.getLevel().getTimerManager().restartTimer(this.name);
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
    private void attackEffect(final Enemy enemy) {
        enemy.setDamagedLife(DAMAGE);

        var stats = (CharacterStatistics) this.character.getStats();

        double manaIncrease = 0.1 * stats.getInitialMana();
        double lifeIncrease = 0.1 * stats.getFullLife();

        stats.setMana(Math.min(stats.getMana() + manaIncrease, stats.getInitialMana()));
        stats.setLife(Math.min(stats.getLife() + lifeIncrease, stats.getFullLife()));

        if (enemy.isDead()) {
            this.enemy = Optional.empty();
            this.isAttacking = false;
            this.numberAttack = 0;
            this.attackFinishListener.onAttackFinished(this);
        }
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
    private void move(final double deltaTime) {
        Vector2 currentPos = super.getPosition();
        Vector2 targetPos = this.character.getPosition();
        if (!this.isAttacking) {
            if (!targetPos.equals(currentPos)) {

                double nextX;
                if (targetPos.x() - currentPos.x() > 0) {
                    nextX = Math.min(currentPos.x() + VELOCITY.x() * deltaTime, targetPos.x());
                } else {
                    nextX = Math.max(currentPos.x() - VELOCITY.x() * deltaTime, targetPos.x());
                }

                double desiredY = targetPos.y() + 5;
                double nextY;
                if (desiredY - currentPos.y() > 0) {
                    nextY = Math.min(currentPos.y() + VELOCITY.y() * deltaTime, desiredY);
                } else {
                    nextY = Math.max(currentPos.y() - VELOCITY.y() * deltaTime, desiredY);
                }

                super.setPosition(new Vector2(nextX, nextY));
            }
        } else if (this.enemy.isEmpty()) {
            Vector2 velocity = new Vector2(
                    VELOCITY.x() * attackDirection.x(),
                    VELOCITY.y() * attackDirection.y()).multiply(deltaTime);

            var attackPos = currentPos;
            if (Math.abs(currentPos.y() - targetPos.y()) <= 5)
                attackPos = new Vector2(currentPos.x(), targetPos.y());

            super.setPosition(attackPos.add(velocity));
        } else {
            Vector2 toEnemy = this.enemy.get().getPosition().subtract(currentPos).normalize();
            attackDirection = toEnemy;

            Vector2 velocity = new Vector2(
                    VELOCITY.x() * attackDirection.x(),
                    VELOCITY.y() * attackDirection.y()).multiply(deltaTime);
            var attackPos = currentPos.add(velocity);

            if (currentPos.distance(attackPos) > currentPos.distance(this.enemy.get().getPosition())) {
                super.setPosition(this.enemy.get().getPosition());
            } else {
                super.setPosition(attackPos);
            }
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
     * Returns the character that owns this FamiliarBat, cast as a {@link Druid}.
     *
     * @return the owning druid character
     */
    public Druid getCharacter() {
        return (Druid) this.character;
    }
}
