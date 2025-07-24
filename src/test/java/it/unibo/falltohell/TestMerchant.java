package it.unibo.falltohell;


import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.MerchantTest;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class to test if the merchant works as expected.
 * @author Martina Malagoli
 */
class TestMerchant {
    private MerchantTest merchant;
    private Character character;

    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        this.merchant = new MerchantTest(level, Vector2.zero(), 0, 0, new BoxCollider(new Dimensions(GameObject.TILE_SIZE,GameObject.TILE_SIZE)));
        this.character = new Rogue(level, Vector2.zero());
    }

    @Test
    void TestSellAndAcquisitionOfBuff() {
        final int initialNumberOfItems = this.merchant.getMerch().size();
        this.merchant.getMerch().get(0).interact(this.character);
        Assertions.assertEquals(initialNumberOfItems - 1, this.merchant.getMerch().size());
        final CharacterStatistics statistics = (CharacterStatistics)this.character.getStats();
        Assertions.assertNotEquals(0, statistics.getTemporaryLife());
    }

    //TODO --> testare entrata

}
