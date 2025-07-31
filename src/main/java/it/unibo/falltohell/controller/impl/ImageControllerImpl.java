package it.unibo.falltohell.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.ImageController;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Controller that handles the loading of an image from the file system.
 * @author Martina Malagoli
 */
public final class ImageControllerImpl implements ImageController {

    private static final String IMAGE_DIR_PATH = "/images/";

    /**
     *{@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "DM_EXIT",
        justification = "If a resource is not found the application must be shut down"
    )
    @Override
    public Image loadImage(final String fileName) {
        try {
            return ImageIO.read(Objects.requireNonNull(this.getClass().getResource(IMAGE_DIR_PATH + fileName)));
        } catch (final IOException e) {
            Logger.getLogger("FileLevelLogger").severe("There is no file with the given name:" + fileName);
            System.exit(1);
        }
        throw new IllegalStateException("The program should have already been stopped");
    }
}
