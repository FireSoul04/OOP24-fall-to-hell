package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedLongRangeEnemyStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Concrete implementation of {@link RestrictedLongRangeEnemyStatistics} extending {@link LongRangedEnemyStatisticsImpl}.
 * Represents statistics for a restricted long range enemy with a limited engagement distance.
 * @author Sara Visani
 */
public class RestrictedLongRangeEnemyStatisticsImpl extends LongRangedEnemyStatisticsImpl implements RestrictedLongRangeEnemyStatistics{

    final double distance;

    /**
     *Constructs a new {@code RestrictedLongRangeEnemyStatisticsImpl} with the specified parameters.
     * <p>
     * @param life the amount of life points
     * @param attack the attack power
     * @param speed the movement speed as a {@link Vector2}
     * @param dimension the dimensions of the enemy as a {@link Dimensions}
     * @param position the starting position as a {@link Vector2}
     * @param noAggro the no-aggro delay or count (game-specific meaning)
     * @param character the associated {@link Character} entity
     * @param projectileAttack the damage dealt by projectiles
     * @param projectileVelocity the velocity of projectiles as a {@link Vector2}
     * @param projectileDimensions the dimensions of the projectiles as a {@link Dimensions}
     * @param distance the maximum distance the enemy can engage (aggro range)
     */
    public RestrictedLongRangeEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed, final Dimensions dimension, final Vector2 position, final int noAggro, final Character character, final double projectileAttack, final Vector2 projectileVelocity, final Dimensions projectileDimensions, final double distance){
        super(life, attack, speed, dimension, position, noAggro, character, projectileAttack, projectileVelocity, projectileDimensions);
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
