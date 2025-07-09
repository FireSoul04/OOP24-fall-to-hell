package it.unibo.falltohell.view.impl;

import javax.swing.*;

import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.view.api.GameWindow;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Swing implementation of the main window for the game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 */
public class GameWindowImpl implements GameWindow {

    private static final double INITIAL_SCREEN_RATIO = 2.0 / 3.0;

    private final SwingGameRenderer renderer;
	private final int width;
    private final int height;

    private Vector2 scale;

    public GameWindowImpl(final int width, final int height) {
        super();
        this.width = width;
        this.height = height;
        this.scale = Vector2.one();
        this.renderer = new SwingGameRenderer(this);
        this.initializeWindow(width, height);
    }

    private void initializeWindow(final int width, final int height) {
	    final JFrame mainFrame = new JFrame("FTH");
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.scale = new Vector2(screenSize.getWidth() / width, screenSize.getHeight() / height)
            .multiply(INITIAL_SCREEN_RATIO);
        final Point startPosition = new Point(
            (int) (screenSize.getWidth() - width * this.scale.x()) / 2,
            (int) (screenSize.getHeight() - height * this.scale.y()) / 2
        );
        mainFrame.setLocation(startPosition);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(width, height);
        mainFrame.getContentPane().add(this.renderer);
        mainFrame.getContentPane()
            .setPreferredSize(new Dimension((int) (width * this.scale.x()), (int) (height * this.scale.y())));
        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setMinimumSize(mainFrame.getSize());
        mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent e) {
                final Dimension d = ((JFrame) e.getComponent()).getContentPane().getSize();
                scale = new Vector2(d.getWidth() / (double) width, d.getHeight() / (double) height);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        this.renderer.render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.renderer.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return this.width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimensions getDimensions() {
        return new Dimensions(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getScale() {
        return this.scale;
    }
}
