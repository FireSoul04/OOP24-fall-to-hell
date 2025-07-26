package it.unibo.falltohell.controller.api.renderablecontroller;

import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.view.api.renderable.Renderable;

/**
 * Controller that handles the update of a renderable object using the information
 * given by the drawable object associated with it.
 * @author Martina Malagoli
 */
public interface RenderableController {

    /**
     * Method that updates an existent renderable object using the information
     * given by the drawable object associated with it.
     * @param camera of the game
     */
    void updateRenderable(GameCamera camera);

    /**
     * @return the renderable object
     */
    Renderable getRenderable();

}
