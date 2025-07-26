package it.unibo.falltohell.model.api.gameobject.interactable;

/**
 * Interface that represents an item that the character can buy from the
 * merchant.
 *
 * @author Martina Malagoli
 */
public interface Item extends Interactable {

    /**
     * @return the price of the item to be sold
     */
    long getPrice();

    /**
     * @return if the item is sold
     */
    boolean isSold();
}
