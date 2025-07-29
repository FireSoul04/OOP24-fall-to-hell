package it.unibo.falltohell.controller.api;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;

import java.util.Map;

/**
 * Controller that saves the current state of the game in the save file.
 * @author Martina Malagoli
 */
public interface SaveFileController {

    /**
     * Method to save the current state of the game in the save file.
     * @param data the object representing the current state of the game to be saved
     * @return if the saving happened correctly
     */
    boolean save(GameData data);

    /**
     * @param characters a map of character IDs to  objects used to restore character-related state
     * @return the game data loaded from the save file.
     */
    GameData load(Map<Character.CharacterID, Character> characters);

}
