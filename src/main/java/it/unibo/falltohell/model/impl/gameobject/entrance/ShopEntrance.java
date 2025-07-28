package it.unibo.falltohell.model.impl.gameobject.entrance;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.Merchant;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.listener.EnterSafeZoneListener;
import it.unibo.falltohell.model.api.listener.ExitSafeZoneListener;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

/**
 * Class that represents the entrance of the shop.
 * @author Martina Malagoli
 */
public class ShopEntrance extends BaseEntrance {

    private Optional<Merchant> merchant;

    /**
     * Initialization of the ShopEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     */
    public ShopEntrance(final Level lv, final Vector2 position) {
        super(lv, position);
        merchant = Optional.empty();
    }

    /**
     *{@inheritDoc}
     * It is used to restock the merch of the merchant when the
     * character enters the shop and to destock it when the character
     * walks away from it.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        if (other instanceof Character) {
            if (direction.equals(Vector2.right())) {
                this.merchant.orElseThrow().restock();
                this.getListenerEnter().ifPresent(EnterSafeZoneListener::call);
            } else if (direction.equals(Vector2.left())) {
                this.merchant.orElseThrow().destock();
                this.getListenerExit().ifPresent(ExitSafeZoneListener::call);
            }
        }
    }

    /**
     * Method add the merchant to this class.
     * @param merchant of the same level as this entrance
     */
    public void setMerchant(final Merchant merchant) {
        this.merchant = Optional.of(merchant);
    }
}
