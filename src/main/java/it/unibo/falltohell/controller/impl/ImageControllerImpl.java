package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.ImageController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Controller that handles the loading of an image from the file system.
 * @author Martina Malagoli
 */
public class ImageControllerImpl implements ImageController {

    private static final String IMAGE_DIR_PATH = "src/main/resources/images/";

    /**
     *{@inheritDoc}
     */
    @Override
    public Image loadImage(String fileName) throws IOException {
        return ImageIO.read(new File(IMAGE_DIR_PATH + fileName));
    }
}
