package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.model.api.GameData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to that saves the current state of the game in the save file.
 * @author Martina Malagoli
 */
public class SaveFileControllerImpl extends FileControllerImpl implements SaveFileController {

    private static final String FILE_NAME = "saveFile.txt";
    private static final String DIR_PATH = System.getProperty("user.home") + File.separator + "FTH" + File.separator;
    private final GameData data;

    /**
     * Initialization of the SaveFileControllerImpl.
     * @param data is what has to be saved on the file
     */
    public SaveFileControllerImpl(final GameData data) {
        super(DIR_PATH + FILE_NAME);
        this.data = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        final File saveDir = new File(DIR_PATH);
        if (!saveDir.exists() || !saveDir.isDirectory()) {
            final boolean savedDir = saveDir.mkdir();
            //TODO --> handle when it doesn't work correctly
            try {
                final boolean newSaveFile = new File(DIR_PATH + FILE_NAME).createNewFile();
                //TODO --> handle when it doesn't work correctly
            } catch (IOException e) {
                //TODO --> use logger
            }
        }
        try (
                final BufferedWriter saveOutput = new BufferedWriter(new FileWriter(DIR_PATH + FILE_NAME)
                )
        ) {
            saveOutput.write(String.valueOf(this.data.getPoints()));
            saveOutput.newLine();
            saveOutput.write(this.data.getCurrentCharacterID().name());
            //TODO save the current level
        } catch (IOException e) {
            //TODO --> use logger
        }
    }

}
