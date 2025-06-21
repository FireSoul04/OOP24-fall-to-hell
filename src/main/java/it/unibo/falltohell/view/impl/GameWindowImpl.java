package it.unibo.falltohell.view.impl;

import javax.swing.*;

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
 * @author Daniele Mastroianni
 */
public class GameWindowImpl extends JPanel implements GameWindow {

    private JFrame mainFrame;

    private int width;
    private int height;

    private double scaleX;
    private double scaleY;

    public GameWindowImpl(final int width, final int height) {
        super();
        this.scaleX = 1.0;
        this.scaleY = 1.0;
        this.init(width, height);
    }

    private void init(final int width, final int height) {
        this.mainFrame = new JFrame("FTH");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setSize(width, height);
        this.mainFrame.getContentPane().add(this);
        this.mainFrame.getContentPane()
            .setPreferredSize(new Dimension((int) (width * this.scaleX), (int) (height * this.scaleY)));
        this.mainFrame.setVisible(true);
        this.mainFrame.pack();
        this.mainFrame.setMinimumSize(this.mainFrame.getSize());
        this.mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent e) {
                final Dimension d = ((JFrame) e.getComponent()).getContentPane().getSize();
                scaleX = d.getWidth() / (double) width;
                scaleY = d.getHeight() / (double) height;
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    public void render() {
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
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
        g2.scale(this.scaleX, this.scaleY);
        g2.setColor(Color.WHITE);
        g2.fillRect(50, 50, 50, 50);
        g2.fillRect(this.getWidth() - 100, this.getHeight() - 100, 50, 50);
        g2.dispose();
    }
}
