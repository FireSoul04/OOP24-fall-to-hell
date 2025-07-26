package it.unibo.falltohell.model.api.statistic;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

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
    double getProjectileAttack();

    /**
     * Returns the projectile's movement speed.
     * <p>
     *
     * @return a {@link Vector2} representing the speed of the projectile
     */
    Vector2 getProjectileSpeed();

    /**
     * Returns the dimensions (width and height) of the projectile.
     * <p>
     *
     * @return a {@link Dimensions} object representing projectile size
     */
    Dimensions getProjectileDimensions();

    /**
     * Gets the cooldown or timing associated with the projectile attack.
     *
     * @return the time between attacks in game ticks or milliseconds
     */
    int getTimeAttack();
}
