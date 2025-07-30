package it.unibo.falltohell.model.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.util.Vector2;

import java.util.Map;

/**
 * Class to maintain the current state of the game.
 *
 * @author Martina Malagoli
 */
public class GameDataImpl implements GameData {

    private long points;
    private Character currentCharacter;
    private final Vector2 lastSavedPosition;

    /**
     * Initialization of GameData when reading an already existent save file.
     *
     * @param points      saved on the save file
     * @param characterID is the ID of last character used before saving
     * @param characters  is the map of characters in the game
     * @param position is the last position of the character before saving
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The game data should know the current character"
    )
    public GameDataImpl(final long points, final CharacterID characterID,
            final Map<CharacterID, Character> characters, final Vector2 position) {
        this.points = points;
        this.currentCharacter = characters.get(characterID);
        this.lastSavedPosition = position;
    }

    /**
     * Initialization of GameData when starting a new game.
     * @param characters is the map of characters in the game
     */
    public GameDataImpl(final Map<CharacterID, Character> characters) {
        this(0, CharacterID.ROGUE, characters, Vector2.one().multiply(GameObject.TILE_SIZE * 3));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPoints(final long amount) {
        this.checkPositiveAmount(amount);
        this.points = this.points + amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePoints(final long amount) {
        this.checkSufficientAmount(amount);
        this.points = this.points - amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getPoints() {
        return this.points;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "The game data must know who is the new current character"
    )
    @Override
    public void changeCurrentCharacter(final Character newCharacter) {
        this.currentCharacter = newCharacter;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "The game data must let any game object know which is the current character"
    )
    @Override
    public Character getCurrentCharacter() {
        return this.currentCharacter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getLastSavedPosition() {
        return this.lastSavedPosition;
    }

    /**
     * Method to check if the amount of points is positive.
     *
     * @param amount of points
     */
    private void checkPositiveAmount(final long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount should be positive");
        }
    }

    /**
     * Method to check if the amount of points is sufficient.
     *
     * @param amount of points
     */
    private void checkSufficientAmount(final long amount) {
        this.checkPositiveAmount(amount);
        if (amount > this.points) {
            throw new IllegalArgumentException("The amount should be lesser or equal to the points");
        }
    }

}
