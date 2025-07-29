package it.unibo.falltohell.view.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.view.api.GameWindow;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

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
    private JFrame mainFrame;

    private Vector2 scale;

    /**
     * Creates a window for the game with the specified width and height.
     * Window starts at the center of the screen with a 2/3 ratio of the screen size.
     *
     * @param width of the window
     * @param height of the window
     * @param keyListener that notifies for key events
     * @param drh handler for every renderable
     */
    public GameWindowImpl(final int width, final int height, final KeyListener keyListener,
                          final DrawableRenderableHandler drh) {
        super();
        this.width = width;
        this.height = height;
        this.scale = Vector2.one();
        this.renderer = new SwingGameRenderer(this, drh);
        this.initializeWindow(width, height, keyListener);
    }

    private void initializeWindow(final int width, final int height, final KeyListener keyListener) {
        mainFrame = new JFrame("FTH");
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.scale = new Vector2(screenSize.getWidth() / width, screenSize.getHeight() / height)
            .multiply(INITIAL_SCREEN_RATIO);
        final Point startPosition = new Point(
            (int) (screenSize.getWidth() - width * this.scale.x()) / 2,
            (int) (screenSize.getHeight() - height * this.scale.y()) / 2
        );
        mainFrame.addKeyListener(keyListener);
        mainFrame.setLocation(startPosition);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(width, height);
        mainFrame.getContentPane()
            .setPreferredSize(new Dimension((int) (width * this.scale.x()), (int) (height * this.scale.y())));
        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setMinimumSize(mainFrame.getSize());
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final Dimension d = ((JFrame) e.getComponent()).getContentPane().getSize();
                scale = new Vector2(d.getWidth() / width, d.getHeight() / height);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameTitle(final String title) {
        this.mainFrame.setTitle(title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showMenu(final JPanel menuPanel) {
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().add(menuPanel);
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGame() {
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().add(this.renderer);
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void requestFocusOnWindow() {
        this.mainFrame.requestFocus();
    }
}
