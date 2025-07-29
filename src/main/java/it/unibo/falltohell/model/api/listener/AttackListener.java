package it.unibo.falltohell.model.api.listener;

/**
 * Listener interface used by {@link it.unibo.falltohell.model.api.manager.EnemyTimerManager}
 * to notify an enemy when its attack timer finishes.
 * <p>
 * Implementations should define the {@link #attack()} method
 * to specify the behavior that occurs when the enemy is ready to perform an attack.
 * </p>
 *
 * @author Sara Visani
 */
public interface AttackListener {

    /**
     * Called when the enemy's attack timer completes,
     * indicating that the enemy can perform an attack.
     */
    void attack();
}
