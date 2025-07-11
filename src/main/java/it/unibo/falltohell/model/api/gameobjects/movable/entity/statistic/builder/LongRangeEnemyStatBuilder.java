package it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.builder;

import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.LongRangeEnemyStatistics;
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
    public LongRangeEnemyStatBuilder withProjectileVelocity(Vector2 projectileVelocity);

    /**
     * Sets the dimensions of the projectile.
     * <p>
     *
     * @param projectileDimensions the {@link Dimensions} of the projectile
     * @return this builder instance for chaining
     */
    public LongRangeEnemyStatBuilder withProjectileDimensions(Dimensions projectileDimensions);

    /**
     * Sets the time between attacks.
     * <p>
     *
     * @param timeAttack the time between two attacks
     * @return this builder instance for chaining
     */
    public LongRangeEnemyStatBuilder withTimeAttack(int timeAttack);

    /**
     * Builds the configured {@link LongRangeEnemyStatistics} instance.
     * <p>
     *
     * @return the constructed statistics object
     */
    public LongRangeEnemyStatistics build();
}
