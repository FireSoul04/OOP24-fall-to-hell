package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.FileController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Class to handle input from files.
 * @author Martina Malagoli
 */
public class FileControllerImpl implements FileController {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> read(final String path) throws IOException {
        return Files.readAllLines(Path.of(path));
    }
}
