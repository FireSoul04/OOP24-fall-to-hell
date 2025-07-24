package it.unibo.falltohell.view.impl;

import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.view.api.Renderable;

import java.awt.Graphics;

public abstract class BaseRenderable implements Renderable {

    private boolean mirrored;
    private boolean visible;
    private Vector2 position;

    public BaseRenderable(final boolean visibility, final Vector2 position) {
        this.mirrored = false;
        this.visible = visibility;
        this.position = position;
    }

    @Override
    public void mirror(final boolean mirroring) {
        this.mirrored = mirroring;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisibility(final boolean visibility) {
        this.visible = visibility;
    }

    @Override
    public Vector2 getPosition() {
        return this.position;
    }

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

    abstract void render(Graphics graphics);
}
