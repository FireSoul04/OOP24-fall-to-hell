package it.unibo.falltohell.model.impl.gameobject.interactable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.buff.Buff;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.buff.LifeBuff;
import it.unibo.falltohell.model.impl.buff.ManaBuff;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents a potion item that can be bought by the
 * character from the merchant.
 * @author Martina Malagoli
 */
public class Potion extends BaseItem {

    private static final long POTION_DURATION = 12 * 1000;
    private static final long LIFE_MANA_POTION_DURATION = 60 * 1000;
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
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The potion must know which buff to apply"
    )
    public Potion(final Level lv, final Vector2 position, final Collider collider,
                  final long price, final Buff buff, final String fileName) {
        super(lv, position, collider, price, fileName);
        this.buff = buff;
    }

    /**
     * {@inheritDoc}
     * It gives to the character that interacts with the potion a buff.
     */
    @Override
    protected void onInteract(final Character character) {
        final String name = "potion_buff" + this.hashCode();
        if (buff instanceof LifeBuff || buff instanceof ManaBuff) {
            character.getBuffManager().addBuff(this.buff, LIFE_MANA_POTION_DURATION, name);
        } else {
            character.getBuffManager().addBuff(this.buff, POTION_DURATION, name);
        }
    }
}
