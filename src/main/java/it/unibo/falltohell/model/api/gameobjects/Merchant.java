package it.unibo.falltohell.model.api.gameobjects;

import it.unibo.falltohell.model.api.GameObject;

/**
 * Interface to handle the character's purchase of items.
 * @author Martina Malagoli
 */
public interface Merchant extends GameObject {

    /**
     * Method to refill the shop of items.
     */
    void restock();
}
