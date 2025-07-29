package it.unibo.falltohell.model.api;
/**
 * Represents a game event that can be executed.
 * This interface defines a single method to execute the event logic.
 *
 * @author Casadei Lorenzo
 */
@FunctionalInterface
public interface GameEvent {

    /**
     * execute the event.
     */
    void execute();
}


