package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.view.impl.LabelView;

/**
 * Controller for managing the label model and its view.
 * It updates the view based on the model's state and camera position.
 * 
 * @author Casadei Lorenzo
 */
public class LabelController extends BaseRenderableController {
    private final Label label;
    private final LabelView view;

    /**
     * Constructor for the LabelController.
     * 
     * @param label the model object associated with this controller
     * @param view  the view object associated with this controller
     */
    public LabelController(final Label label, final LabelView view) {
        super(label, view);
        this.view = view;
        this.label = label;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRenderable(final GameCamera camera) {
        super.updateRenderable(camera);
        view.setText(label.getText());
    }
}
