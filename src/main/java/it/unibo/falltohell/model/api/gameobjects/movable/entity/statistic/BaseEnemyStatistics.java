package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface for all enemies
 *
 * @author Sara Visani
 */
public interface BaseEnemyStatistics extends Statistics{

    /**
     * @return starting coordinates
     */
    public Vector2 getInitialPos();

    /**
     * @return the maximum life
     */
    public double getFullLife();

    /**
     * @return the timer manager of the enemy
     */
    public TimerManager getTm();

    /**
     * @return return the name of the timer
     */
    public String getNo_aggroName();

    /**
     * @return return the time of duration of the timer
     */
    public int getNoAggro();

    /**
     * @return return the character that the enemy follow
     */
    public Character getCharacter();

    /**
     * @param character change the charater the enemy follow
     */
    public void setCharacter(final Character character);
}
