package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;

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

	/**
	 * Creates the game with the demo level.
	 */
	public GameImpl(final Level level, final GameData gameData) {
		this.level = level;
		this.gameData = gameData;
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
}
