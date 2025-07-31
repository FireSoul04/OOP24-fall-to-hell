package it.unibo.falltohell.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.FileController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Class to handle input from files.
 * @author Martina Malagoli
 */
public final class FileControllerImpl implements FileController {

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "DM_EXIT",
        justification = "If a resource is not found the application must be shut down"
    )
    @SuppressWarnings("PMD.AssignmentInOperand")
    @Override
    public List<String> read(final String path) {
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream(path)), StandardCharsets.UTF_8)
        )) {
            final List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (final IOException e) {
            Logger.getLogger("FileControllerLogger").severe("There is no file with the given path:" + path);
            System.exit(1);
        }
        throw new IllegalStateException("The program should have already been stopped");
    }
}
