package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.RestrictedBaseEnemyStatistics;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

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
     * @param life      the life points of the character
     * @param attack    the attack value
     * @param speed     the speed as a {@link Vector2} object
     * @param dimension the size dimensions of the character as a {@link Dimensions}
     *                  object
     * @param position  the position as a {@link Vector2} object
     * @param noAggro   the no-aggro timer or state
     * @param character the associated {@link Character} instance
     * @param distance  the restricted distance value for this enemy
     */
    public RestrictedBaseEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension,
            final Vector2 position, final int noAggro, final Character character, final double distance) {
        super(life, attack, speed, dimension, position, noAggro, character);
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
