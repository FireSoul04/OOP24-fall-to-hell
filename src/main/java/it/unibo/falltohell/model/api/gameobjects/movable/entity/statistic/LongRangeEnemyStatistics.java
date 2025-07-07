package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic;

import it.unibo.falltohell.model.util.Dimensions;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Interface representing statistics specific to long-range or special-attack
 * enemies.
 * <p>
 * Extends the base statistics provided by {@link BaseEnemyStatistics} with
 * additional properties for projectile-based combat such as damage, speed, and
 * dimensions.
 * </p>
 *
 * @see BaseEnemyStatistics
 * @see Dimensions
 * @see Vector2
 *
 * @author Sara Visani
 */
public interface LongRangeEnemyStatistics extends BaseEnemyStatistics {

    /**
     * Returns the damage value of the projectile attack.
     * <p>
     * 
     * @return the current projectile attack damage
     */
    public double getProjectileAttack();

    /**
     * Returns the projectile's movement speed.
     * <p>
     * 
     * @return a {@link Vector2} representing the speed of the projectile
     */
    public Vector2 getProjectileSpeed();

    /**
     * Returns the dimensions (width and height) of the projectile.
     * <p>
     * 
     * @return a {@link Dimensions} object representing projectile size
     */
    public Dimensions getProjectileDimensions();

    /**
     * Returns the internal name used for the projectile's attack timer.
     * <p>
     * 
     * @return a {@link String} identifying the attack timer name
     */
    public String getAttackName();
}
