package it.unibo.falltohell.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.util.Vector2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class to that saves the current state of the game in the save file.
 * @author Martina Malagoli
 */
public class SaveFileControllerImpl implements SaveFileController {

    private static final String FILE_NAME = "saveFile.txt";
    private static final String DIR_PATH = System.getProperty("user.home") + File.separator + "FTH" + File.separator;
    private final String fileName;
    private final Logger logger;

    /**
     * Default initialization of the SaveFileControllerImpl class.
     */
    public SaveFileControllerImpl() {
        this(FILE_NAME);
    }

    /**
     * Initialization of the SaveFileControllerImpl class.
     * @param fileName is the name of the file where to save
     */
    public SaveFileControllerImpl(final String fileName) {
        this.logger = Logger.getLogger("SaveFileControllerLogger");
        this.fileName = fileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final GameData data) {
        if (this.checkExistenceOfFile()) {
            this.createNewSaveFile();
        }
        try (
            BufferedWriter saveOutput = new BufferedWriter(
                new FileWriter(DIR_PATH + this.fileName, StandardCharsets.UTF_8)
            )
        ) {
            final Character character = data.getCurrentCharacter();
            saveOutput.write(String.valueOf(data.getPoints()));
            saveOutput.newLine();
            saveOutput.write(character.getCharacterID().name());
            saveOutput.newLine();
            saveOutput.write(String.valueOf(character.getPosition().x()));
            saveOutput.newLine();
            saveOutput.write(String.valueOf(character.getPosition().y()));
            return true;
        } catch (final IOException e) {
            this.logger.severe("Something went wrong while saving:" + e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "DM_EXIT",
        justification = "The save point is already checked, if it is not found, the file might be deleted after the check"
    )
    @Override
    public GameData load(final Map<CharacterID, Character> characters) {
        if (this.checkExistenceOfFile()) {
            final List<String> fileLines = new ArrayList<>();
            try {
                fileLines.addAll(Files.readAllLines(Path.of(DIR_PATH + fileName)));
            } catch (final IOException e) {
                Logger.getLogger("SaveFileLogger").severe("There is no save file");
                System.exit(1);
            }
            final long points = Long.parseLong(fileLines.get(0));
            final CharacterID currentCharacterID = Enum.valueOf(CharacterID.class, fileLines.get(1));
            final Vector2 position = new Vector2(
                    Double.parseDouble(fileLines.get(2)), Double.parseDouble(fileLines.get(3)));
            return new GameDataImpl(points, currentCharacterID, characters, position);
        }
        return new GameDataImpl(characters);

    }

    /**
     * Method to check the existence of the save file and its directory.
     *
     * @return if the save file already existed
     */
    private boolean checkExistenceOfFile() {
        final File saveDir = new File(DIR_PATH);
        boolean existent = true;
        if (!saveDir.exists() || !saveDir.isDirectory()) {
            if (saveDir.mkdir()) {
                existent = false;
            } else {
                this.logger.severe("The directory " + DIR_PATH + " wasn't created");
            }
        } else {
            final File saveFile = new File(DIR_PATH + this.fileName);
            if (!saveFile.exists()) {
                existent = false;
            }
        }
        return existent;
    }

    /**
     * Method to create a new save file.
     */
    private void createNewSaveFile() {
        try {
            final boolean isNewSaveFile = new File(DIR_PATH + this.fileName).createNewFile();
            if (!isNewSaveFile) {
                this.logger.info("The save file was overwritten");
            }
        } catch (final IOException e) {
            this.logger.severe("The save file was not created correctly:" + e);
        }
    }

}
