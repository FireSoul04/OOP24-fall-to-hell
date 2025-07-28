package it.unibo.falltohell.model.impl.factory;

import it.unibo.falltohell.model.api.factory.StatisticsFactory;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.api.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.builder.ParamBuilderOptional;
import it.unibo.falltohell.model.impl.builder.CharacterStatBuilder;
import it.unibo.falltohell.model.impl.builder.GroundEnemyStatBuilderImpl;
import it.unibo.falltohell.model.impl.builder.LongRangeStatBuilderImpl;
import it.unibo.falltohell.model.impl.builder.ParamBuilderOptionalImpl;
import it.unibo.falltohell.model.impl.builder.RestrictedGrEnStatImpl;
import it.unibo.falltohell.model.impl.builder.RestrictedLongRangeBuilderImpl;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of the {@link StatisticsFactory} interface that acts as a
 * **hybrid between a Factory and a Fluent Builder**.
 * <p>
 * This class encapsulates the logic for creating complex types of
 * {@link CharacterStatistics} and enemy statistics
 * (including ranged, restricted, and optional configurations) by **delegating
 * the instantiation to fluent-style builders**.
 * </p>
 *
 * <h2>Pattern Explanation:</h2>
 * <ul>
 * <li><b>Factory Pattern:</b> The public methods follow the factory pattern by
 * hiding the concrete instantiation logic
 * and exposing simple creation interfaces, promoting encapsulation and
 * consistency.</li>
 * <li><b>Fluent Builder Pattern:</b> Internally, it uses builders with method
 * chaining to configure complex objects step-by-step
 * in a readable and flexible way, especially when optional or context-specific
 * parameters are involved.</li>
 * </ul>
 *
 * <h2>Handling of Optional Parameters</h2>
 * <p>
 * Many of the creation methods accept an instance of
 * {@link ParamBuilderOptional}, which encapsulates
 * optional configuration parameters wrapped as {@link java.util.Optional}
 * values.
 * This design allows the client code to optionally specify parameters such as:
 * </p>
 * <ul>
 * <li><code>noAggro</code> - whether the enemy ignores aggression triggers</li>
 * <li><code>regen</code> - health regeneration rate</li>
 * <li><code>senseDistance</code> - detection or sense distance for AI</li>
 * </ul>
 * <p>
 * When building the statistics objects, this factory:
 * <ol>
 * <li>Checks if the {@code optionalParams} argument is non-null.</li>
 * <li>For each optional parameter inside {@code optionalParams}, it calls
 * {@link java.util.Optional#ifPresent}
 * with the corresponding builder setter method reference.</li>
 * <li>If an optional value is present, it configures the builder accordingly;
 * otherwise, it leaves the default value.</li>
 * </ol>
 * </p>
 *
 * <p>
 * The separation between the construction logic (via builders like
 * {@link CharacterStatBuilder} and
 * {@link LongRangeStatBuilderImpl}) and the instantiation interface (this
 * class) makes the design extensible and maintainable,
 * while still offering a streamlined usage to the clients of the factory.
 * </p>
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * StatisticsFactory factory = new StatisticFactoryImpl();
 * BaseEnemyStatistics groundEnemyStats = factory.createBaseEnemyStatistic(
 *                 100.0, // life
 *                 15.0, // attack
 *                 new Vector2(0.5, 0), // speed
 *                 new Dimensions(1.0, 2.0), // dimensions
 *                 new Vector2(10, 5), // position
 *                 playerCharacter, // target character
 *                 200, // points
 *                 factory.createOptional().withRegen(0.1) // optional parameters (e.g. regen, noAggro)
 * );
 * }</pre>
 *
 * @author Sara Visani
 * @see StatisticsFactory
 * @see CharacterStatistics
 * @see CharacterStatBuilder
 * @see GroundEnemyStatBuilderImpl
 * @see LongRangeStatBuilderImpl
 * @see RestrictedGrEnStatImpl
 * @see RestrictedLongRangeBuilderImpl
 * @see ParamBuilderOptional
 */
public class StatisticFactoryImpl implements StatisticsFactory {

