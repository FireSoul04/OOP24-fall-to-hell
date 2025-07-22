package it.unibo.falltohell.model.impl.gameobjects;

import it.unibo.falltohell.controller.api.FileController;
import it.unibo.falltohell.controller.impl.FileControllerImpl;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Item;
import it.unibo.falltohell.model.api.gameobjects.Merchant;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.model.impl.gameobjects.interactable.Potion;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.LifeBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.AttackBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.AttackSpeedBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.SpeedBuff;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.ManaBuff;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that represents the merchant that handles the character's purchase
 * of items.
 * @author Martina Malagoli
 */
public class MerchantImpl extends GameObjectImpl implements Merchant {

    private static final String PATH = "src/main/resources/merchant/potions.txt";
    private static final Dimensions POTION_DIMENSION = new Dimensions(10, 10);
    private static final double DISTANCE_FROM_ITEMS = 3;
    private static final int NUMBER_ITEMS_AVAILABLE = 3;
    private static final double BUFF_VALUE = 0.3;
    private final List<Item> merch;
    private final List<String> allMerchFromFile;
    private int potionCounter;
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
        final FileController fileController = new FileControllerImpl();
        try {
            this.allMerchFromFile = fileController.read(PATH);
        } catch (IOException e) {
            throw new IllegalStateException("There is no potion file with in this path:" + PATH);
        }
        this.merch = new ArrayList<>();
        this.potionCounter = 0;
        this.restock();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restock() {
        this.potionCounter = 0;
        final List<String> shuffledMerch = new ArrayList<>(this.allMerchFromFile);
        Collections.shuffle(shuffledMerch);
        this.merch.addAll(shuffledMerch
                .stream()
                .limit(NUMBER_ITEMS_AVAILABLE)
                .map(this::parseItem)
                .toList());
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
     * Method to create a new item depending on the type.
     * @param itemFileRow is the row with an item's information in the file
     * @return the new item
     */
    private Item parseItem(final String itemFileRow) {
        final String[] elements = itemFileRow.split(",");
        final String type = elements[0];
        final String cost = elements[1];
        final Collider potionCollider = new BoxCollider(Vector2.zero(), POTION_DIMENSION);
        final Buff buff;
        final CharacterStatistics currentCharacterStats = (CharacterStatistics) this.getLevel()
                .getGameData()
                .getCurrentCharacter()
                .getStats();
        if (type.equalsIgnoreCase("life")) {
            buff = new LifeBuff(currentCharacterStats, BUFF_VALUE);
        } else if (type.equalsIgnoreCase("attack")) {
            buff = new AttackBuff(currentCharacterStats, BUFF_VALUE);
        } else if (type.equalsIgnoreCase("attsp")) {
            buff = new AttackSpeedBuff(currentCharacterStats, BUFF_VALUE);
        } else if (type.equalsIgnoreCase("speed")) {
            buff = new SpeedBuff(currentCharacterStats, BUFF_VALUE);
        } else if (type.equalsIgnoreCase("mana")) {
            buff = new ManaBuff(currentCharacterStats, BUFF_VALUE);
        } else {
            throw new IllegalArgumentException("The row passed is not correct: there is no item with this name");
        }
        return new Potion(this.getLevel(),
                this.computePosition(),
                0, 0,
                potionCollider,
                Long.parseLong(cost), buff);
    }
}
