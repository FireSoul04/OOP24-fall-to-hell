package it.unibo.falltohell.view.api.renderable;

import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * Interface that represents objects that can be rendered.
 * @author Martina Malagoli
 */
public interface Renderable {

    /**
     * Method to mirror along the x-axis the image associated to the renderable object.
     * @param mirroring tells if the image associated to the renderable object should be mirrored
     */
    void mirror(boolean mirroring);

    /**
     * @return if the image associated to the renderable object is visible
     */
    boolean isVisible();

    /**
     * @param visibility tells if the image associated to the renderable object should be visible
     */
    void setVisibility(boolean visibility);

    /**
     * @return the current position of the image associated to the renderable object
     */
    Vector2 getPosition();

    /**
     * Method to change the current position of the image associated to the renderable object.
     * @param newPosition is the new position of the image associated to the renderable object
     */
    void translate(Vector2 newPosition);

    /**
     * @return the priority of the image that has to be rendered
     */
    Priority getPriority();

}
