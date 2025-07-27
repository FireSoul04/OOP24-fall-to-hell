package it.unibo.falltohell.model.api.listener;

/**
 * Functional interface representing a listener that gets triggered when an
 * aggro-related event occurs (e.g., an enemy is activated by an entrance).
 * <p>
 * This interface is typically used to notify registered entities, such as
 * enemies, to change their behavior or state (e.g., start attacking) when a
 * trigger like an {@code Entrance} is activated.
 *
 * <p>
 * It is meant to be implemented as a lambda or method reference.
 *
 * @author Sara Visani
 * @see SafeZoneManager
 */
@FunctionalInterface
public interface ExitSafeZoneListener {

    /**
     * Called when the player exit a safe zone.
     */
    void call();
}
