package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import java.util.Optional;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
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
     * @param character     the associated {@link Character} instance
     * @param regen         optional override for the health regeneration rate. If
     *                      {@link Optional#empty()}, default is used.
     * @param senseDistance optional override for sensing distance. If
     *                      {@link Optional#empty()}, default is used.
     * @param distance      the restricted distance value for this enemy
     */
    public RestrictedBaseEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Character character,
            final Optional<Double> regen, final Optional<Double> senseDistance, final double distance) {
        super(life, attack, speed, dimension, position, noAggro, character, regen, senseDistance);
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
