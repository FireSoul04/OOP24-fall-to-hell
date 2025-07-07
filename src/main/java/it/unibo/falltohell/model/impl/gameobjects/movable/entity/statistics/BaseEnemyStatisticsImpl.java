package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics;

import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.BaseEnemyStatistics;
import it.unibo.falltohell.model.impl.TimerManagerImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.StatisticsImpl;
import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Implementation of {@link BaseEnemyStatistics} containing all statistics
 * of an enemy that attacks physically without restrictions.
 *
 * @author Sara Visani
 */
public class BaseEnemyStatisticsImpl extends StatisticsImpl implements BaseEnemyStatistics {

    final private Vector2 initialPosition;
    final private double fullLife;
    final private int noAggro;
    final private TimerManager tm = new TimerManagerImpl();
    private Character character;

    /**
     * Constructs new enemy statistics with the specified parameters.
     * <p>
     * 
     * @param life      the total life points of the enemy
     * @param attack    the attack power
     * @param speed     the movement speed as a {@link Vector2}
     * @param dimension the physical dimensions of the enemy {@link Dimensions}
     * @param position  the initial position as a {@link Vector2}
     * @param noAggro   an integer representing the aggro state (no aggro)
     * @param character the associated {@link Character} instance
     */
    public BaseEnemyStatisticsImpl(double life, double attack, Vector2 speed, Dimensions dimension,
            final Vector2 position, final int noAggro, final Character character) {
        super(life, attack, speed, dimension);
        this.initialPosition = position;
        this.fullLife = life;
        this.noAggro = noAggro;
        this.character = character;
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
    public double getFullLife() {
        return this.fullLife;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TimerManager getTm() {
        return this.tm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNo_aggroName() {
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
    public void setCharacter(Character character) {
        this.character = character;
    }
}
