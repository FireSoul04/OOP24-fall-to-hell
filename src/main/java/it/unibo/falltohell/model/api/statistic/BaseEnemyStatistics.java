package it.unibo.falltohell.model.api.statistic;

import java.util.Map;

import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.enemy.BaseEnemy.BuffNames;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface for all enemy statistics.
 * <p>
 * Extends {@link Statistics} and defines additional data and behavior for enemy
 * entities,
 * such as initial position, health, and aggro control.
 *
 * @see Statistics
 * @see TimerManager
 * @see Vector2
 *
 * @author Sara Visani
 */
public interface BaseEnemyStatistics extends Statistics {

    /**
     * Gets the enemy's initial spawn coordinates.
     * <p>
     *
     * @return the initial {@link Vector2} position
     */
    Vector2 getInitialPos();

    /**
     * Gets the duration (in ticks or seconds) of the no-aggro state.
     * <p>
     *
     * @return the no-aggro time duration
     */
    int getNoAggro();

    /**
     * Returns the regeneration rate of the enemy (if applicable).
     * <p>
     *
     * @return the regeneration value
     */
    double getRegen();

    /**
     * Gets the enemy's sensing distance (e.g., detection range).
     * <p>
     *
     * @return the sense distance
     */
    double getSenseDistance();

    /**
     * Gets the enemy's points after a kill.
     * <p>
     *
     * @return the points of a enemy
     */
    long getPoints();

    /**
     * @return the map with key type of buff and value the percentage
     */
    Map<BuffNames, Double> getBuffMap();

    /**
     * @return the multiplier of buffs
     */
    double getMultiplier();
}
