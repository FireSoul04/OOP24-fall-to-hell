package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.Level;

public class GameImpl implements Game {

    private final Level level;

    /**
     * Creates the game with the demo level.
     */
	public GameImpl() {
		this.level = new LevelImpl();
	}

	/**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Level getLevel() {
        return this.level;
    }
}
