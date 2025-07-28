package it.unibo.falltohell.model.api.statistic;

import it.unibo.falltohell.util.Vector2;

/**
 * Interface representing the statistics specific to characters.
 * <p>
 * Extends {@link Statistics} and provides additional attributes such as
 * temporary life, mana, temporary mana
 * and attack speed.
 * These values are updatable and relevant to gameplay mechanics such as ability
 * casting and attack timing.
 *
 * @author Davide Mancini
 * @author Sara Visani
 * @see Statistics
 * @see Vector2
 */
public interface CharacterStatistics extends Statistics {

    /**
     * Returns the current temporary life of the entity.
     * <p>
     *
     * @return the current temporary life value
     */
    double getTemporaryLife();

    /**
     * Updates the temporary life of the entity.
     * <p>
     *
     * @param temporaryLife the new temporary life value
     */
    void setTemporaryLife(double temporaryLife);

    /**
     * Set the entity's temporary life to 0.
     */
    void resetTemporaryLife();

    /**
     * Adds a specified amount to the entity's current temporary life.
     * <p>
     *
     * @param temporaryLife the amount of temporary life to add
     */
    void addTemporaryLife(double temporaryLife);

    /**
     * Subtracts a specified amount from the entity's current temporary life.
     * <p>
     *
     * @param temporaryLife the amount of temporary life to subtract
     */
    void subTemporaryLife(double temporaryLife);

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
     * Set the entity's temporary mana to 0.
     */
    void resetTemporaryMana();

    /**
     * Adds a specified amount to the entity's current Mana.
     * <p>
     *
     * @param mana the amount of Mana to add
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
     * Returns the current temporary mana of the entity.
     * <p>
     *
     * @return the current temporary mana value
     */
    double getTemporaryMana();

    /**
     * Updates the temporary mana of the entity.
     * <p>
     *
     * @param temporaryMana the new temporary mana value
     */
    void setTemporaryMana(double temporaryMana);

    /**
     * Adds a specified amount to the entity's current temporary mana.
     * <p>
     *
     * @param temporaryMana the amount of temporary mana to add
     */
    void addTemporaryMana(double temporaryMana);

    /**
     * Subtracts a specified amount from the entity's current temporary mana.
     * <p>
     *
     * @param temporaryMana the amount of temporary mana to subtract
     */
    void subTemporaryMana(double temporaryMana);

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
