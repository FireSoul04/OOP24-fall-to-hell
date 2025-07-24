package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;

import java.util.Collections;
import java.util.Map;

/**
 * Implementation of the logic of the game.
 * This class initialize all logic relevant parameters, like the current level.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public class GameImpl implements Game {

    private final Level level;
    private final GameData gameData;
    private final Map<CharacterID, Character> characters;

    /**
     * Creates the game with the demo level.
     */
    public GameImpl(final Level level, final GameData gameData, final Map<CharacterID, Character> characters) {
        this.level = level;
        this.gameData = gameData;
        this.characters = characters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Level getLevel() {
        return this.level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameData getGameData() {
        return this.gameData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<CharacterID, Character> getCharacters() {
        return Collections.unmodifiableMap(this.characters);
    }
}
