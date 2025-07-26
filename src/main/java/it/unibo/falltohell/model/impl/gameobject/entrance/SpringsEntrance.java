package it.unibo.falltohell.model.impl.gameobject.entrance;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.statistic.Statistics;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents the entrance of the springs.
 * @author Martina Malagoli
 */
public class SpringsEntrance extends BaseEntrance {

    /**
     * Initialization of the SpringsEntrance class.
     * @param lv is the level of the entrance
     * @param position is the position of the entrance in the level
     */
    public SpringsEntrance(final Level lv, final Vector2 position) {
        super(lv, position);
    }

    /**
     *{@inheritDoc}
     * It is used to restore all the character's life every time the character enters the springs.
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {
        if (other instanceof Character) {
            final Statistics statistics = this.getLevel().getGameData().getCurrentCharacter().getStats();
            if (direction.equals(Vector2.left())) {
                statistics.setLife(statistics.getFullLife());
                this.getListener().call();
            } else if (direction.equals(Vector2.right())) {
                this.getListener().call();
            }
        }
    }
}
