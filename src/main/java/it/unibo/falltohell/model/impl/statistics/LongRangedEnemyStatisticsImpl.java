package it.unibo.falltohell.model.impl.statistics;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class implementation for long ranged or special attack enemy.
 * Extends {@link BaseEnemyStatisticsImpl} and implements
 * {@link LongRangeEnemyStatistics}.
 *
 * @author Sara Visani
 */
public class LongRangedEnemyStatisticsImpl extends BaseEnemyStatisticsImpl implements LongRangeEnemyStatistics {

    private static final double MULTIPLIER = 0.5;
    private final double projectileAttack;
    private final Vector2 projectileVelocity;
    private final Dimensions projectileDimensions;
    private final int timeAttack;

    /**
     * Creates new statistics with the parameters specified.
     * <p>
     *
     * @param life                 the life points of the enemy
     * @param attack               the attack damage value
     * @param speed                the movement speed as a {@link Vector2} vector
     * @param dimension            the size/dimensions of the enemy as
     *                             {@link Dimensions}
     * @param position             the initial position as a {@link Vector2}
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
     * @param projectileAttack     the damage of the projectile attack
     * @param projectileVelocity   the velocity of the projectile as a
     *                             {@link Vector2}
     * @param projectileDimensions the dimensions of the projectile as
     *                             {@link Dimensions}
     * @param timeAttack           the cooldown time between attacks in ticks (or
     *                             milliseconds)
     * @param buff                 optional map of possible buffs dropped by this
     *                             enemy, with probabilities
     */
    public LongRangedEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Optional<Double> regen, final Optional<Double> senseDistance,
            final long points, final double projectileAttack, final Vector2 projectileVelocity,
            final Dimensions projectileDimensions, final int timeAttack, final Optional<Map<BuffNames, Double>> buff) {
        super(life, attack, speed, dimension, position, noAggro, regen, senseDistance, points, buff);
        this.projectileAttack = projectileAttack;
        this.projectileVelocity = projectileVelocity;
        this.projectileDimensions = projectileDimensions;
        this.timeAttack = timeAttack;
        super.setMultiplier(MULTIPLIER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProjectileAttack() {
        return this.projectileAttack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getProjectileSpeed() {
        return this.projectileVelocity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimensions getProjectileDimensions() {
        return this.projectileDimensions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeAttack() {
        return this.timeAttack;
    }

}
