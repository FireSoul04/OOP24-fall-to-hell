package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.view.api.Renderable;
import it.unibo.falltohell.view.impl.SpriteRenderable;

import java.awt.*;

public class SpriteRenderableController extends BaseRenderableController {

    public SpriteRenderableController(final Drawable drawable, final Image sprite) {
        super(drawable, new SpriteRenderable(drawable.isVisible(), drawable.getPosition(), sprite));
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void updateRenderable(final GameCamera camera) {
        super.updateRenderable(camera);
        final Renderable renderable = this.getRenderable();
        final Drawable drawable = this.getDrawable();
        renderable.mirror(drawable.isMirrored());
    }
}