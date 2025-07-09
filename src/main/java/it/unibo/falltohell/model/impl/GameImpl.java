package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Game;
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
	public Level getLevel() {
		return this.level;
	}
}
