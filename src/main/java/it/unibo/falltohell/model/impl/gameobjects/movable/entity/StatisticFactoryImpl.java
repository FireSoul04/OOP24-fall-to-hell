package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.StatisticsFactory;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.BaseEnemyStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.CharacterStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.LongRangedEnemyStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.RestrictedEnemyStatisticsImpl;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Class implementations for factory of statistics
 * @author Sara Visani
 */
public class StatisticFactoryImpl implements StatisticsFactory{

    /*
     * {@inheritDoc}
     */
    @Override
    public Statistics createCharacterStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimensions,
            final double mana, final Vector2 attackSpeed) {
        return new CharacterStatisticsImpl(life, attack, speed, dimensions, mana, attackSpeed);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Statistics createBaseEnemyStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimension,
            final Vector2 position, final int noAggro, final Character character) {
        return new BaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Statistics createLongRangeEnemyStatistic(final double life, final double attack, final Vector2 speed, final Dimensions dimension,
            final Vector2 position, final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity,
            final Dimensions projectileDimensions) {
        return new LongRangedEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, projectileAttack, projectileVelocity, projectileDimensions);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Statistics createGroundRestrictedEnemyStatistic(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final int noAggro, final Character character, final double distance) {
        return (Statistics)new RestrictedEnemyStatisticsImpl(this.createBaseEnemyStatistic(life, attack, speed, dimension, position, noAggro, character), distance);
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Statistics createLongRangeRestrictedStatistic(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final int noAggro, final Character character, final double projectileAttack,
            final Vector2 projectileVelocity, final Dimensions projectileDimensions, final double distance) {
        return (Statistics)new RestrictedEnemyStatisticsImpl(this.createLongRangeEnemyStatistic(life, attack, speed, dimension, position, noAggro, character, projectileAttack, projectileVelocity, projectileDimensions), distance);
    }
    
}
