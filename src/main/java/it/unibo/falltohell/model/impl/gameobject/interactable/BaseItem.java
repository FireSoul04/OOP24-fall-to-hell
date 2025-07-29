package it.unibo.falltohell.model.impl.gameobject.interactable;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.interactable.Item;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a general item that can be bought by the
 * character from the merchant.
 * @author Martina Malagoli
 */
public abstract class BaseItem extends GameObjectImpl implements Item {

    private static final Vector2 LABEL_POSITION = new Vector2(70.0, 100.0);
    private static final long LABEL_DURATION = 1500;
    private boolean sold;
    private final long price;
    private final Label label;

    /**
     * Initialization of the BaseItem class.
     * @param lv is the current level
     * @param position is the position of the item in the level
     * @param collider is the collider associated with the item
     * @param price is the price of the item
     * @param fileName is the name of the image file associated to the item
     */
    public BaseItem(final Level lv, final Vector2 position, final Collider collider,
                    final long price, final String fileName) {
        super(lv, position, collider);
        this.price = price;
        this.sold = false;
        this.label = new Label("You don't have enough soul points",
                LABEL_POSITION, false);
        this.initDrawable(Priority.VERY_LOW, fileName);
        this.getLevel().getDrawableRenderableHandler().linkLabel(this.label);
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
            try {
                this.purchase(this.price);
                this.sold = true;
                this.onInteract(character);
                this.getLevel().getDrawableRenderableHandler().removeLink(this.label);
            } catch (final IllegalArgumentException e) {
                this.label.setVisible(true);
                final String timerName = "can_not_sell" + this.hashCode();
                this.getLevel().getTimerManager().restartIfPresent(timerName, new CustomTimerImpl(LABEL_DURATION,
                    () -> this.label.setVisible(false)));
            }
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
        this.getLevel().getGameData().removePoints(price);
    }

    /**
     * Method that tells what to do when an interaction occurs.
     * @param character is the character on which the item's effect will be applied
     */
    protected abstract void onInteract(Character character);
}