        /**
         * {@inheritDoc}
         *
         * @see CharacterStatBuilder
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
         *
         * @see GroundEnemyStatBuilderImpl
         */
        @Override
        public BaseEnemyStatistics createBaseEnemyStatistic(final double life, final double attack, final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final long points, final ParamBuilderOptional optionalParams) {
                final GroundEnemyStatBuilderImpl<?> builder = new GroundEnemyStatBuilderImpl<>()
                                .withLife(life)
                                .withAttack(attack)
                                .withSpeed(speed)
                                .withDimensions(dimension)
                                .withPosition(position)
                                .withPoints(points);

                if (optionalParams != null) {
                        optionalParams.getNoAggro().ifPresent(builder::withNoAggro);
                        optionalParams.getRegen().ifPresent(builder::withRegen);
                        optionalParams.getSenseDistance().ifPresent(builder::withSenseDistance);
                        optionalParams.getBuffMap().ifPresent(builder::withBuff);
                }

                return builder.build();
        }

        /**
         * {@inheritDoc}
         *
         * @see LongRangeStatBuilderImpl
         */
        @Override
        public LongRangeEnemyStatistics createLongRangeEnemyStatistic(final double life, final double attack,
                        final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final long points, final ParamBuilderOptional optionalParams, final double projectileAttack,
                        final Vector2 projectileVelocity, final Dimensions projectileDimensions, final int timeAttack) {
                final LongRangeStatBuilderImpl<?> builder = new LongRangeStatBuilderImpl<>()
                                .withLife(life)
                                .withAttack(attack)
                                .withSpeed(speed)
                                .withDimensions(dimension)
                                .withPosition(position)
                                .withPoints(points)
                                .withProjectileAttack(projectileAttack)
                                .withProjectileVelocity(projectileVelocity)
                                .withProjectileDimensions(projectileDimensions)
                                .withTimeAttack(timeAttack);

                if (optionalParams != null) {
                        optionalParams.getNoAggro().ifPresent(builder::withNoAggro);
                        optionalParams.getRegen().ifPresent(builder::withRegen);
                        optionalParams.getSenseDistance().ifPresent(builder::withSenseDistance);
                        optionalParams.getBuffMap().ifPresent(builder::withBuff);
                }

                return builder.build();
        }

        /**
         * {@inheritDoc}
         *
         * @see RestrictedGrEnStatImpl
         */
        @Override
        public RestrictedBaseEnemyStatistics createGroundRestrictedEnemyStatistic(final double life,
                        final double attack, final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final long points, final ParamBuilderOptional optionalParams, final double distance) {
                final RestrictedGrEnStatImpl builder = new RestrictedGrEnStatImpl()
                                .withLife(life)
                                .withAttack(attack)
                                .withSpeed(speed)
                                .withDimensions(dimension)
                                .withPosition(position)
                                .withPoints(points);

                if (optionalParams != null) {
                        optionalParams.getNoAggro().ifPresent(builder::withNoAggro);
                        optionalParams.getRegen().ifPresent(builder::withRegen);
                        optionalParams.getSenseDistance().ifPresent(builder::withSenseDistance);
                        optionalParams.getBuffMap().ifPresent(builder::withBuff);
                }

                return builder.build();
        }

        /**
         * {@inheritDoc}
         *
         * @see RestrictedLongRangeBuilderImpl
         */
        @Override
        public RestrictedLongRangeEnemyStatistics createLongRangeRestrictedStatistic(final double life,
                        final double attack, final Vector2 speed,
                        final Dimensions dimension, final Vector2 position,
                        final long points, final ParamBuilderOptional optionalParams, final double projectileAttack,
                        final Vector2 projectileVelocity, final Dimensions projectileDimensions, final double distance,
                        final int timeAttack) {
                final RestrictedLongRangeBuilderImpl builder = new RestrictedLongRangeBuilderImpl()
                                .withLife(life)
                                .withAttack(attack)
                                .withSpeed(speed)
                                .withDimensions(dimension)
                                .withPosition(position)
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
                        optionalParams.getBuffMap().ifPresent(builder::withBuff);
                }

                return builder.build();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ParamBuilderOptional createOptional() {
                return new ParamBuilderOptionalImpl();
        }

}
