package it.unibo.falltohell.model.impl.statistics;

import it.unibo.falltohell.model.api.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Concrete implementation of {@link RestrictedLongRangeEnemyStatistics}
 * extending {@link LongRangedEnemyStatisticsImpl}.
 * Represents statistics for a restricted long range enemy with a limited
 * engagement distance.
 *
 * @author Sara Visani
 */
public class RestrictedLongRangeEnemyStatisticsImpl extends LongRangedEnemyStatisticsImpl
        implements RestrictedLongRangeEnemyStatistics {

    private final double distance;

    /**
     * Constructs a new {@code RestrictedLongRangeEnemyStatisticsImpl} with the
     * specified parameters.
     * <p>
     *
     * @param life                 the amount of life points
     * @param attack               the attack power
     * @param speed                the movement speed as a {@link Vector2}
     * @param dimension            the dimensions of the enemy as a
     *                             {@link Dimensions}
     * @param position             the starting position as a {@link Vector2}
     * @param noAggro              optional override for an integer representing the
     *                             aggro state (no aggro). If
     *                             {@link Optional#empty()}, default is used.
     * @param regen                optional override for the health regeneration
     *                             rate. If {@link Optional#empty()}, default is
     *                             used.
     * @param senseDistance        optional override for sensing distance. If
     *                             {@link Optional#empty()}, default is used.
     * @param points               the amount of points awarded when the enemy is
     *                             defeated
     * @param projectileAttack     the damage dealt by projectiles
     * @param projectileVelocity   the velocity of projectiles as a {@link Vector2}
     * @param projectileDimensions the dimensions of the projectiles as a
     *                             {@link Dimensions}
     * @param distance             the maximum distance the enemy can engage (aggro
     *                             range)
     * @param timeAttack           the cooldown time between attacks in ticks (or
     *                             milliseconds)
     * @param buff                 optional map of possible buffs dropped by this
     *                             enemy, with probabilities
     */
    public RestrictedLongRangeEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Optional<Double> regen, final Optional<Double> senseDistance,
            final long points, final double projectileAttack, final Vector2 projectileVelocity,
            final Dimensions projectileDimensions, final double distance, final int timeAttack,
            final Optional<Map<BuffNames, Double>> buff) {
        super(life, attack, speed, dimension, position, noAggro, regen, senseDistance, points,
                projectileAttack, projectileVelocity, projectileDimensions, timeAttack, buff);
        this.distance = distance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDistance() {
        return this.distance;
    }
}
