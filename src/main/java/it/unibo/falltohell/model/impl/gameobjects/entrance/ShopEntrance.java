package it.unibo.falltohell.model.impl.gameobjects.entrance;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Merchant;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.Optional;

/**
 * Class that represents the entrance of the shop.
 * @author Martina Malagoli
 */
public class ShopEntrance extends GameObjectImpl {

    private Optional<Merchant> merchant;

    /**
     * Initialization of the ShopEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     * @param width
     * @param height
     * @param collider associated with this entrance
     */
    public ShopEntrance(final Level lv, final Vector2 position,
                        final Optional<Drawable> drawable, final Collider collider) {
        super(lv, position, false, collider, drawable);
        merchant = Optional.empty();
    }

    /**
     *{@inheritDoc}
     * It is used to restock the merch of the merchant when the
     * character enters the shop and to destock it when the character
     * walks away from it.
     */
    @Override
    public void onCollisionExit(GameObject other, Vector2 direction) {
        if (direction.equals(Vector2.right())) {
            this.merchant.ifPresent(Merchant::restock);
        } else {
            this.merchant.ifPresent(Merchant::destock);
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
