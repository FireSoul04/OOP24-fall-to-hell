package it.unibo.falltohell.model.api.buff;

/**
 * Interface that represents buff for an entity.
 * @author Martina Malagoli
 */
public interface Buff {

    /**
     * Method to add the buff to the entity statistics.
     */
    void apply();

    /**
     * Method to remove the buff from the entity statistics.
     */
    void remove();
}
