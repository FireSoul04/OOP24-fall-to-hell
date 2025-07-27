package it.unibo.falltohell.model.impl.drawable;

import it.unibo.falltohell.model.api.drawable.Drawable;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.util.Priority;

/**
 * Class representing a label that can be drawn on the screen.
 *
 * @author Casadei Lorenzo
 */
public class Label implements Drawable {
    private String text;
    private final Vector2 position;
    private boolean visible;
    private final boolean mirrored;

    /**
     * Constructor for a label.
     *
     * @param text     the text of the label.
     * @param position the position of the label.
     * @param visible  if the label is visible or not.
     */
    public Label(final String text, final Vector2 position, final boolean visible) {
        this.text = text;
        this.position = position;
        this.visible = visible;
        this.mirrored = false;
    }

    /**
     * @return the text of the label.
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mirror(final boolean mirroring) {
        // Not applicable for text labels, do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMirrored() {
        return mirrored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visibility) {
        this.visible = visibility;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return visible;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPosition() {
        return position;
    }

    /**
     * set the text of the label.
     *
     * @param text
     */
    public void setText(final String text) {
        this.text = text;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Priority getPriority() {
        return Priority.GUI;
    }
}
