package it.unibo.falltohell.model.api;

public interface GameEvent {
    
    /**
     * execute the event
     * @throws Exception if the event cannot be executed
     */
    void execute() throws Exception;
}

    
