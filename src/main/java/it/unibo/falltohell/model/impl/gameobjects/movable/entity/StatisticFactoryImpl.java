package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.StatisticsFactory;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.BaseEnemyStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.CharacterStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.LongRangedEnemyStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.RestrictedBaseEnemyStatisticsImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.RestrictedLongRangeEnemyStatisticsImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Factory implementation for creating various types of
 * {@link CharacterStatistics} and enemy statistics.
 * Implements the {@link StatisticsFactory} interface to encapsulate the
 * instantiation logic of different statistic types.
 *
 * @author Sara Visani
 */
public class StatisticFactoryImpl implements StatisticsFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterStatistics createCharacterStatistic(final double life, final double attack, final Vector2 speed,
            final Dimensions dimensions, final double mana, final Vector2 attackSpeed) {
        return new CharacterStatisticsImpl(life, attack, speed, dimensions, mana, attackSpeed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseEnemyStatistics createBaseEnemyStatistic(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Character character,
            final Optional<Double> regen, final Optional<Double> senseDistance) {
        return new BaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen,
                senseDistance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LongRangeEnemyStatistics createLongRangeEnemyStatistic(final double life, final double attack,
            final Vector2 speed, final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Character character, final Optional<Double> regen, final Optional<Double> senseDistance,
            final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions,
            final int timeAttack) {
        return new LongRangedEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character, regen,
                senseDistance, projectileAttack, projectileVelocity, projectileDimensions, timeAttack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedBaseEnemyStatistics createGroundRestrictedEnemyStatistic(final double life, final double attack,
            final Vector2 speed, final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Character character, final Optional<Double> regen, final Optional<Double> senseDistance,
            final double distance) {
        return new RestrictedBaseEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character,
                regen, senseDistance, distance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestrictedLongRangeEnemyStatistics createLongRangeRestrictedStatistic(final double life, final double attack,
            final Vector2 speed, final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Character character, final Optional<Double> regen, final Optional<Double> senseDistance,
            final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions,
            final double distance, final int timeAttack) {
        return new RestrictedLongRangeEnemyStatisticsImpl(life, attack, speed, dimension, position, noAggro, character,
                regen, senseDistance, projectileAttack, projectileVelocity, projectileDimensions, distance, timeAttack);
    }

}
