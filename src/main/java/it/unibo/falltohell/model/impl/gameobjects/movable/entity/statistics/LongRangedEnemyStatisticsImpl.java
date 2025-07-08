package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Class implementation for long ranged or special attack enemy.
 * Extends {@link BaseEnemyStatisticsImpl} and implements
 * {@link LongRangeEnemyStatistics}.
 *
 * @author Sara Visani
 */
public class LongRangedEnemyStatisticsImpl extends BaseEnemyStatisticsImpl implements LongRangeEnemyStatistics {

    private final double projectileAttack;
    private final Vector2 projectileVelocity;
    private final Dimensions projectileDimensions;

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
     * @param noAggro              the aggro flag or parameter
     * @param character            the {@link Character} this statistics belong to
     * @param projectileAttack     the damage of the projectile attack
     * @param projectileVelocity   the velocity of the projectile as a
     *                             {@link Vector2}
     * @param projectileDimensions the dimensions of the projectile as
     *                             {@link Dimensions}
     */
    public LongRangedEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension,
            final Vector2 position, final int noAggro, final Character character, final double projectileAttack,
            final Vector2 projectileVelocity, final Dimensions projectileDimensions) {
        super(life, attack, speed, dimension, position, noAggro, character);
        this.projectileAttack = projectileAttack;
        this.projectileVelocity = projectileVelocity;
        this.projectileDimensions = projectileDimensions;
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
    public String getAttackName() {
        return "attack";
    }

}
