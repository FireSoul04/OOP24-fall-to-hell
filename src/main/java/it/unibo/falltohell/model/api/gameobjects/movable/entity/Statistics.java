package it.unibo.falltohell.model.api.gameobjects.movable.entity;

import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface containing common statistic of different entities entity.
 *
 * @author Davide Mancini, Sara Visani
 */
public interface Statistics {

    /**
	 * @return current life of entity
	 */
	public double getLife();

	/**
	 * @param life to be updated
	 */
	public void setLife(final double life);

    /**
     * @param life what to add
     */
    public void addLife(final double life);

    /**
     * @param life what to subtract
     */
    public void subLife(final double life);

	/**
	 * @return current attack of entity
	 */
	public double getAttack();

	/**
	 * @param attack to be updated
	 */
	public void setAttack(final double attack);

	/**
	 * @return current speed of entity
	 */
	public Vector2 getSpeed();

	/**
	 * @param speed to be updated
	 */
	public void setSpeed(final Vector2 speed);

    /**
     * @return the dimension of the entity
     */
    public Dimensions getDimensions();
}
