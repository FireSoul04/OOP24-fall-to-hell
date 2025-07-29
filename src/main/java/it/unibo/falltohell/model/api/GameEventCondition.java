package it.unibo.falltohell.model.api;
/**
 * Represents a condition that can be tested in the game.
 * This interface defines a single method to test the condition.
 *
 * @author Casadei Lorenzo
 */
@FunctionalInterface
public interface GameEventCondition {
    /**
     * Test the condition.
     * @return true if the condition is met, false otherwise
     */
    boolean test();

}
