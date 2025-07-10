package it.unibo.falltohell.model.impl.gameobjects;

import it.unibo.falltohell.controller.api.FileController;
import it.unibo.falltohell.controller.impl.FileControllerImpl;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Item;
import it.unibo.falltohell.model.api.gameobjects.Merchant;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents the merchant that handles the character's purchase
 * of items.
 * @author Martina Malagoli
 */
public class MerchantImpl extends GameObjectImpl implements Merchant {

    private static final String PATH = "src/main/resources/merchant";
    private final FileController fileController;
    private final List<Item> merch;

    /**
     * Initialization of the Merchant class.
     * @param lv is the current level
     * @param position is the position of the merchant in the level
     * @param width
     * @param height
     * @param collider is the collider associated with the merchant
     */
    public MerchantImpl(final Level lv, final Vector2 position, final double width,
                        final double height, final Collider collider) {
        super(lv, position, width, height, collider);
        this.fileController = new FileControllerImpl(PATH);
        this.merch = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restock() {
        //TODO --> serve la classe Entrance --> poi ci va la lettura da file
    }

    /**
     * {@inheritDoc}
     * Method to check at each frame if the items from the merchant's merch are sold.
     */
    @Override
    public void update() {
        this.sell();
    }

    /**
     * Method to remove items if they are marked as sold.
     */
    private void sell() {
        Iterator<Item> merchIterator = this.merch.iterator();
        while (merchIterator.hasNext()) {
            final Item item = merchIterator.next();
            if (item.isSold()) {
                merchIterator.remove();
            }
        }
    }
}
