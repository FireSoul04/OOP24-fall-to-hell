package it.unibo.falltohell.controller.api;

import java.util.List;

/**
 * Controller to handle input from files.
 * @author Martina Malagoli
 */
public interface FileController {

    /**
     * Method to read from the file.
     * @param path the path to the file to be read.
     * @return a list of strings representing the lines read from the file.
     */
    List<String> read(String path);

}
