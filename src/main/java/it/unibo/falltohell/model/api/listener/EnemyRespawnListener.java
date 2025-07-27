package it.unibo.falltohell.model.api.listener;

/**
 * <p>
 * Functional interface used to define a callback for enemy respawn behavior.
 * </p>
 *
 * <p>
 * Implementations should define what happens when an enemy needs to be
 * respawned, typically after exiting a safe zone.
 * </p>
 *
 * @author Sara Visani
 */
@FunctionalInterface
public interface EnemyRespawnListener {

    /**
     * <p>
     * Called to trigger the enemy respawn logic.
     * </p>
     */
    void respawn();
}
