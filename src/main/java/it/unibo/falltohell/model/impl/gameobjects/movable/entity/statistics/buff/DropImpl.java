package it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Block;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Buff;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.Drop;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

public class DropImpl extends MovableImpl implements Drop {

    private static final Vector2 VELOCITY = new Vector2(10, 0);
    private static final Dimensions DIMENSIONS = new Dimensions(10, 10);
    private final Buff buff;

    public DropImpl(final Level lv, final Vector2 position, final Buff buff) {
        super(lv, position, DIMENSIONS.width(), DIMENSIONS.height(), VELOCITY.x(), VELOCITY.y(),
                new BoxCollider(Vector2.zero(), DIMENSIONS));
        this.buff = buff;
    }

    /**
     * {@inheritDoc}
     */
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other instanceof Character) {
            var character = (Character) other;
            character.getBuffManager().addBuff(this.buff);
            super.getLevel().removeGameObject(this);
        }
        if (other instanceof Block && direction.y() < 0) {
            super.setSpeedX(0);
        }
    }

}
