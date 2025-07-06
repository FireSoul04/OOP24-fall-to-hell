package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.*;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface to call the creations of specific statistic
 *
 * @author Sara Visani
 */
public interface StatisticsFactory {
    
    /**
     * @param life
     * @param attack
     * @param speed
     * @param dimensions
     * @param mana
     * @param attackSpeed
     * @return the character statistic
     */
    public CharacterStatistics createCharacterStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimensions, final double mana, final Vector2 attackSpeed);

    /**
     * @param life
     * @param attack
     * @param speed
     * @param dimension
     * @param position
     * @param noAggro
     * @param character
     * @return base enemy statistic
     */
    public BaseEnemyStatistics createBaseEnemyStatistic(double life, double attack, Vector2 speed, Dimensions dimension, final Vector2 position, final int noAggro, final Character character);

    /**
     * @param life
     * @param attack
     * @param speed
     * @param dimension
     * @param position
     * @param noAggro
     * @param character
     * @param projectileAttack
     * @param projectileVelocity
     * @param projectileDimensions
     * @return special or long range attack enemy
     */
    public LongRangeEnemyStatistics createLongRangeEnemyStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimension, final Vector2 position,final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions);

    /**
     * @param life
     * @param attack
     * @param speed
     * @param dimension
     * @param position
     * @param noAggro
     * @param character
     * @param distance
     * @return base enemy with restricted movement statistic
     */
    public RestrictedBaseEnemyStatistics createGroundRestrictedEnemyStatistic(double life, double attack, Vector2 speed, Dimensions dimension, final Vector2 position, final int noAggro, final Character character, final double distance);

    /**
     * @param life
     * @param attack
     * @param speed
     * @param dimension
     * @param position
     * @param noAggro
     * @param character
     * @param projectileAttack
     * @param projectileVelocity
     * @param projectileDimensions
     * @param distance
     * @return special or long range attack enemy with restricted movement statistic
     */
    public RestrictedLongRangeEnemyStatistics createLongRangeRestrictedStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimension, final Vector2 position,final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions, final double distance);
}
