package it.unibo.falltohell.model.api.gameobject;

/**
 * Interface to handle the character's purchase of items.
 * @author Martina Malagoli
 */
public interface Merchant extends GameObject {

    /**
     * Method to refill the shop of items.
     */
    void restock();

    /**
     * Method to clear the shop from all items.
     */
    void destock();
}
