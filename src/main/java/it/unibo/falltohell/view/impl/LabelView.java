package it.unibo.falltohell.view.impl;

import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;
import java.awt.Graphics;
import java.awt.Color;
import it.unibo.falltohell.model.impl.drawable.Label;

/**
 * View class for rendering a label in the game.
 * This class extends BaseRenderable and implements the rendering logic for a label.
 * It handles the visibility, position, and text of the label.
 * @author Casadei Lorenzo
 */
public class LabelView extends BaseRenderable {
    private final Label text;
    
    /**
     * Constructor for the LabelView.
     * @param text the label model to be represented by this view
     */
    public LabelView(final Label text) {
        super(text.isVisible(), text.getPosition());
        this.text = text;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void mirror(final boolean mirroring) {
        
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return text.isVisible();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisibility(final boolean visibility) {
        this.text.setVisible(visibility);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPosition() {
        return text.getPosition();
    }
    /**
     * Method to change the current text of the label.
     * @param text is the new text of the label
     */
    public void setText(final String text) {
        this.text.setText(text);
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
            g.drawString(text.getText(), (int) getPosition().x(), (int) getPosition().y());
        }
    }
}
