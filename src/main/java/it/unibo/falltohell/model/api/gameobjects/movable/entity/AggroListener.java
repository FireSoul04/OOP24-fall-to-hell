package it.unibo.falltohell.model.api.gameobjects.movable.entity;

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
 * @see it.unibo.falltohell.model.impl.gameobjects.movable.entity.enemy.ManagerIngage
 */
@FunctionalInterface
public interface AggroListener {

    /**
     * Called when the player enters or exit a safe zone.
     */
    void call();
}
