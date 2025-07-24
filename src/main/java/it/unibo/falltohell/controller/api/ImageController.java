package it.unibo.falltohell.controller.api;

import java.awt.Image;

/**
 * Controller that handles the loading of an image from the file system.
 *
 * @author Martina Malagoli
 */
public interface ImageController {

    /**
     * Method to load an image from the file system.
     *
     * @param fileName of the image to be loaded with its file extension
     * @return Image loaded
     */
    Image loadImage(String fileName);

}
