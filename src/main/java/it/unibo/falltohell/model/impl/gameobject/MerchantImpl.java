package it.unibo.falltohell.model.impl.gameobject;

import it.unibo.falltohell.controller.api.FileController;
import it.unibo.falltohell.controller.impl.FileControllerImpl;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.interactable.Item;
import it.unibo.falltohell.model.api.gameobject.Merchant;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.interactable.Potion;
import it.unibo.falltohell.model.impl.buff.LifeBuff;
import it.unibo.falltohell.model.impl.buff.AttackBuff;
import it.unibo.falltohell.model.impl.buff.AttackSpeedBuff;
import it.unibo.falltohell.model.impl.buff.SpeedBuff;
import it.unibo.falltohell.model.impl.buff.ManaBuff;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that represents the merchant that handles the character's purchase
 * of items.
 *
 * @author Martina Malagoli
 */
public class MerchantImpl extends GameObjectImpl implements Merchant {

    private static final String PATH = "/merchant/potions.txt";
    private static final Dimensions POTION_DIMENSION = new Dimensions(10.0, 10.0);
    private static final double DISTANCE_FROM_ITEMS = 3.0;
    private static final int NUMBER_ITEMS_AVAILABLE = 3;
    private static final double BUFF_VALUE = 0.3;
    private final List<Item> merch;
    private final List<String> allMerchFromFile;
    private int potionCounter;

    /**
     * Initialization of the Merchant class.
     *
     * @param lv       is the current level
     * @param position is the position of the merchant in the level
     * @param collider is the collider associated with the merchant
     */
    public MerchantImpl(final Level lv, final Vector2 position, final Collider collider) {
        super(lv, position, collider);
        final FileController fileController = new FileControllerImpl();
        this.allMerchFromFile = fileController.read(PATH);
        this.merch = new ArrayList<>();
        this.potionCounter = 0;
        this.initDrawable(Priority.VERY_LOW, "merchant.png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restock() {
        if (this.merch.isEmpty()) {
            this.potionCounter = 0;
            final List<String> shuffledMerch = new ArrayList<>(this.allMerchFromFile);
            Collections.shuffle(shuffledMerch);
            this.merch.addAll(shuffledMerch
                    .stream()
                    .limit(NUMBER_ITEMS_AVAILABLE)
                    .map(this::parseItem)
                    .toList());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destock() {
        this.merch.forEach(this.getLevel()::removeGameObject);
        this.merch.clear();
    }

    /**
     * {@inheritDoc}
     * It is used to check at each frame if the items from the merchant's merch are
     * sold.
     */
    @Override
    public void update() {
        this.sell();
    }

    /**
     * Method to remove items if they are marked as sold.
     */
    private void sell() {
        final Iterator<Item> iterator = this.merch.iterator();
        while (iterator.hasNext()) {
            final Item item = iterator.next();
            if (item.isSold()) {
                this.getLevel().removeGameObject(item);
                iterator.remove();
            }
        }
    }

    /**
     * Method to compute the position of an item.
     *
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
     *
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
        final String fileName;
        if ("life".equalsIgnoreCase(type)) {
            buff = new LifeBuff(currentCharacterStats, BUFF_VALUE);
            fileName = "life_potion.png";
        } else if ("attack".equalsIgnoreCase(type)) {
            buff = new AttackBuff(currentCharacterStats, BUFF_VALUE);
            fileName = "attack_potion.png";
        } else if ("attsp".equalsIgnoreCase(type)) {
            buff = new AttackSpeedBuff(currentCharacterStats, BUFF_VALUE);
            fileName = "attsp_potion.png";
        } else if ("speed".equalsIgnoreCase(type)) {
            buff = new SpeedBuff(currentCharacterStats, BUFF_VALUE);
            fileName = "speed_potion.png";
        } else if ("mana".equalsIgnoreCase(type)) {
            buff = new ManaBuff(currentCharacterStats, BUFF_VALUE);
            fileName = "mana_potion.png";
        } else {
            throw new IllegalArgumentException("The row passed is not correct: there is no item with this name");
        }
        return new Potion(this.getLevel(),
                this.computePosition(),
                potionCollider,
                Long.parseLong(cost), buff, fileName);
    }
}
