package it.unibo.falltohell.model.impl.gameobjects.entrance;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Statistics;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents the entrance of the springs.
 * @author Martina Malagoli
 */
public class SpringsEntrance extends GameObjectImpl {

    /**
     * Initialization of the SpringsEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     * @param width
     * @param height
     * @param collider associated with this entrance
     */
    public SpringsEntrance(final Level lv, final Vector2 position,
                           final double width, final double height, final Collider collider) {
        super(lv, position, width, height, false, collider);
    }

    /**
     *{@inheritDoc}
     * It is used to restore all the character's life every time the character enters the springs.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        final Statistics statistics = this.getLevel().getGameData().getCurrentCharacter().getStats();
        if (direction.equals(Vector2.right())) {
            statistics.setLife(statistics.getFullLife());
        }
    }
}
