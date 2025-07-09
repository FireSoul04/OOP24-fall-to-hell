package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Factory interface responsible for creating specific statistics objects
 * used by characters and enemies in the game.
 * <p>
 * This interface provides methods to create different types of statistics such
 * as {@link CharacterStatistics}, {@link BaseEnemyStatistics},
 * {@link LongRangeEnemyStatistics},
 * {@link RestrictedBaseEnemyStatistics}, and
 * {@link RestrictedLongRangeEnemyStatistics}.
 * </p>
 * <p>
 * Both {@link Optional} parameters {@code senseDistance}, {@code regen}
 * and {@code noAggro} allow overriding
 * of internal defaults. If empty, the class will apply its own standard
 * behavior.
 * </p>
 *
 * @see CharacterStatistics
 * @see BaseEnemyStatistics
 * @see LongRangeEnemyStatistics
 * @see RestrictedBaseEnemyStatistics
 * @see RestrictedLongRangeEnemyStatistics
 * @see Dimensions
 * @see Vector2
 * @author Sara Visani
 */
public interface StatisticsFactory {

        /**
         * Creates a {@link CharacterStatistics} instance with given parameters.
         * <p>
         *
         * @param life        the life points of the character
         * @param attack      the attack value
         * @param speed       the speed represented as a {@link Vector2}
         * @param dimensions  the size of the character as {@link Dimensions}
         * @param mana        the mana points of the character
         * @param attackSpeed the attack speed as a {@link Vector2}
         * @return a new instance of {@link CharacterStatistics}
         */
        CharacterStatistics createCharacterStatistic(double life, double attack, Vector2 speed,
                        Dimensions dimensions, double mana, Vector2 attackSpeed);

        /**
         * Creates a {@link BaseEnemyStatistics} instance for a base enemy type.
         * <p>
         *
         * @param life          the life points of the enemy
         * @param attack        the attack value
         * @param speed         the speed represented as a {@link Vector2}
         * @param dimension     the size of the enemy as {@link Dimensions}
         * @param position      the starting position as {@link Vector2}
         * @param noAggro       number of ticks before enemy begins regenration. If
         *                      {@link Optional#empty()}, default is used.
         * @param character     the character owning or related to this statistic
         * @param regen         optional override for regen. If
         *                      {@link Optional#empty()}, default is used.
         * @param senseDistance optional override for sensing distance. If
         *                      {@link Optional#empty()}, default is used.
         * @return a new instance of {@link BaseEnemyStatistics}
         */
        BaseEnemyStatistics createBaseEnemyStatistic(double life, double attack, Vector2 speed, Dimensions dimension,
                        Vector2 position, Optional<Integer> noAggro, Character character, Optional<Double> regen,
                        Optional<Double> senseDistance);

        /**
         * Creates a {@link LongRangeEnemyStatistics} instance for enemies with long
         * range or special attacks.
         * <p>
         *
         * @param life                 the life points of the enemy
         * @param attack               the attack value
         * @param speed                the speed represented as a {@link Vector2}
         * @param dimension            the size of the enemy as {@link Dimensions}
         * @param position             the starting position as {@link Vector2}
         * @param noAggro              number of ticks before enemy begins regenration.
         *                             If {@link Optional#empty()}, default is used.
         * @param character            the character owning or related to this statistic
         * @param regen                optional override for regen. If
         *                             {@link Optional#empty()}, default is used.
         * @param senseDistance        optional override for sensing distance. If
         *                             {@link Optional#empty()}, default is used.
         * @param projectileAttack     the damage of the projectile attack
         * @param projectileVelocity   the velocity of the projectile as {@link Vector2}
         * @param projectileDimensions the size of the projectile as {@link Dimensions}
         * @param timeAttack           ticks between each projectile attack
         * @return a new instance of {@link LongRangeEnemyStatistics}
         */
        LongRangeEnemyStatistics createLongRangeEnemyStatistic(double life, double attack,
                        Vector2 speed, Dimensions dimension, Vector2 position, Optional<Integer> noAggro,
                        Character character, Optional<Double> regen, Optional<Double> senseDistance,
                        double projectileAttack, Vector2 projectileVelocity, Dimensions projectileDimensions,
                        int timeAttack);

        /**
         * Creates a {@link RestrictedBaseEnemyStatistics} instance for enemies with
         * restricted ground movement.
         * <p>
         *
         * @param life          the life points of the enemy
         * @param attack        the attack value
         * @param speed         the speed represented as a {@link Vector2}
         * @param dimension     the size of the enemy as {@link Dimensions}
         * @param position      the starting position as {@link Vector2}
         * @param noAggro       number of ticks before enemy begins regenration. If
         *                      {@link Optional#empty()}, default is used.
         * @param character     the character owning or related to this statistic
         * @param regen         optional override for regen. If
         *                      {@link Optional#empty()}, default is used.
         * @param senseDistance optional override for sensing distance. If
         *                      {@link Optional#empty()}, default is used.
         * @param distance      the restricted movement distance
         * @return a new instance of {@link RestrictedBaseEnemyStatistics}
         */
        RestrictedBaseEnemyStatistics createGroundRestrictedEnemyStatistic(double life, double attack, Vector2 speed,
                        Dimensions dimension, Vector2 position, Optional<Integer> noAggro, Character character,
                        Optional<Double> regen, Optional<Double> senseDistance, double distance);

        /**
         * Creates a {@link RestrictedLongRangeEnemyStatistics} instance for enemies
         * with long range attacks and restricted movement.
         * <p>
         *
         * @param life                 the life points of the enemy
         * @param attack               the attack value
         * @param speed                the speed represented as a {@link Vector2}
         * @param dimension            the size of the enemy as {@link Dimensions}
         * @param position             the starting position as {@link Vector2}
         * @param noAggro              number of ticks before enemy begins regenration.
         *                             If {@link Optional#empty()}, default is used.
         * @param character            the character owning or related to this statistic
         * @param regen                optional override for regen. If
         *                             {@link Optional#empty()}, default is used.
         * @param senseDistance        optional override for sensing distance. If
         *                             {@link Optional#empty()}, default is used.
         * @param projectileAttack     the damage of the projectile attack
         * @param projectileVelocity   the velocity of the projectile as {@link Vector2}
         * @param projectileDimensions the size of the projectile as {@link Dimensions}
         * @param distance             the restricted movement distance
         * @param timeAttack           ticks between each projectile attack
         * @return a new instance of {@link RestrictedLongRangeEnemyStatistics}
         */
        RestrictedLongRangeEnemyStatistics createLongRangeRestrictedStatistic(double life, double attack,
                        Vector2 speed, Dimensions dimension, Vector2 position, Optional<Integer> noAggro,
                        Character character, Optional<Double> regen, Optional<Double> senseDistance,
                        double projectileAttack,
                        Vector2 projectileVelocity, Dimensions projectileDimensions, double distance, int timeAttack);
}
