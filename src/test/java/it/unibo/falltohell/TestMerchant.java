package it.unibo.falltohell;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.gameobjects.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.gameobjects.entrance.ShopEntrance;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.MerchantTest;
import it.unibo.falltohell.util.Vector2;

import java.util.HashMap;
import java.util.Map;

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
        final Map<CharacterID, Character> characters = new HashMap<>();
        characters.put(CharacterID.ROGUE, new Rogue(level, Vector2.zero()));
        characters.put(CharacterID.DRUID, new Druid(level, Vector2.zero()));
        level.linkGameData(new GameDataImpl(1000, CharacterID.ROGUE, characters, Vector2.zero()));
        this.merchant = new MerchantTest(level, Vector2.zero(), new BoxCollider());
        this.character = new Rogue(level, Vector2.zero());
    }

    @Test
    void TestSellAndAcquisitionOfBuff() {
        final int initialNumberOfItems = this.merchant.getMerch().size();
        final long initialPoints = this.merchant.getLevel().getGameData().getPoints();
        this.merchant.getMerch().get(0).interact(this.character);
        this.merchant.update();
        Assertions.assertEquals(initialNumberOfItems - 1, this.merchant.getMerch().size());
        final CharacterStatistics statistics = (CharacterStatistics)this.character.getStats();
        Assertions.assertNotEquals(0, statistics.getTemporaryLife());
        Assertions.assertNotEquals(initialPoints, this.merchant.getLevel().getGameData().getPoints());
    }

    @Test
    void TestShopEntrance() {
        final BaseEntrance entrance = new ShopEntrance(this.merchant.getLevel(), Vector2.zero(), new BoxCollider());
        Assertions.assertEquals(0, this.merchant.getMerch().size());
        entrance.onCollisionExit(this.character, Vector2.left());
        Assertions.assertEquals(3, this.merchant.getMerch().size());
        entrance.onCollisionExit(this.character, Vector2.right());
        Assertions.assertEquals(0, this.merchant.getMerch().size());

    }

}
