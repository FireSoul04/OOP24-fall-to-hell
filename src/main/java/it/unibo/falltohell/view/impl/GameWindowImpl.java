package it.unibo.falltohell.view.impl;

import javax.swing.JFrame;

import it.unibo.falltohell.view.api.GameWindow;

/**
 * Swing implementation of the main window for the game.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei
 * @author Daniele Mastroianni
 */
public class GameWindowImpl implements GameWindow {

    private JFrame mainFrame;

    private int width;
    private int height;

    public GameWindowImpl(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.init();
    }

    private void init() {
        this.mainFrame = new JFrame("FTH");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(width, height);
        this.mainFrame.setVisible(true);
        this.mainFrame.pack();
    }

    /**
     * {@inheritDoc}
     */
    public void render() {

    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        
    }

    /**
     * {@inheritDoc}
     */
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    public int getHeight() {
        return height;
    }
}
