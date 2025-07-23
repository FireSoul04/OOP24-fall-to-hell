package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Class to that saves the current state of the game in the save file.
 * @author Martina Malagoli
 */
public class SaveFileControllerImpl implements SaveFileController {

    private static final String FILE_NAME = "saveFile.txt";
    private static final String DIR_PATH = System.getProperty("user.home") + File.separator + "FTH" + File.separator;
    private final GameData data;
    private final Logger logger;

    /**
     * Initialization of the SaveFileControllerImpl.
     * @param data is what has to be saved on the file
     */
    public SaveFileControllerImpl(final GameData data) {
        this.data = data;
        this.logger = Logger.getLogger("SaveFileControllerLogger");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save() {
        final File saveDir = new File(DIR_PATH);
        if (!saveDir.exists() || !saveDir.isDirectory()) {
            final boolean savedDir = saveDir.mkdir();
            try {
                final boolean newSaveFile = new File(DIR_PATH + FILE_NAME).createNewFile();
            } catch (final IOException e) {
                this.logger.severe("The save file was not created correctly:" + e);
            }
        }
        try (
                final BufferedWriter saveOutput = new BufferedWriter(new FileWriter(DIR_PATH + FILE_NAME)
                )
        ) {
            final Character character = this.data.getCurrentCharacter();
            saveOutput.write(String.valueOf(this.data.getPoints()));
            saveOutput.newLine();
            saveOutput.write(character.getCharacterID().name());
            saveOutput.newLine();
            saveOutput.write(String.valueOf(character.getPosition().x()));
            saveOutput.newLine();
            saveOutput.write(String.valueOf(character.getPosition().y()));
        } catch (final IOException e) {
            this.logger.severe("Something went wrong while saving:" + e);
        }
    }

}
