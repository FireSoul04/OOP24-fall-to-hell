package it.unibo.falltohell.controller.api;

import java.util.List;

/**
 * Controller to handle input from files.
 * @author Martina Malagoli
 */
public interface FileController {

    /**
     * Method to read from the file.
     */
    List<String> read(String path);

}
