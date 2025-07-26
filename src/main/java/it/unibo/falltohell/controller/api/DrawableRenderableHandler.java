package it.unibo.falltohell.controller.api;

import it.unibo.falltohell.model.api.drawable.Drawable;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.view.api.renderable.Renderable;

import java.util.List;

/**
 * Interface that handles the drawable-renderable pair associated with a game object.
 * @author Martina Malagoli
 */
public interface DrawableRenderableHandler {

    /**
     * Method that creates a pair of Drawable-SpriteRenderableController and that puts it on the map.
     * @param drawable object that must be added to the map with its associated spriteRenderableController
     * @param fileName is the name of the image file associated to the drawable object
     */
    void linkSprite(Drawable drawable, String fileName);

    /**
     * Method that removes the pair of Drawable-RenderableController from the map.
     * @param drawable object that must be removed from the map with its associated renderableController
     */
    void removeLink(Drawable drawable);

    /**
     * Method to update all the renderable objects with their associate drawable's information.
     */
    void updateAll(GameCamera camera);

    /**
     * @return a list with all the renderable objects
     */
    List<Renderable> getAllRenderables();
}
