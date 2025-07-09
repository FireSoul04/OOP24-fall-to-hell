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
     * Gets the initial mana of the entity.
     * <p>
     *
     * @return the initial mana value
     */
    double getInitialMana();

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
     * Adds a specified amount to the entity's current Mana.
     * <p>
     *
     * @param mana the amount of life to Mana
     */
    void addMana(double mana);

    /**
     * Subtracts a specified amount from the entity's current Mana.
     * <p>
     *
     * @param mana the amount of Mana to subtract
     */
    void subMana(double mana);

	/**
     * Gets the initial attack speed of the entity.
     * <p>
     *
     * @return the initial attack speed value
     */
    double getInitialAttackSpeed();

	/**
	 * Returns the current attack speed of the entity.
	 * This may influence how quickly the entity can perform attacks.
	 * <p>
	 *
	 * @return the attack speed
	 */
	double getAttackSpeed();

	/**
	 * Updates the attack speed of the entity.
	 * <p>
	 *
	 * @param attackSpeed the new attack speed value
	 */
	void setAttackSpeed(double attackSpeed);

	/**
     * Adds a specified amount to the entity's current AttackSpeed.
     * <p>
     *
     * @param attackSpeed the amount of AttackSpeed to add
     */
    void addAttackSpeed(double attackSpeed);

    /**
     * Subtracts a specified amount from the entity's current AttackSpeed.
     * <p>
     *
     * @param attackSpeed the amount of AttackSpeed to subtract
     */
    void subAttackSpeed(double attackSpeed);
}
