package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface representing the statistics specific to characters.
 * <p>
 * Extends {@link Statistics} and provides additional attributes such as mana
 * and attack speed.
 * These values are updatable and relevant to gameplay mechanics such as ability
 * casting and attack timing.
 *
 * @see Statistics
 * @see Vector2
 *
 * @author Davide Mancini, Sara Visani
 */
public interface CharacterStatistics extends Statistics {

	/**
	 * Returns the current mana of the entity.
	 * <p>
	 * 
	 * @return the current mana value
	 */
	double getMana();

	/**
	 * Updates the mana of the entity.
	 * <p>
	 * 
	 * @param mana the new mana value
	 */
	void setMana(double mana);

	/**
	 * Returns the current attack speed vector of the entity.
	 * This may influence how quickly the entity can perform attacks.
	 * <p>
	 * 
	 * @return the attack speed as a {@link Vector2}
	 */
	Vector2 getAttackSpeed();

	/**
	 * Updates the attack speed of the entity.
	 * <p>
	 * 
	 * @param attackSpeed the new attack speed value
	 */
	void setAttackSpeed(Vector2 attackSpeed);
}
