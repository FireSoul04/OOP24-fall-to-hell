package it.unibo.falltohell.model.api.buff;

/**
 * Interface that represents buff for the current character.
 * @author Martina Malagoli
 */
public interface Buff {

    /**
     * Method to add the buff to the current character statistics.
     */
    void apply();

    /**
     * Method to remove the buff from the current character statistics.
     */
    void remove();
}
