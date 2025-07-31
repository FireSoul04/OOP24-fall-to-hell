package it.unibo.falltohell.view.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.view.api.GameRenderer;
import it.unibo.falltohell.view.api.GameWindow;
import it.unibo.falltohell.view.impl.renderable.BaseRenderable;

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

    private static final long serialVersionUID = 1L;

    private final transient GameWindow window;
    private final transient DrawableRenderableHandler drh;

    /**
     * Create a renderer implemented with Java Swing.
     *
     * @param window of the game
     * @param drh handler for renderables
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "Window has to notify for any scaling of the window and drawable-renderable handler has to"
            + "update what to render to the screen"
    )
    public SwingGameRenderer(final GameWindow window, final DrawableRenderableHandler drh) {
        super();
        this.window = window;
        this.drh = drh;
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

        this.drh.getAllRenderables()
            .stream()
            .sorted((a, b) -> Integer.compare(b.getPriority().ordinal(), a.getPriority().ordinal()))
            .map(t -> (BaseRenderable) t)
            .forEach(t -> t.render(g));

        g2.dispose();
    }
}
