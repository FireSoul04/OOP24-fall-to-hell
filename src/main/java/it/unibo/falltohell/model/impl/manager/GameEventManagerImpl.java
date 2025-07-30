package it.unibo.falltohell.model.impl.manager;

import java.util.HashMap;
import java.util.Map;
import it.unibo.falltohell.model.api.GameEvent;
import it.unibo.falltohell.model.api.GameEventCondition;
import it.unibo.falltohell.model.api.manager.GameEventManager;


/**
 * Manages game events and their associated conditions and actions.
 * Allows registration of conditions (when an event should trigger) and actions
 * (what to do when triggered)
 * using a key of type {@code K}. On each update, all conditions are checked
 * and, if true, the corresponding
 * action is executed.
 *
 * @param <K> the type of key used to identify events
 * @author Casadei Lorenzo
 */
public class GameEventManagerImpl<K> implements GameEventManager<K> {
    private final Map<K, GameEventCondition> conditions = new HashMap<>();
    private final Map<K, GameEvent> actions = new HashMap<>();

    /**
     * {@inheritDoc}
    */
    @Override
    public void addCondition(final K key, final GameEventCondition condition) {
        this.conditions.put(key, condition);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public boolean checkCondition(final K key) {
        return this.conditions.get(key).test();
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public void addAction(final K key, final GameEvent action) {
        this.actions.put(key, action);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public void update() {
        this.conditions.forEach((key, cond) -> {
            if (this.actions.containsKey(key) && cond.test()) {
                this.actions.get(key).execute();
            }
        });
    }
}
