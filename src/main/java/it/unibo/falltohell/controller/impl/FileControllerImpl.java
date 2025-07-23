package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.FileController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class to handle input from files.
 * @author Martina Malagoli
 */
public class FileControllerImpl implements FileController {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> read(final String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (final IOException e) {
            Logger.getLogger("FileLevelLogger").severe("There is no file with the given name");
            System.exit(1);
        }
        throw new IllegalStateException("The program should have already been stopped");
    }
}
