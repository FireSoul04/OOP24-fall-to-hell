package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface defining the basic statistics shared by entities in the game,
 * such as life, attack, speed, and dimensions.
 *
 * @author Davide Mancini, Sara Visani
 * @see it.unibo.falltohell.model.api.gameobjects.movable.Entity
 */
public interface Statistics {

    /**
	 * Returns the current life points of the entity.
     * <p>
     * @return the entity's current life
	 */
	public double getLife();

	/**
	 * Sets the life points of the entity.
     * <p>
     * @param life the new life value to be set
	 */
	public void setLife(final double life);

    /**
     * Adds a specified amount to the entity's current life.
     * <p>
     * @param life the amount of life to add
     */
    public void addLife(final double life);

    /**
     * Subtracts a specified amount from the entity's current life.
     * <p>
     * @param life the amount of life to subtract
     */
    public void subLife(final double life);

	/**
	 * Returns the current attack power of the entity.
     * <p>
     * @return the attack value
	 */
	public double getAttack();

	/**
	 * Sets the attack power of the entity.
     * <p>
     * @param attack the new attack value to be set
	 */
	public void setAttack(final double attack);

	/**
	 * Returns the current movement speed of the entity.
     * <p>
     * @return the speed as a {@link Vector2}
	 */
	public Vector2 getSpeed();

	/**
	 * Sets the movement speed of the entity.
     * <p>
     * @param speed the new speed value as a {@link Vector2}
	 */
	public void setSpeed(final Vector2 speed);

    /**
     * Returns the physical size of the entity.
     * <p>
     * @return the entity's {@link Dimensions}
     */
    public Dimensions getDimensions();
}
