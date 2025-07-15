package it.unibo.falltohell.model.impl.gameobjects.movable.entity.weapons;

import java.util.Random;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Enemy;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Represents a FamiliarBat weapon that follows and attacks the associated character.
 * <p>
 * The FamiliarBat moves towards the character when idle and performs attacks in a specified direction.
 * When colliding with enemies, it applies damage based on a weighted random number of hits.
 * It also regenerates the character's mana and life proportionally to the attack count.
 * </p>
 *
 * @author Sara Visani
 * @see Character
 * @see Enemy
 */
public class FamiliarBat extends MovableImpl{
    private static final double DAMAGE = 5;
    private static final Vector2 VELOCITY = new Vector2(10, 10);
    private static final Dimensions DIMENSIONS = new Dimensions(5, 5);
    private static final BoxCollider COLLIDER = new BoxCollider(Vector2.zero(), DIMENSIONS);
    final Random random = new Random();
    private Character character;
    private Vector2 attackDirection;
    private boolean isAttacking = false;

    /**
     * Constructs a FamiliarBat bound to the specified character.
     *
     * @param character the character that this FamiliarBat follows and assists
     */
    public FamiliarBat(Character character) {
        super(character.getLevel(), character.getPosition(), DIMENSIONS.width(), DIMENSIONS.height(), VELOCITY.x(), VELOCITY.y(), COLLIDER);
        this.character = character;
    }

    /**
     * Starts an attack in the given direction.
     *
     * @param direction the normalized direction vector for the attack
     */
    public void attack(final Vector2 direction){
        this.isAttacking = true;
        this.attackDirection = direction;
    }

    /**
     * Checks whether the FamiliarBat is currently attacking.
     *
     * @return {@code true} if attacking; {@code false} otherwise
     */
    public boolean canAttack(){
        return this.isAttacking;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the FamiliarBat's state, moving it either towards the character or performing an attack.
     *
     * @param deltaTime time elapsed since the last update, used for smooth movement
     * @see #move(double)
     */
    @Override
    public void update(final double deltaTime){
        this.move(deltaTime);
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the FamiliarBat is attacking and collides with a {@link Block}, the attack stops.
     * If it collides with an {@link Enemy}, it applies damage and stops the attack.
     * </p>
     *
     * @param other the game object collided with
     * @param direction the direction vector of the collision
     * @see #action(Enemy)
     */
    @Override
    public void onCollision(GameObject other, Vector2 direction) {
        if(isAttacking){
            if(other instanceof Block){
                isAttacking = false;
            }
            if(other instanceof Enemy){
                this.action((Enemy)other);
                isAttacking = false;
            }
        }
    }

    /**
     * Applies damage to the enemy and regenerates the character's mana and life.
     * <p>
     * The number of attacks (hits) is determined randomly with weighted probabilities:
     * <ul>
     *   <li>1 hit - 10%</li>
     *   <li>2 hits - 20%</li>
     *   <li>3 hits - 40%</li>
     *   <li>4 hits - 20%</li>
     *   <li>5 hits - 10%</li>
     * </ul>
     * Total damage is the base damage multiplied by the number of hits.
     * Mana and life regeneration are 10% of the character's initial mana and full life per hit, capped at maximum values.
     * </p>
     *
     * @param enemy the enemy to apply damage to
     * @see CharacterStatistics
     */
    private void action(final Enemy enemy){
        int numberAttack;
        int rand = random.nextInt(100) + 1; // 1 - 100

        if (rand <= 10) {           // 1-10 -> 10%
            numberAttack = 1;
        } else if (rand <= 30) {    // 11-30 -> 20%
            numberAttack = 2;
        } else if (rand <= 70) {    // 31-70 -> 40%
            numberAttack = 3;
        } else if (rand <= 90) {    // 71-90 -> 20%
            numberAttack = 4;
        } else {                   // 91-100 -> 10%
            numberAttack = 5;
        }

        double totalDamage = DAMAGE * numberAttack;
        enemy.setDamagedLife(totalDamage);

        var stats = (CharacterStatistics)this.character.getStats();

        double manaIncrease = 0.1 * stats.getInitialMana() * numberAttack;
        double lifeIncrease = 0.1 * stats.getFullLife() * numberAttack;

        stats.setMana(Math.min(stats.getMana() + manaIncrease, stats.getInitialMana()));
        stats.setLife(Math.min(stats.getLife() + lifeIncrease, stats.getFullLife()));

    }

    /**
     * Moves the FamiliarBat towards the character if not attacking,
     * or moves it in the attack direction while attacking.
     * <p>
     * When following the character, it tries to stay slightly above (5 units).
     * While attacking, it moves according to {@link #attackDirection} scaled by velocity and delta time.
     * </p>
     *
     * @param deltaTime time elapsed since the last update, used to scale movement
     * @see #attack(Vector2)
     */
    private void move(final double deltaTime){
        Vector2 currentPos = super.getPosition();
        Vector2 targetPos = this.character.getPosition();
        if(!this.isAttacking){
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
        }else{
            Vector2 velocity = new Vector2(
                VELOCITY.x() * attackDirection.x(),
                VELOCITY.y() * attackDirection.y()
            ).multiply(deltaTime);

            var attackPos = currentPos;
            if(Math.abs(currentPos.y() -  targetPos.y()) <= 5)
            attackPos = new Vector2(currentPos.x(), targetPos.y());

            super.setPosition(attackPos.add(velocity));
        }
    }
}
