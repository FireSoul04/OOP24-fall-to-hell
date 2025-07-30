package it.unibo.falltohell.model.api.manager;

import it.unibo.falltohell.model.api.GameEvent;
import it.unibo.falltohell.model.api.GameEventCondition;
/**
 * The manager for the game event.
 * @param <K> the type of the key used to identify events.
 */
public interface GameEventManager<K> {
    /**
     * Adds a condition for a specific event key.
     *
     * @param key       the key identifying the event.
     * @param condition the condition to check for this event.
     */
    void addCondition(K key, GameEventCondition condition);

    /**
     * Checks the condition associated with the given key.
     *
     * @param key the key identifying the event.
     * @return {@code true} if the condition is met, {@code false} otherwise.
     */
    boolean checkCondition(K key);

    /**
     * Associates an action to be executed when the condition for the given key is met.
     *
     * @param key    the key identifying the event.
     * @param action the action to execute.
     */
    void addAction(K key, GameEvent action);

    /**
     * Checks all registered conditions and executes the corresponding actions if the conditions are met.
     */
    void update();
}
