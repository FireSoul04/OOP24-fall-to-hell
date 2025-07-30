package it.unibo.falltohell.view.impl.renderable;

import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

import java.awt.Graphics;
import java.awt.Color;


/**
 * View class for rendering a label in the game.
 * This class extends BaseRenderable and implements the rendering logic for a label.
 * It handles the visibility, position, and text of the label.
 * @author Casadei Lorenzo
 */
public class LabelRenderable extends BaseRenderable {
    private static final int Y_OFFSET = 10;
    private String text;

    /**
     * Constructor for the LabelView.
     * @param text the label model to be represented by this view.
     * @param isVisible is a boolean that tels if the label is visibile.
     * @param position is the position of this label.
     */
    public LabelRenderable(final Boolean isVisible, final Vector2 position, final String text) {
        super(isVisible, position);
        this.text = text;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void mirror(final boolean mirroring) {
       // you cant mirror a label, so this method is empty. 
    }

    /**
     * Method to change the current text of the label.
     * @param text is the new text of the label
     */
    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public void translate(final Vector2 newPosition) {
        // Labels typically do not move, but if needed, this can be implemented.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Priority getPriority() {
        return Priority.GUI;
    }

    /**
     * Method to render the label on the screen.
     * @param g the graphics context to render the label.
     */
    @Override
    public void render(final Graphics g) {
        if (isVisible()) {
            g.setColor(Color.WHITE);
            g.drawString(text, (int) getPosition().x(), (int) getPosition().y() + Y_OFFSET);
        }
    }
}
