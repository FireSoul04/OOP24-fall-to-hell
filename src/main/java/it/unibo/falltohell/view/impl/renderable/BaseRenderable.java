package it.unibo.falltohell.view.impl.renderable;

import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.view.api.renderable.Renderable;

import java.awt.Graphics;

/**
 * Class that represents objects that can be rendered.
 * @author Martina Malagoli
 */
public abstract class BaseRenderable implements Renderable {

    private boolean mirrored;
    private boolean visible;
    private Vector2 position;

    /**
     * Initialization of the BaseRenderable class.
     * @param visibility tells if the renderable should be visible
     * @param position of the renderable
     */
    public BaseRenderable(final boolean visibility, final Vector2 position) {
        this.mirrored = false;
        this.visible = visibility;
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mirror(final boolean mirroring) {
        this.mirrored = mirroring;
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
    public void setVisibility(final boolean visibility) {
        this.visible = visibility;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void translate(final Vector2 newPosition) {
        this.position = newPosition;
    }

    /**
     * @return if the renderable object is mirrored
     */
    protected boolean isMirrored() {
        return this.mirrored;
    }

    /**
     * Method that tells what and how it should be rendered.
     * @param graphics object that is needed to render other objects
     */
    public abstract void render(Graphics graphics);
}
