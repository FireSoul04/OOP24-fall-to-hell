package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.RenderableController;
import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.view.api.Renderable;

public abstract class BaseRenderableController implements RenderableController {

    private final Drawable drawable;
    private final Renderable renderable;

    public BaseRenderableController(final Drawable drawable, final Renderable renderable) {
        this.drawable = drawable;
        this.renderable = renderable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRenderable(final GameCamera camera) {
        this.renderable.translate(this.drawable.getPosition().subtract(camera.getCameraPosition()));
        this.renderable.setVisibility(this.drawable.isVisible());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Renderable getRenderable() {
        return this.renderable;
    }

    /**
     * @return the drawable object
     */
    protected Drawable getDrawable() {
        return this.drawable;
    }
}