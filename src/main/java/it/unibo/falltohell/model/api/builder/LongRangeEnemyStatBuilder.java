package it.unibo.falltohell.model.api.builder;

import it.unibo.falltohell.model.api.statistic.LongRangeEnemyStatistics;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Builder interface for constructing {@link LongRangeEnemyStatistics} objects.
 * This builder allows configuration of various properties relevant to ranged
 * enemies,
 * such as projectile speed, dimensions, and attack timing.
 * <p>
 *
 * @author Sara Visani
 */
public interface LongRangeEnemyStatBuilder {

    /**
     * Sets the projectile attack power.
     * <p>
     *
     * @param projectileAttack the damage of the projectile
     * @return this builder instance for method chaining
     */
    LongRangeEnemyStatBuilder withProjectileAttack(double projectileAttack);

    /**
     * Sets the velocity of the projectile.
     * <p>
     *
     * @param projectileVelocity the {@link Vector2} representing projectile speed
     *                           in both axes
     * @return this builder instance for chaining
     */
    LongRangeEnemyStatBuilder withProjectileVelocity(Vector2 projectileVelocity);

    /**
     * Sets the dimensions of the projectile.
     * <p>
     *
     * @param projectileDimensions the {@link Dimensions} of the projectile
     * @return this builder instance for chaining
     */
    LongRangeEnemyStatBuilder withProjectileDimensions(Dimensions projectileDimensions);

    /**
     * Sets the time between attacks.
     * <p>
     *
     * @param timeAttack the time between two attacks
     * @return this builder instance for chaining
     */
    LongRangeEnemyStatBuilder withTimeAttack(int timeAttack);

    /**
     * Builds the configured {@link LongRangeEnemyStatistics} instance.
     * <p>
     *
     * @return the constructed statistics object
     */
    LongRangeEnemyStatistics build();

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
