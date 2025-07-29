package it.unibo.falltohell;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.gameobject.entrance.ShopEntrance;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.LevelTest;
import it.unibo.falltohell.test.util.MerchantTest;
import it.unibo.falltohell.util.Vector2;

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

    /**
     * Initialization of the variables used in the tests.
     */
    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        this.character = new Rogue(level, Vector2.zero());
        final Map<CharacterID, Character> characters = Map.of(CharacterID.ROGUE, this.character);
        level.linkGameData(new GameDataImpl(1000, CharacterID.ROGUE, characters, Vector2.zero()));
        this.merchant = new MerchantTest(level, Vector2.zero(), new BoxCollider());
    }

    /**
     * Tests if the purchase happens if the character has enough points and if potions apply buffs
     * correctly when purchased.
     */
    @Test
    void testSellWithPointsAndAcquisitionOfBuff() {
        this.merchant.restock();
        final int initialNumberOfItems = this.merchant.getMerch().size();
        final long initialPoints = this.merchant.getLevel().getGameData().getPoints();
        this.merchant.getMerch().get(0).interact(this.character);
        this.merchant.update();
        Assertions.assertEquals(initialNumberOfItems - 1, this.merchant.getMerch().size(),
                "The number of items should be the on");
        final CharacterStatistics statistics = (CharacterStatistics) this.character.getStats();
        Assertions.assertNotEquals(0, statistics.getTemporaryLife());
        Assertions.assertNotEquals(initialPoints, this.merchant.getLevel().getGameData().getPoints());
    }

    /**
     * Test if there is no purchase if the character has not enough points.
     */
    @Test
    void testSellWithoutPoints() {
        this.merchant.restock();
        final Level level = this.merchant.getLevel();
        final GameData gameData = new GameDataImpl(Map.of(CharacterID.CASTER, new Caster(level, Vector2.zero())));
        level.linkGameData(gameData);
        final long initialPoints = gameData.getPoints();
        final int initialQuantity = this.merchant.getMerch().size();
        this.merchant.getMerch().get(0).interact(this.character);
        Assertions.assertEquals(initialPoints, gameData.getPoints());
        Assertions.assertEquals(initialQuantity, this.merchant.getMerch().size());
    }

    /**
     * Tests if the computed position of potions is correct.
     */
    @Test
    void testPositionOfPotions() {
        this.merchant.restock();
        final Vector2 position1 = this.merchant.getMerch().get(0).getPosition();
        final Vector2 position2 = this.merchant.getMerch().get(1).getPosition();
        final Vector2 position3 = this.merchant.getMerch().get(2).getPosition();
        Assertions.assertEquals(0, Double.compare(Math.abs(this.merchant.getPosition().x() - position1.x()),
                GameObject.TILE_SIZE * this.merchant.getDistanceFromItems()));
        Assertions.assertEquals(0, Double.compare(Math.abs(position1.x() - position2.x()),
                GameObject.TILE_SIZE * this.merchant.getDistanceFromItems()));
        Assertions.assertEquals(0, Double.compare(Math.abs(position2.x() - position3.x()),
                GameObject.TILE_SIZE * this.merchant.getDistanceFromItems()));
    }

    /**
     * Tests if restock and destock work as expected.
     */
    @Test
    void testShopEntrance() {
        final ShopEntrance entrance = new ShopEntrance(this.merchant.getLevel(), Vector2.zero());
        entrance.setMerchant(this.merchant);
        Assertions.assertEquals(0, this.merchant.getMerch().size());
        entrance.onCollisionExit(this.character, Vector2.right());
        Assertions.assertEquals(3, this.merchant.getMerch().size());
        entrance.onCollisionExit(this.character, Vector2.left());
        Assertions.assertEquals(0, this.merchant.getMerch().size());
    }

}
