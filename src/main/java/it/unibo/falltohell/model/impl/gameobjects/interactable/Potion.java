package it.unibo.falltohell.model.impl.gameobjects.interactable;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.api.physics.Collider;
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
     * @param collider is the collider associated with the potion
     * @param price is the price of the potion
     * @param buff is the buff given by the potion to the character
     * @param fileName is the name of the image file associated to the potion
     */
    public Potion(final Level lv, final Vector2 position, final Collider collider,
                  final long price, final Buff buff, final String fileName) {
        super(lv, position, collider, price, fileName);
        this.buff = buff;
    }

    @Override
    protected void onInteract(final Character character) {
        character.getBuffManager().addBuff(this.buff);
    }
}
