package it.unibo.falltohell.controller.api;

import java.awt.*;
import java.util.Map;

/**
 * Controller that handles the loading of all the sprites images.
 * @author Martina Malagoli
 */
public interface ImageController {

    /**
     * @return a map that has the name of the class, which is also the name
     * of the image file, as the key and the value associated is the
     * corresponding image
     */
    Map<String, Image> createImageMap();
}