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
    
    /**
     * Constructor for the LabelController.
     * 
     * @param label the model object associated with this controller
     * @param view  the view object associated with this controller
     */
    public LabelController(final Label label) {
        super(label, new LabelView(label.isVisible(), label.getPosition(), label.getText()));
        

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRenderable(final GameCamera camera) {
        LabelView labelView = (LabelView)this.getRenderable();
        Label label = (Label)this.getDrawable();
        labelView.setText(label.getText());
        labelView.setVisibility(label.isVisible());

    }

}
