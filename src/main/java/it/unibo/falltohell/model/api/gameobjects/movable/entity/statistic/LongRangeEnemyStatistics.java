package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface for all enemies with long or special attacks
 *
 * @author Sara Visani
 */
public interface LongRangeEnemyStatistics extends BaseEnemyStatistics{

    /**
	 * @return current attack of entity
	 */
	public double getProjectileAttack();

	/**
	 * @return current speed of entity
	 */
	public Vector2 getProjectileSpeed();

    /**
     * @return the dimension of the entity
     */
    public Dimensions getProjectileDimensions();

    /**
     * @return return the name of the timer
     */
    public String getAttackName();
}
