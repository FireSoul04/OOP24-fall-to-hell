package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.*;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Factory interface responsible for creating specific statistics objects
 * used by characters and enemies in the game.
 * <p>
 * This interface provides methods to create different types of statistics such as 
 * {@link CharacterStatistics}, {@link BaseEnemyStatistics}, and others with
 * specific properties.
 * </p>
 *
 * @author Sara Visani
 */
public interface StatisticsFactory {
    
    /**
     * Creates a {@link CharacterStatistics} instance with given parameters.
     * <p>
     * @param life the life points of the character
     * @param attack the attack value
     * @param speed the speed represented as a {@link Vector2}
     * @param dimensions the size of the character as {@link Dimensions}
     * @param mana the mana points of the character
     * @param attackSpeed the attack speed as a {@link Vector2}
     * @return a new instance of {@link CharacterStatistics}
     */
    public CharacterStatistics createCharacterStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimensions, final double mana, final Vector2 attackSpeed);

    /**
     * Creates a {@link BaseEnemyStatistics} instance for a base enemy type.
     * <p>
     * @param life the life points of the enemy
     * @param attack the attack value
     * @param speed the speed represented as a {@link Vector2}
     * @param dimension the size of the enemy as {@link Dimensions}
     * @param position the starting position as {@link Vector2}
     * @param noAggro number of ticks before the enemy becomes aggressive
     * @param character the character owning or related to this statistic
     * @return a new instance of {@link BaseEnemyStatistics}
     */
    public BaseEnemyStatistics createBaseEnemyStatistic(double life, double attack, Vector2 speed, Dimensions dimension, final Vector2 position, final int noAggro, final Character character);

    /**
     * Creates a {@link LongRangeEnemyStatistics} instance for enemies with long range or special attacks.
     * <p>
     * @param life the life points of the enemy
     * @param attack the attack value
     * @param speed the speed represented as a {@link Vector2}
     * @param dimension the size of the enemy as {@link Dimensions}
     * @param position the starting position as {@link Vector2}
     * @param noAggro number of ticks before the enemy becomes aggressive
     * @param character the character owning or related to this statistic
     * @param projectileAttack the damage of the projectile attack
     * @param projectileVelocity the velocity of the projectile as {@link Vector2}
     * @param projectileDimensions the size of the projectile as {@link Dimensions}
     * @return a new instance of {@link LongRangeEnemyStatistics}
     */
    public LongRangeEnemyStatistics createLongRangeEnemyStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimension, final Vector2 position,final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions);

    /**
     * Creates a {@link RestrictedBaseEnemyStatistics} instance for enemies with restricted ground movement.
     * <p>
     * @param life the life points of the enemy
     * @param attack the attack value
     * @param speed the speed represented as a {@link Vector2}
     * @param dimension the size of the enemy as {@link Dimensions}
     * @param position the starting position as {@link Vector2}
     * @param noAggro number of ticks before the enemy becomes aggressive
     * @param character the character owning or related to this statistic
     * @param distance the restricted movement distance
     * @return a new instance of {@link RestrictedBaseEnemyStatistics}
     */
    public RestrictedBaseEnemyStatistics createGroundRestrictedEnemyStatistic(double life, double attack, Vector2 speed, Dimensions dimension, final Vector2 position, final int noAggro, final Character character, final double distance);

    /**
     * Creates a {@link RestrictedLongRangeEnemyStatistics} instance for enemies with long range attacks and restricted movement.
     * <p>
     * @param life the life points of the enemy
     * @param attack the attack value
     * @param speed the speed represented as a {@link Vector2}
     * @param dimension the size of the enemy as {@link Dimensions}
     * @param position the starting position as {@link Vector2}
     * @param noAggro number of ticks before the enemy becomes aggressive
     * @param character the character owning or related to this statistic
     * @param projectileAttack the damage of the projectile attack
     * @param projectileVelocity the velocity of the projectile as {@link Vector2}
     * @param projectileDimensions the size of the projectile as {@link Dimensions}
     * @param distance the restricted movement distance
     * @return a new instance of {@link RestrictedLongRangeEnemyStatistics}
     */
    public RestrictedLongRangeEnemyStatistics createLongRangeRestrictedStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimension, final Vector2 position,final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions, final double distance);
}
