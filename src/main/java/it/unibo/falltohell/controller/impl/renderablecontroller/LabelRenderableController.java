package it.unibo.falltohell.controller.impl.renderablecontroller;

import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.view.impl.renderable.LabelRenderable;

/**
 * Controller for managing the label model and its view.
 * It updates the view based on the model's state.
 * 
 * @author Casadei Lorenzo
 */
public class LabelRenderableController extends BaseRenderableController {

    /**
     * Constructor for the LabelController.
     * 
     * @param label the model object associated with this controller.
     */
    public LabelRenderableController(final Label label) {
        super(label, new LabelRenderable(label.isVisible(), label.getPosition(), label.getText()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRenderable(final GameCamera camera) {
        final LabelRenderable labelRenderable = (LabelRenderable) this.getRenderable();
        final Label label = (Label) this.getDrawable();
        labelRenderable.setText(label.getText());
        labelRenderable.setVisibility(label.isVisible());
    }
}
