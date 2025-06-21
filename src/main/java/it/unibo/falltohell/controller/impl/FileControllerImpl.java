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

    private final String path;

    /**
     * Initialization of the FileControllerImpl.
     * @param path of the file
     */
    public FileControllerImpl(final String path) {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPath() {
        return this.path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> read() throws IOException {
        return Files.readAllLines(Path.of(this.path));
    }
}
