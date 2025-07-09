package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface defining the basic statistics shared by entities in the game,
 * such as life, attack, speed, and dimensions.
 *
 * @author Davide Mancini
 * @author Sara Visani
 * @see it.unibo.falltohell.model.api.gameobjects.movable.Entity
 */
public interface Statistics {

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
     * Returns the physical size of the entity.
     * <p>
     * 
     * @return the entity's {@link Dimensions}
     */
    Dimensions getDimensions();
}
