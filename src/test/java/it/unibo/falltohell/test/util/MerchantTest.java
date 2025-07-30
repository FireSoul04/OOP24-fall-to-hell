package it.unibo.falltohell.test.util;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.interactable.Item;
import it.unibo.falltohell.model.api.gameobject.Merchant;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.physics.Collider;

import it.unibo.falltohell.model.impl.buff.LifeBuff;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.gameobject.interactable.Potion;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for new Merchant dedicated to test.
 * The method restock is implemented in a default mode to
 * check if the selling and use of items works correctly.
 * This class has an additional method compared to the Merchant
 * class that permits to have access to the merch in the shop.
 * @author Martina Malagoli
 */
public class MerchantTest extends GameObjectImpl implements Merchant {

    private static final Dimensions POTION_DIMENSION = new Dimensions(10, 10);
    private static final double DISTANCE_FROM_ITEMS = 3.0;
    private static final double BUFF_VALUE = 0.3;
    private final List<Item> merch;
    private int potionCounter;

    /**
     * Initialization of the MerchantTest class.
     * @param lv is the current level
     * @param position is the position of the merchant in the level
     * @param collider is the collider associated with the merchant
     */
    public MerchantTest(final Level lv, final Vector2 position, final Collider collider) {
        super(lv, position, collider);
        this.merch = new ArrayList<>();
        this.potionCounter = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restock() {
        this.merch.add(this.createPotion());
        this.merch.add(this.createPotion());
        this.merch.add(this.createPotion());
    }

    /**
     * @return  new potion item
     */
    private Potion createPotion() {
        final CharacterStatistics statistics = (CharacterStatistics) this.getLevel()
                .getGameData()
                .getCurrentCharacter()
                .getStats();
        final Buff buff = new LifeBuff(statistics, BUFF_VALUE);
        return new Potion(this.getLevel(), this.computePosition(), new BoxCollider(POTION_DIMENSION),
            100, buff, "test.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destock() {
        this.merch.clear();
    }

    /**
     * {@inheritDoc}
     * It is used to check at each frame if the items from the merchant's merch are sold.
     */
    @Override
    public void update() {
        this.sell();
    }

    /**
     * Method to remove items if they are marked as sold.
     */
    private void sell() {
        this.merch.removeIf(Item::isSold);
    }

    /**
     * Method to compute the position of an item.
     * @return the item's position
     */
    private Vector2 computePosition() {
        potionCounter++;
        final double itemY = this.getPosition().y();
        final double itemX = this.getPosition().x() - TILE_SIZE * potionCounter * DISTANCE_FROM_ITEMS;
        return new Vector2(itemX, itemY);
    }

    /**
     * @return the merch in the merchant's shop
     */
    public List<Item> getMerch() {
        return Collections.unmodifiableList(this.merch);
    }

    /**
     * @return the distance from each item
     */
    public double getDistanceFromItems() {
        return DISTANCE_FROM_ITEMS;
    }

}
