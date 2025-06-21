package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;

/**
 * Class to maintain the current state of the game.
 * @author Martina Malagoli
 */
public class GameDataImpl implements GameData {

    private long points;
    private CharacterID currentCharacterID;
    //TODO add reference to the current level

    /**
     * Initialization of GameData when reading an already existent save file.
     * @param points saved on the save file
     * @param characterID is the ID of last character used before saving
     */
    public GameDataImpl(final long points, final CharacterID characterID) {
        this.points = points;
        this.currentCharacterID = characterID;
    }

    /**
     * Initialization of GameData when starting a new game.
     */
    public GameDataImpl() {
        this(0, CharacterID.ROGUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPoints(final long amount) {
        this.checkAmount(amount);
        this.points = this.points + amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePoints(final long amount) {
        this.checkAmount(amount);
        if (this.points >= amount) {
            this.points = this.points - amount;
        }
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
    @Override
    public void changeCurrentCharacter(final Character newCharacter) {
        this.currentCharacterID = newCharacter.getCharacterID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterID getCurrentCharacterID() {
        return this.currentCharacterID;
    }

    /**
     * Method to check if the amount of points is positive.
     * @param amount of points
     */
    private void checkAmount(final long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount should be positive");
        }
    }
}
