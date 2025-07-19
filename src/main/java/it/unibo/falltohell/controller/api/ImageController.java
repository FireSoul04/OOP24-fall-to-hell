package it.unibo.falltohell.controller.api;

import java.awt.*;
import java.io.IOException;

/**
 * Controller that handles the loading of an image from the file system.
 * @author Martina Malagoli
 */
public interface ImageController {

    /**
     * Method to load an image from the file system.
     * @param fileName of the image to be loaded with its file extension
     * @return Image loaded
     * @throws IOException if the path given is not one of an image
     * or doesn't exist
     */
    Image loadImage(String fileName) throws IOException;

}