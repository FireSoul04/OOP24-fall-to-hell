package it.unibo.falltohell.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.falltohell.model.api.GameEvent;
import it.unibo.falltohell.model.api.GameEventCondition;
/**
 * Manages game events and their associated conditions and actions.
 * <p>
 * Allows registration of conditions (when an event should trigger) and actions (what to do when triggered)
 * using a key of type {@code K}. On each update, all conditions are checked and, if true, the corresponding
 * action is executed.
 * </p>
 *
 * @param <K> the type of key used to identify events
 */
public class GameEventManager<K> {
    private final Map<K, GameEventCondition> conditions = new HashMap<>();
    private final Map<K, GameEvent> actions = new HashMap<>();
    /**
     * Adds a condition for a specific event key.
     *
     * @param key the key identifying the event
     * @param condition the condition to check for this event
     */
    public void addCondition(K key, GameEventCondition condition) {
        this.conditions.put(key, condition);
    }
    /**
     * Checks the condition associated with the given key.
     *
     * @param key the key identifying the event
     * @return {@code true} if the condition is met, {@code false} otherwise
     */
    public boolean checkCondition(K key) {
        return this.conditions.get(key).test();
    }
    /**
     * Associates an action to be executed when the condition for the given key is met.
     *
     * @param key the key identifying the event
     * @param action the action to execute
     */
    public void addAction(K key, GameEvent action) {
        this.actions.put(key, action);
    }
    /**
     * Checks all registered conditions and executes the corresponding 
     * actions if the conditions are met.
     */
    public void update() {
        this.conditions.forEach((key, cond) -> {
            if (this.actions.containsKey(key) && cond.test()) {
                try {
                    this.actions.get(key).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
