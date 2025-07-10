package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.StatisticsFactory;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder.ParamBuilderOptional;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder.CharacterStatBuilder;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder.GroundEnemyStatBuilderImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder.LongRangeStatBuilderImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder.RestrictedGrEnStatImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.builder.RestrictedLongRangeBuilderImpl;
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
                        final Dimensions dimensions, final double mana, final double attackSpeed) {
                return new CharacterStatBuilder()
                                                .withLife(life)
                                                .withAttack(attack)
                                                .withSpeed(speed)
                                                .withDimensions(dimensions)
                                                .withMana(mana)
                                                .withAttackSpeed(attackSpeed)
                                                .build();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BaseEnemyStatistics createBaseEnemyStatistic(final double life, final double attack, final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final Character character,
                        final long points, final ParamBuilderOptional optionalParams) {
                GroundEnemyStatBuilderImpl<?> builder = new GroundEnemyStatBuilderImpl<>()
                        .withLife(life)
                        .withAttack(attack)
                        .withSpeed(speed)
                        .withDimensions(dimension)
                        .withPosition(position)
                        .withCharacter(character)
                        .withPoints(points);

                if (optionalParams != null) {
                        optionalParams.getNoAggro().ifPresent(builder::withNoAggro);
                        optionalParams.getRegen().ifPresent(builder::withRegen);
                        optionalParams.getSenseDistance().ifPresent(builder::withSenseDistance);
                }

                return builder.build();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LongRangeEnemyStatistics createLongRangeEnemyStatistic(final double life, final double attack, final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final Character character,
                        final long points, final ParamBuilderOptional optionalParams, final double projectileAttack,
                        final Vector2 projectileVelocity, final Dimensions projectileDimensions, final int timeAttack) {
                LongRangeStatBuilderImpl<?> builder = new LongRangeStatBuilderImpl<>()
                        .withLife(life)
                        .withAttack(attack)
                        .withSpeed(speed)
                        .withDimensions(dimension)
                        .withPosition(position)
                        .withCharacter(character)
                        .withPoints(points)
                        .withProjectileAttack(projectileAttack)
                        .withProjectileVelocity(projectileVelocity)
                        .withProjectileDimensions(projectileDimensions)
                        .withTimeAttack(timeAttack);

                if (optionalParams != null) {
                        optionalParams.getNoAggro().ifPresent(builder::withNoAggro);
                        optionalParams.getRegen().ifPresent(builder::withRegen);
                        optionalParams.getSenseDistance().ifPresent(builder::withSenseDistance);
                }

                return builder.build();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public RestrictedBaseEnemyStatistics createGroundRestrictedEnemyStatistic(final double life, final double attack, final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final Character character,
                        final long points, final ParamBuilderOptional optionalParams, final double distance) {
                RestrictedGrEnStatImpl builder = new RestrictedGrEnStatImpl()
                        .withLife(life)
                        .withAttack(attack)
                        .withSpeed(speed)
                        .withDimensions(dimension)
                        .withPosition(position)
                        .withCharacter(character)
                        .withPoints(points);

                if (optionalParams != null) {
                        optionalParams.getNoAggro().ifPresent(builder::withNoAggro);
                        optionalParams.getRegen().ifPresent(builder::withRegen);
                        optionalParams.getSenseDistance().ifPresent(builder::withSenseDistance);
                }

                return builder.build();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public RestrictedLongRangeEnemyStatistics createLongRangeRestrictedStatistic(final double life, final double attack, final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final Character character,
                        final long points, final ParamBuilderOptional optionalParams, final double projectileAttack,
                        final Vector2 projectileVelocity, final Dimensions projectileDimensions, final double distance,
                        final int timeAttack) {
                RestrictedLongRangeBuilderImpl builder = new RestrictedLongRangeBuilderImpl()
                        .withLife(life)
                        .withAttack(attack)
                        .withSpeed(speed)
                        .withDimensions(dimension)
                        .withPosition(position)
                        .withCharacter(character)
                        .withPoints(points)
                        .withProjectileAttack(projectileAttack)
                        .withProjectileVelocity(projectileVelocity)
                        .withProjectileDimensions(projectileDimensions)
                        .withDistance(distance)
                        .withTimeAttack(timeAttack);

                if (optionalParams != null) {
                        optionalParams.getNoAggro().ifPresent(builder::withNoAggro);
                        optionalParams.getRegen().ifPresent(builder::withRegen);
                        optionalParams.getSenseDistance().ifPresent(builder::withSenseDistance);
                }

                return builder.build();
        }

}
