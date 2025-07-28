package it.unibo.falltohell.model.impl.statistics;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of {@link RestrictedBaseEnemyStatistics} extending
 * {@link BaseEnemyStatisticsImpl}.
 * Adds a restricted distance property for enemy statistics.
 *
 * @author Sara Visani
 */
public class RestrictedBaseEnemyStatisticsImpl extends BaseEnemyStatisticsImpl
        implements RestrictedBaseEnemyStatistics {

    private final double distance;

    /**
     * Constructs a new {@code RestrictedBaseEnemyStatisticsImpl} instance.
     * <p>
     *
     * @param life          the life points of the character
     * @param attack        the attack value
     * @param speed         the speed as a {@link Vector2} object
     * @param dimension     the size dimensions of the character as a
     *                      {@link Dimensions}
     *                      object
     * @param position      the position as a {@link Vector2} object
     * @param noAggro       optional override for an integer representing the aggro
     *                      state (no aggro). If {@link Optional#empty()}, default
     *                      is used.
     * @param regen         optional override for the health regeneration rate. If
     *                      {@link Optional#empty()}, default is used.
     * @param senseDistance optional override for sensing distance. If
     *                      {@link Optional#empty()}, default is used.
     * @param points        the amount of points awarded when the enemy is defeated
     * @param distance      the restricted distance value for this enemy
     * @param buff          optional map of possible buffs dropped by this enemy,
     *                      with probabilities
     */
    public RestrictedBaseEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Optional<Double> regen, final Optional<Double> senseDistance,
            final long points, final double distance, final Optional<Map<BuffNames, Double>> buff) {
        super(life, attack, speed, dimension, position, noAggro, regen, senseDistance, points, buff);
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
