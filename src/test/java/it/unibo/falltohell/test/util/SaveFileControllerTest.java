package it.unibo.falltohell.test.util;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.controller.impl.FileControllerImpl;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.impl.GameDataImpl;
import it.unibo.falltohell.util.Vector2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class for a new SaveFileController dedicated to tests.
 * @author Martina Malagoli
 */
public class SaveFileControllerTest implements SaveFileController {

    private static final String FILE_NAME = "saveFileTest.txt";
    private static final String DIR_PATH = System.getProperty("user.home") + File.separator + "FTH" + File.separator;
    private final Logger logger;

    /**
     * Initialization of the SaveFileControllerImpl.
     */
    public SaveFileControllerTest() {
        this.logger = Logger.getLogger("SaveFileControllerLogger");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final GameData data) {
        if (this.checkExistenceOfFile()) {
            this.createNewSaveFile();
        }
        try (
                BufferedWriter saveOutput = new BufferedWriter(new FileWriter(DIR_PATH + FILE_NAME)
                )
        ) {
            final it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character character = data.getCurrentCharacter();
            saveOutput.write(String.valueOf(data.getPoints()));
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

    /**
     * {@inheritDoc}
     */
    @Override
    public GameData load(final Map<Character.CharacterID, Character> characters) {
        if(this.checkExistenceOfFile()) {
            final List<String> fileLines = new FileControllerImpl().read(DIR_PATH + FILE_NAME);
            final long points = Long.parseLong(fileLines.get(0));
            final Character.CharacterID currentCharacterID = Enum.valueOf(Character.CharacterID.class, fileLines.get(1));
            final Vector2 position = new Vector2(
                    Double.parseDouble(fileLines.get(2)), Double.parseDouble(fileLines.get(3)));
            return new GameDataImpl(points, currentCharacterID, characters, position);
        }
        return new GameDataImpl(characters);

    }

    public void removeTestFile() {
        final File saveFile = new File(DIR_PATH + FILE_NAME);
        if (saveFile.exists()) {
           final boolean deleted = saveFile.delete();
        }
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
            final boolean savedDir = saveDir.mkdir();
            existent = false;
        } else {
            final File saveFile = new File(DIR_PATH + FILE_NAME);
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
            final boolean newSaveFile = new File(DIR_PATH + FILE_NAME).createNewFile();
        } catch (final IOException e) {
            this.logger.severe("The save file was not created correctly:" + e);
        }
    }
}
