package it.unibo.falltohell.model.impl.statistics;

import java.util.Map;
import java.util.Optional;

import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of {@link BaseEnemyStatistics} containing all statistics
 * of an enemy that attacks physically without restrictions.
 * <p>
 * Stores data such as initial position, total life, aggro state, and
 * regeneration rate.
 * Includes reference to the {@link Character} it belongs to and a
 * {@link TimerManager}
 * to manage time-based effects.
 *
 * @author Sara Visani
 */
public class BaseEnemyStatisticsImpl extends StatisticsImpl implements BaseEnemyStatistics {

    private static final int MIN_NO_AGGRO = 5000;
    private static final double STANDARD_MULTIPLIER = 0.3;
    private static final double MIN_R = 0.05;
    private static final double MAX_R = 0.9;
    private static final double STANDARD_SENSE = 10 * 20;
    private static final double STANDARD_REGEN = 0.1;
    private static final int STANDARD_NO_AGGRO = 8500;
    private static final Map<BuffNames, Double> BUFF = Map.of(
            BuffNames.LIFE, 12.0,
            BuffNames.MANA, 15.0,
            BuffNames.ATTACK_SPEED, 9.0,
            BuffNames.ATTACK, 3.0,
            BuffNames.SPEED, 6.0);

    private final Vector2 initialPosition;
    private final int noAggro;
    private final double regen;
    private final double senseDistance;
    private final long points;
    private final Map<BuffNames, Double> buff;
    private double multiplier = STANDARD_MULTIPLIER;

    /**
     * Constructs new enemy statistics with the specified parameters.
     * <p>
     *
     * @param life          the total life points of the enemy
     * @param attack        the attack power
     * @param speed         the movement speed as a {@link Vector2}
     * @param dimension     the physical dimensions of the enemy {@link Dimensions}
     * @param position      the initial position as a {@link Vector2}
     * @param noAggro       optional override for an integer representing the aggro
     *                      state (no aggro). If {@link Optional#empty()}, default
     *                      is used.
     * @param regen         optional override for the health regeneration rate. If
     *                      {@link Optional#empty()}, default is used.
     * @param senseDistance optional override for sensing distance. If
     *                      {@link Optional#empty()}, default is used.
     * @param points        the amount of points awarded when the enemy is defeated
     * @param buff          optional map of possible buffs dropped by this enemy,
     *                      with probabilities
     */
    public BaseEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Optional<Double> regen, final Optional<Double> senseDistance,
            final long points, final Optional<Map<BuffNames, Double>> buff) {
        super(life, attack, speed, dimension);
        this.initialPosition = position;
        this.noAggro = noAggro.filter(a -> a > MIN_NO_AGGRO).orElse(STANDARD_NO_AGGRO);
        this.regen = regen.filter(r -> r >= MIN_R && r <= MAX_R).orElse(STANDARD_REGEN);
        this.senseDistance = senseDistance.filter(d -> d > 0).orElse(STANDARD_SENSE);
        this.points = points;
        this.buff = buff.orElse(BUFF);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getInitialPos() {
        return this.initialPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNoAggro() {
        return this.noAggro;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getRegen() {
        return this.regen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSenseDistance() {
        return this.senseDistance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getPoints() {
        return this.points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<BuffNames, Double> getBuffMap() {
        return Map.copyOf(this.buff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMultiplier() {
        return this.multiplier;
    }

    /**
     * <p>
     * Sets the internal multiplier value used for damage, speed, or other scaling
     * calculations.
     * </p>
     *
     * @param multiplier the new multiplier to apply; typically a value greater than
     *                   0
     */
    protected void setMultiplier(final double multiplier) {
        this.multiplier = multiplier;
    }
}
