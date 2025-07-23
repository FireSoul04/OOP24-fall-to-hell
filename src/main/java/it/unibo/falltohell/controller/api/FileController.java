package it.unibo.falltohell.controller.api;

import java.io.IOException;
import java.util.List;

/**
 * Controller to handle input from files.
 * @author Martina Malagoli
 */
public interface FileController {

    /**
     * Method to read from the file.
     * @throws IOException when the
     */
    List<String> read(String path) throws IOException;

}
