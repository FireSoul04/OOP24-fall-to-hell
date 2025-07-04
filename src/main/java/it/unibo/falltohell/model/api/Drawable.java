package it.unibo.falltohell.model.api;

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
}
