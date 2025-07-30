package it.unibo.falltohell;

import it.unibo.falltohell.model.api.factory.CollidableBlockFactory;
import it.unibo.falltohell.model.api.gameobject.movable.entity.Entity;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.statistic.Statistics;
import it.unibo.falltohell.model.impl.factory.CollidableBlockFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test if the blocks work as expected.
 * @author Martina Malagoli
 */
class TestBlocks {

    private Entity entity;
    private CollidableBlockFactory blockFactory;

    /**
     * Initialization of the variables used in each test.
     */
    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        this.entity = new Caster(level, Vector2.zero());
        this.blockFactory = new CollidableBlockFactoryImpl();
    }

    /**
     * Test to see if a lava block as expected:
     * checks if the block removes life to an entity if there is
     * a collision with an entity.
     */
    @Test
    void testLavaBlock() {
        final BaseCollidableBlock lavaBlock = this.blockFactory.createLavaBlock(this.entity.getLevel(), Vector2.zero());
        lavaBlock.onCollision(this.entity, Vector2.up());
        final Statistics statistics = this.entity.getStats();
        final double currentLife = statistics.getLife();
        Assertions.assertTrue(currentLife < statistics.getFullLife(), "When there is a collision "
                + "with a lava block life should be subtracted");
    }

    /**
     * Test to see if a vine block works as expected:
     * checks if the block reduces the entity speed if there is no other collision with
     * another vines block and if it repristinate correctly the entity speed on exit.
     */
    @Test
    void testVinesBlock() {
        final BaseCollidableBlock vinesBlock1 = this.blockFactory.createVinesBlock(entity.getLevel(), entity.getPosition());
        final BaseCollidableBlock vinesBlock2 = this.blockFactory.createVinesBlock(entity.getLevel(), entity.getPosition());
        final CharacterStatistics statistics = (CharacterStatistics) this.entity.getStats();
        final Vector2 initialSpeed = statistics.getInitialSpeed();
        vinesBlock1.onCollision(this.entity, Vector2.up());
        final Vector2 currentSpeed = statistics.getSpeed();
        Assertions.assertTrue(currentSpeed.magnitude() < initialSpeed.magnitude(),
                "The current speed must be lesser than initial speed");
        vinesBlock2.onCollision(this.entity, Vector2.up());
        Assertions.assertEquals(currentSpeed.magnitude(), statistics.getSpeed().magnitude(),
                "When vines debuff is already present it must not be reapplied");
        vinesBlock1.onCollisionExit(this.entity, Vector2.up());
        Assertions.assertEquals(statistics.getSpeed().magnitude(), initialSpeed.magnitude(),
                "When the collision ends the vines debuff is removed and the"
                        + "speed is reset to the one that should have been without vines");
    }
}
