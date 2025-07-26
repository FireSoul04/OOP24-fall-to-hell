package it.unibo.falltohell.model.api.statistic;

import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface defining the basic statistics shared by entities in the game,
 * such as life, attack, speed, and dimensions.
 *
 * @author Davide Mancini
 * @author Sara Visani
 * @see Entity
 */
public interface Statistics {

    /**
     * Gets the full (maximum) life of the entity.
     * <p>
     *
     * @return the max life value
     */
    double getFullLife();

    /**
     * Returns the current life points of the entity.
     * <p>
     *
     * @return the entity's current life
     */
    double getLife();

    /**
     * Sets the life points of the entity.
     * <p>
     *
     * @param life the new life value to be set
     */
    void setLife(double life);

    /**
     * Adds a specified amount to the entity's current life.
     * <p>
     *
     * @param life the amount of life to add
     */
    void addLife(double life);

    /**
     * Subtracts a specified amount from the entity's current life.
     * <p>
     *
     * @param life the amount of life to subtract
     */
    void subLife(double life);

    /**
     * Gets the initial attack of the entity.
     * <p>
     *
     * @return the initial attack value
     */
    double getInitialAttack();

    /**
     * Returns the current attack power of the entity.
     * <p>
     *
     * @return the attack value
     */
    double getAttack();

    /**
     * Sets the attack power of the entity.
     * <p>
     *
     * @param attack the new attack value to be set
     */
    void setAttack(double attack);

    /**
     * Adds a specified amount to the entity's current attack.
     * <p>
     *
     * @param attack the amount of attack to add
     */
    void addAttack(double attack);

    /**
     * Subtracts a specified amount from the entity's current attack.
     * <p>
     *
     * @param attack the amount of life to subtract
     */
    void subAttack(double attack);

    /**
     * Gets the initial speed of the entity.
     * <p>
     *
     * @return the initial speed value
     */
    Vector2 getInitialSpeed();

    /**
     * Returns the current movement speed of the entity.
     * <p>
     *
     * @return the speed as a {@link Vector2}
     */
    Vector2 getSpeed();

    /**
     * Sets the movement speed of the entity.
     * <p>
     *
     * @param speed the new speed value as a {@link Vector2}
     */
    void setSpeed(Vector2 speed);

    /**
     * Adds a specified amount to the entity's current Speed.
     * <p>
     *
     * @param speed the amount of speed to add
     */
    void addSpeed(Vector2 speed);

    /**
     * Subtracts a specified amount from the entity's current Speed.
     * <p>
     *
     * @param speed the amount of speed to subtract
     */
    void subSpeed(Vector2 speed);

    /**
     * Returns the physical size of the entity.
     * <p>
     *
     * @return the entity's {@link Dimensions}
     */
    Dimensions getDimensions();
}
