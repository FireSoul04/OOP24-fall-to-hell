package it.unibo.falltohell.model.impl.gameobjects.interactable;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Item;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a potion item that can be bought by the
 * character from the merchant.
 * @author Martina Malagoli
 */
public class Potion extends BaseItem {

    private final Buff buff;

    /**
     * Initialization of the Potion class.
     * @param lv is the current level
     * @param position is the position of the potion in the level
     * @param width
     * @param height
     * @param collider is the collider associated with the potion
     * @param price is the price of the potion
     * @param buff is the buff given by the potion to the character
     */
    public Potion(final Level lv, final Vector2 position, final double width, final double height,
                  final Collider collider, final long price, final Buff buff) {
        super(lv, position, width, height, collider, price);
        this.buff = buff;
    }

    @Override
    protected void onInteract(Character character) {
        character.getBuffManager().addBuff(this.buff);
    }
}
