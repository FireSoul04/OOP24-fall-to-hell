package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import java.util.Optional;

import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticsImpl;
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

    static private double STANDARD_SENSE = 100;
    static private double STANDARD_REGEN = 0.1;
    static private int STANDARD_NO_AGGRO = 1000;

    private final Vector2 initialPosition;
    private final int noAggro;
    private final double regen;
    private final double senseDistance;
    private final long points;
    private Character character;

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
     * @param character     the associated {@link Character} instance
     * @param regen         optional override for the health regeneration rate. If
     *                      {@link Optional#empty()}, default is used.
     * @param senseDistance optional override for sensing distance. If
     *                      {@link Optional#empty()}, default is used.
     * @param points TODO
     */
    public BaseEnemyStatisticsImpl(final double life, final double attack, final Vector2 speed,
            final Dimensions dimension, final Vector2 position, final Optional<Integer> noAggro,
            final Character character, final Optional<Double> regen, final Optional<Double> senseDistance,
            final long points) {
        super(life, attack, speed, dimension);
        this.initialPosition = position;
        this.noAggro = noAggro.filter(a -> a >= 0).orElse(STANDARD_NO_AGGRO);
        this.character = character;
        this.regen = regen.filter(r -> r >= 0.05 && r <= 0.9).orElse(STANDARD_REGEN);
        this.senseDistance = senseDistance.orElse(STANDARD_SENSE);
        this.points = points;
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
    public String getNoAggroName() {
        return "no_aggro";
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
    public Character getCharacter() {
        return this.character;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCharacter(final Character character) {
        this.character = character;

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
}
