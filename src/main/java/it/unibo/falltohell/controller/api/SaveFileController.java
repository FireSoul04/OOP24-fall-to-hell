package it.unibo.falltohell.controller.api;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

import java.util.Map;

/**
 * Controller that saves the current state of the game in the save file.
 * @author Martina Malagoli
 */
public interface SaveFileController {

    /**
     * Method to save the current state of the game in the save file.
     */
    void save();

    /**
     * @return the game data loaded from the save file.
     */
    GameData load(Map<Character.CharacterID, Character> characters);

}
