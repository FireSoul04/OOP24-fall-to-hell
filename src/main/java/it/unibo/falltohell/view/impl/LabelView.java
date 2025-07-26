package it.unibo.falltohell.view.impl;

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
public class LabelView extends BaseRenderable {
    private String text;
    
    /**
     * Constructor for the LabelView.
     * @param text the label model to be represented by this view
     */
    public LabelView(final Boolean isVisible, final Vector2 position, final String text) {
        super(isVisible, position);
        this.text = text;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void mirror(final boolean mirroring) {
        
    }

    /**
     * Method to change the current text of the label.
     * @param text is the new text of the label
     */
    public void setText(final String text) {
        this.text = text;
    }

    public void translate(final Vector2 newPosition) {
        // Labels typically do not move, but if needed, this can be implemented.
    }

    @Override
    public Priority getPriority() {
        return Priority.GUI;
    }

    /**
     * Method to render the label on the screen.
     * @param g the graphics context to render the label.
     */
    @Override
    public void render(Graphics g) {
        if(isVisible()) {
            g.setColor(Color.WHITE);
            g.drawString(text, (int) getPosition().x(), (int) getPosition().y());
        }
    }
}
