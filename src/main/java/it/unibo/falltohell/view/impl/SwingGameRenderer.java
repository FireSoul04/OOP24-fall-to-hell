package it.unibo.falltohell.view.impl;

import it.unibo.falltohell.view.api.GameRenderer;
import it.unibo.falltohell.view.api.GameWindow;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Swing implementation of the renderer for the game.
 *
 * @author Davide Mancini
 */
public class SwingGameRenderer extends JPanel implements GameRenderer {

	private final GameWindow window;

	/**
	 * Create a renderer implemented with Java Swing.
	 *
	 * @param window of the game
	 */
	public SwingGameRenderer(final GameWindow window) {
		super();
		this.window = window;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render() {
		this.repaint();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.scale(this.window.getScale().x(), this.window.getScale().y());

		// Two rectangles are drawn for debugging purpose only
		g2.setColor(Color.WHITE);
		g2.fillRect(50, 50, 50, 50);
		g2.fillRect(this.window.getWidth() - 100, this.window.getHeight() - 100, 50, 50);
		//

		g2.dispose();
	}
}
