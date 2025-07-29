package it.unibo.falltohell.test.util;

import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.block.LavaBlock;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class used for tests that represents a type of block that deals damage continuously
 * to the character and to enemies while they touch it from above.
 * @author Martina Malagoli
 */
public class LavaBlockTest extends LavaBlock {

    private static final double DAMAGE = 0.2;

    /**
     * Initialization of the LavaBlockTest class.
     * @param lv is the level of the block
     */
    public LavaBlockTest(final Level lv) {
        super(lv, Vector2.zero(), new BoxCollider(), "test.png", Vector2.zero());
    }

    /**
     * @return the damage inflicted to the entity
     */
    public double getDamage() {
        return DAMAGE;
    }
}
