package it.unibo.falltohell;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.controller.impl.SaveFileControllerImpl;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.SavePoint;
import it.unibo.falltohell.model.impl.gameobjects.CharacterChanger;
import it.unibo.falltohell.model.impl.gameobjects.entrance.SpringsEntrance;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Rogue;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.test.util.LevelTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test if the save point and the character changer work as expected.
 * @author Martina Malagoli
 */
class TestSprings {
    private Interactable savePoint;
    private SaveFileController saveController;
    private GameData data;
    private Map<CharacterID, Character> characters;
    private Interactable characterChanger;
    private GameObject entrance;

    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        this.characters = new HashMap<>();
        this.characters.put(CharacterID.ROGUE, new Rogue(level, Vector2.zero()));
        this.characters.put(CharacterID.DRUID, new Druid(level, Vector2.zero()));
        this.data = new GameDataImpl(1000, CharacterID.ROGUE, this.characters);
        level.linkGameData(this.data);
        this.savePoint = new SavePoint(level, Vector2.zero(), new BoxCollider(), data);
        this.saveController = new SaveFileControllerImpl(data);
        this.characterChanger = new CharacterChanger(level, Vector2.zero(), new BoxCollider(), this.characters);
        this.entrance = new SpringsEntrance(level, Vector2.zero(), new BoxCollider());
    }

    @Test
    void TestIfSavesAndLoadCorrectly() {
        this.savePoint.interact(this.data.getCurrentCharacter());
        final GameData testData = this.saveController.load(this.characters);
        assertEquals(testData.getPoints(), this.data.getPoints());
        assertEquals(testData.getCurrentCharacter().getCharacterID(), this.data.getCurrentCharacter().getCharacterID());
    }

    @Test
    void TestCharacterChanger() {
        this.characterChanger.interact(this.data.getCurrentCharacter());
        assertEquals(CharacterID.DRUID, this.data.getCurrentCharacter().getCharacterID());
        this.characterChanger.interact(this.data.getCurrentCharacter());
        assertEquals(CharacterID.ROGUE, this.data.getCurrentCharacter().getCharacterID());
    }

    @Test
    void TestEntrance() {
        final Character character = this.data.getCurrentCharacter();
        character.setDamagedLife(character.getStats().getFullLife() / 2);
        this.entrance.onCollisionExit(character, Vector2.right());
        assertEquals(character.getStats().getFullLife(), character.getStats().getLife());
    }

}
