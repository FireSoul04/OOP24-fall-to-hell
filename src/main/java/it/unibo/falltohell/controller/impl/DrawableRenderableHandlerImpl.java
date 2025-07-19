package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.api.RenderableController;
import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameCamera;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles the drawable-renderable pair associated with a game object.
 * @author Martina Malagoli
 */
public class DrawableRenderableHandlerImpl implements DrawableRenderableHandler {

    private final Map<Drawable, RenderableController> renderableControllers;

    /**
     * Initialization of the DrawableRenderableHandlerImpl.
     */
    public DrawableRenderableHandlerImpl() {
        this.renderableControllers = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void linkSprite(final Drawable drawable, final Image image) {
        this.renderableControllers.put(drawable, new SpriteRenderableController(drawable, image));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLink(final Drawable drawable) {
        this.renderableControllers.remove(drawable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAll(GameCamera camera) {
        this.renderableControllers.forEach((k, v) -> v.updateRenderable(camera));
    }
}
