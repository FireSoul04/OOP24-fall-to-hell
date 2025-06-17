package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.GameData;

public class GameDataImpl implements GameData {

    private long points;
    private Character currentCharacter;

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
        this.currentCharacter = newCharacter;
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
