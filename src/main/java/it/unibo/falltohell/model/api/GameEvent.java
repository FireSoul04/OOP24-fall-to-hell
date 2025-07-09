package it.unibo.falltohell.model.api;

@FunctionalInterface
public interface GameEvent {
    
    /**
     * execute the event
     */
    void execute();
}

    
