package it.unibo.falltohell;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.interactable.Interactable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.model.impl.gameobject.interactable.CharacterChanger;
import it.unibo.falltohell.model.impl.gameobject.entrance.SpringsEntrance;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.test.util.SaveFileControllerTest;
import it.unibo.falltohell.test.util.SavePointTest;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.test.util.LevelTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class to test if the save point and the character changer work as expected.
 * @author Martina Malagoli
 */
class TestSprings {
    private Interactable savePoint;
    private SaveFileControllerTest saveController;
    private GameData data;
    private Map<CharacterID, Character> characters;
    private Interactable characterChanger;
    private GameObject entrance;

    /**
     * Initialization of the variables used in each test.
     */
    @BeforeEach
    void initialization() {
        final Level level = new LevelTest();
        this.characters = new EnumMap<>(CharacterID.class);
        this.characters.put(CharacterID.ROGUE, new Rogue(level, Vector2.zero()));
        this.characters.put(CharacterID.DRUID, new Druid(level, Vector2.zero()));
        this.data = new GameDataImpl(1000, CharacterID.ROGUE, this.characters, Vector2.one());
        level.linkGameData(this.data);
        this.savePoint = new SavePointTest(level, Vector2.zero(), new BoxCollider());
        this.saveController = new SaveFileControllerTest();
        this.characterChanger = new CharacterChanger(level, Vector2.zero(), new BoxCollider(), this.characters);
        this.entrance = new SpringsEntrance(level, Vector2.zero());
    }

    /**
     * Tests if the interaction with the save point works correctly:
     * game data is saved and loaded as expected.
     */
    @Test
    void testIfSavesAndLoadCorrectly() {
        this.savePoint.interact(this.data.getCurrentCharacter());
        final GameData testData = this.saveController.load(this.characters);
        Assertions.assertEquals(testData.getPoints(), this.data.getPoints(),
                "Points saved and loaded must be the same of the game data before save");
        Assertions.assertEquals(testData.getCurrentCharacter().getCharacterID(), this.data.getCurrentCharacter().getCharacterID(),
                "The ID of the current character must be the same of the game data before save");
        Assertions.assertEquals(testData.getLastSavedPosition().x(), this.data.getCurrentCharacter().getPosition().x(),
                "The x-axis position of the character must be the same of the game data before save");
        Assertions.assertEquals(testData.getLastSavedPosition().y(), this.data.getCurrentCharacter().getPosition().y(),
                "The y-axis position of the character must be the same of the game data before save");
        this.saveController.removeTestFile();
    }

    /**
     * Tests if the character changer works correctly:
     * checks if interacting with the character changer changes the character to the next one
     * and, if the character is the last one, the next is the first.
     */
    @Test
    void testCharacterChanger() {
        this.characterChanger.interact(this.data.getCurrentCharacter());
        Assertions.assertEquals(CharacterID.DRUID, this.data.getCurrentCharacter().getCharacterID(),
                "The next character must be the Druid");
        this.characterChanger.interact(this.data.getCurrentCharacter());
        Assertions.assertEquals(CharacterID.ROGUE, this.data.getCurrentCharacter().getCharacterID(),
                "The next character must be the first one");
    }

    /**
     * Tests if the spring entrance works correctly:
     * checks if life and mana are refilled when the character enters.
     */
    @Test
    void testEntrance() {
        final Character character = this.data.getCurrentCharacter();
        final CharacterStatistics statistics = (CharacterStatistics) character.getStats();
        character.setDamagedLife(statistics.getFullLife() / 2);
        character.subMana(statistics.getInitialMana() / 2);
        this.entrance.onCollisionExit(character, Vector2.left());
        Assertions.assertEquals(statistics.getFullLife(), statistics.getLife(),
            "Life must be at max after entering the springs");
        Assertions.assertEquals(statistics.getInitialMana(), statistics.getMana(),
            "Mana must be at max after entering the springs");
    }

}
