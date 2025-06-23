package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Class implementations for Long ranged or special attack enemy
 *
 * @author Sara Visani
 */
public class LongRangedEnemyStatisticsImpl extends BaseEnemyStatisticsImpl implements LongRangeEnemyStatistics{

    final double projectileAttack;
    final Vector2 projectileVelocity;
    final Dimensions projectileDimensions;

    /**
     * Create new statistics with the parameters specified.
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
     */
    public LongRangedEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimension,
            final Vector2 position,final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions) {
        super(life, attack, speed, dimension, position, noAggro, character);
        this.projectileAttack = projectileAttack;
        this.projectileVelocity = projectileVelocity;
        this.projectileDimensions = projectileDimensions;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public double getProjectileAttack() {
        return this.projectileAttack;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Vector2 getProjectileSpeed() {
        return this.projectileVelocity;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public Dimensions getProjectileDimensions() {
        return this.projectileDimensions;
    }

    /*
     * {@inheritDoc}
     */
    @Override
    public String getAttackName() {
        return "attack";
    }
    
}
