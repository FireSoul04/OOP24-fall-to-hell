package it.unibo.falltohell.model.api;

@FunctionalInterface
public interface GameEventCondition {
    /**
     * Test the condition
     * @return true if the condition is met, false otherwise
     */
    boolean test();

}
