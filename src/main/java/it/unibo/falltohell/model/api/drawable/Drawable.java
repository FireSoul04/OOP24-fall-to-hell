package it.unibo.falltohell.model.api.drawable;

import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.util.Priority;

/**
 * Interface to handle information about the rendering of a drawable object.
 * @author Martina Malagoli
 */
public interface Drawable {

    /**
     * Method to mirror a drawable object.
     * @param mirroring tells if the drawable object should be mirrored
     */
    void mirror(boolean mirroring);

    /**
     * @return if a drawable object is mirrored.
     */
    boolean isMirrored();

    /**
     * Method to set the visibility of a drawable object.
     * @param visibility tells if the drawable object should be visible
     */
    void setVisible(boolean visibility);

    /**
     *
     * @return if a drawable object is visible
     */
    boolean isVisible();

    /**
     * @return the current position of the game object associated
     * with the drawable object.
     */
    Vector2 getPosition();

    /**
     * @return the priority of the drawable object when it has to be rendered
     */
    Priority getPriority();
}
