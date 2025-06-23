package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface for characters.
 * Every statistic is updatable.
 *
 * @author Davide Mancini, Sara Visani
 */
public interface CharacterStatistics extends Statistics{
    
    /**
	 * @return mana attack of entity
	 */
	public double getMana();

	/**
	 * @param mana to be updated
	 */
	public void setMana(final double mana);

	/**
	 * @return current attack speed of entity
	 */
	public Vector2 getAttackSpeed();

	/**
	 * @param attackSpeed to be updated
	 */
	public void setAttackSpeed(final Vector2 attackSpeed);
}
