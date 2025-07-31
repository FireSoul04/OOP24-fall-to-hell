package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.api.renderablecontroller.RenderableController;
import it.unibo.falltohell.controller.impl.renderablecontroller.LabelRenderableController;
import it.unibo.falltohell.controller.impl.renderablecontroller.SpriteRenderableController;
import it.unibo.falltohell.model.api.drawable.Drawable;
import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.view.api.renderable.Renderable;

import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        this.renderableControllers = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void linkSprite(final Drawable drawable, final String fileName) {
        final Image image = new ImageControllerImpl().loadImage(fileName);
        this.renderableControllers.put(drawable, new SpriteRenderableController(drawable, image, drawable.getPriority()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void linkLabel(final Label label) {
        this.renderableControllers.put(label, new LabelRenderableController(label));
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
        this.renderableControllers.values().forEach((rc) -> rc.updateRenderable(camera));
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

    /**
     *{@inheritDoc}
     */
    @Override
    public void removeAllLinks() {
        this.renderableControllers.keySet().forEach(this::removeLink);
    }
}
