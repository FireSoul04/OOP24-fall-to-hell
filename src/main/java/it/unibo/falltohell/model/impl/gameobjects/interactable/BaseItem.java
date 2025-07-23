package it.unibo.falltohell.model.impl.gameobjects.interactable;

import java.util.Optional;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Item;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a general item that can be bought by the
 * character from the merchant.
 * @author Martina Malagoli
 */
public abstract class BaseItem extends GameObjectImpl implements Item {

    private boolean sold;
    private final long price;

    public BaseItem(final Level lv, final Vector2 position, final Collider collider, final long price, final Optional<Drawable> drawable) {
        super(lv, position, collider, drawable);
        this.price = price;
        this.sold = false;
    }

    /**
     *{@inheritDoc}
     * Method to apply the effect given by the item to the character
     * and mark the item as sold.
     * @param character is the character on which the item's effect will be applied
     */
    @Override
    public void interact(final Character character) {
        if (!this.sold) {
            this.sold = true;
            this.purchase(this.price);
            this.onInteract(character);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public long getPrice() {
        return this.price;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean isSold() {
        return this.sold;
    }

    /**
     * Method to remove points from the character to buy an item
     * if possible (the character has enough points).
     * @param price is the price of the item
     */
    private void purchase(final long price) {
        try {
            this.getLevel().getGameData().removePoints(price);
        } catch (final IllegalArgumentException e) {
            //TODO--> deve lanciare un messaggio di errore
        }
    }

    /**
     * Method that tells what to do when an interaction occurs.
     * @param character is the character on which the item's effect will be applied
     */
    protected abstract void onInteract(Character character);
}
