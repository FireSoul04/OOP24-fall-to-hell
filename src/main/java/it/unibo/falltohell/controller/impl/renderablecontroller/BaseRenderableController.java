package it.unibo.falltohell.controller.impl.renderablecontroller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.renderablecontroller.RenderableController;
import it.unibo.falltohell.model.api.drawable.Drawable;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.view.api.renderable.Renderable;

/**
 * Controller that handles the update of a renderable object using the information
 * given by the drawable object associated with it.
 * @author Martina Malagoli
 */
public abstract class BaseRenderableController implements RenderableController {

    private final Drawable drawable;
    private final Renderable renderable;

    /**
     * Initialization of the BaseRenderableController class.
     * @param drawable associated with the renderable to be updated
     * @param renderable to be updated
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The controller must have access to both the drawable and the renderable "
            + "objects to be able to handle their communication"
    )
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
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "The view should know how to render the renderable object"
    )
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
