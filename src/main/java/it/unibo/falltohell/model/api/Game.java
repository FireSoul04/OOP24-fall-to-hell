package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.level.Level;

import java.util.Map;

/**
 * Interface for the game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public interface Game {

    /**
     * @return current level playing
     */
    Level getLevel();

    /**
     * @return data of the game
     */
    GameData getGameData();

    /**
     * @return all the characters of the game
     */
    Map<CharacterID, Character> getCharacters();
}
