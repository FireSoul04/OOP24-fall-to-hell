package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.api.RenderableController;
import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.view.api.Renderable;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
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
    public void linkSprite(final Drawable drawable, final String fileName) {
        Image image = new ImageControllerImpl().loadImage(fileName);
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
    public void updateAll(final GameCamera camera) {
        this.renderableControllers.forEach((k, v) -> v.updateRenderable(camera));
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Renderable> getAllRenderables() {
        return this.renderableControllers.values()
                .stream()
                .map(RenderableController::getRenderable)
                .toList();
    }
}
