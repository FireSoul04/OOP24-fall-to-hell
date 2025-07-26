package it.unibo.falltohell.view.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.view.api.GameRenderer;
import it.unibo.falltohell.view.api.GameWindow;
import it.unibo.falltohell.view.impl.renderable.BaseRenderable;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Swing implementation of the renderer for the game.
 *
 * @author Davide Mancini
 */
public class SwingGameRenderer extends JPanel implements GameRenderer {

    private final GameWindow window;
    private final DrawableRenderableHandler drh;

    /**
     * Create a renderer implemented with Java Swing.
     *
     * @param window of the game
     */
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

    public static List<GameObject> g = new CopyOnWriteArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
//        g2.scale(this.window.getScale().x() / 2, this.window.getScale().y() / 2);

        this.drh.getAllRenderables()
            .stream()
			.sorted((a, b) -> Integer.compare(b.getPriority().ordinal(), a.getPriority().ordinal()))
            .map(t -> (BaseRenderable) t)
            .forEach(t -> t.render(g));

        g2.setColor(Color.RED);
        this.g.forEach(t -> g2.drawRect(
            (int) t.getPosition().add(t.getCollider().orElse(new BoxCollider()).offset()).x(),
            (int) t.getPosition().add(t.getCollider().orElse(new BoxCollider()).offset()).y(),
            (int) t.getCollider().orElse(new BoxCollider()).size().width(),
            (int) t.getCollider().orElse(new BoxCollider()).size().height()
        ));

        g2.dispose();
    }
}
