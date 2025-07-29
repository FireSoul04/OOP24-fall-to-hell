package it.unibo.falltohell.controller.impl.renderablecontroller;

import it.unibo.falltohell.model.api.drawable.Drawable;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.view.api.renderable.Renderable;
import it.unibo.falltohell.view.impl.renderable.SpriteRenderable;

import java.awt.Image;

/**
 * Controller that handles the update of a sprite object using the information
 * given by the drawable object associated with it.
 * @author Martina Malagoli
 */
public class SpriteRenderableController extends BaseRenderableController {

    /**
     * Initialization of the class SpriteRenderableController.
     * @param drawable associated with the sprite to be updated
     * @param sprite to be updated
     * @param priority of the sprite
     */
    public SpriteRenderableController(final Drawable drawable, final Image sprite, final Priority priority) {
        super(drawable, new SpriteRenderable(drawable.isVisible(), drawable.getPosition(), sprite, priority));
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
