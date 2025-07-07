package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface for all enemy statistics.
 * <p>
 * Extends {@link Statistics} and defines additional data and behavior for enemy entities,
 * such as initial position, health, and aggro control.
 *
 * @see Character
 * @see TimerManager
 * @see Vector2
 *
 * @author Sara Visani
 */
public interface BaseEnemyStatistics extends Statistics{

    /**
     * Gets the enemy's initial spawn coordinates.
     * <p>
     * @return the initial {@link Vector2} position
     */
    public Vector2 getInitialPos();

    /**
     * Gets the full (maximum) life of the enemy.
     * <p>
     * @return the max life value
     */
    public double getFullLife();

    /**
     * Returns the {@link TimerManager} used to manage time-based behaviors like aggro.
     * <p>
     * @return the timer manager
     */
    public TimerManager getTm();

    /**
     * Gets the name identifier for the no-aggro timer.
     * <p>
     * @return the timer name as a {@link String}
     */
    public String getNo_aggroName();

    /**
     * Gets the duration (in ticks or seconds) of the no-aggro state.
     * <p>
     * @return the no-aggro time duration
     */
    public int getNoAggro();

    /**
     * Gets the current {@link Character} that this enemy is targeting.
     * <p>
     * @return the followed character
     */
    public Character getCharacter();

    /**
     * Sets the {@link Character} that this enemy should follow.
     * <p>
     * @param character the character to follow
     */
    public void setCharacter(final Character character);
}
