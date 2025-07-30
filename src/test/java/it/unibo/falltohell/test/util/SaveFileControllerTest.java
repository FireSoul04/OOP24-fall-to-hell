package it.unibo.falltohell.test.util;

import it.unibo.falltohell.controller.impl.SaveFileControllerImpl;
import java.io.File;
import java.util.logging.Logger;

/**
 * Class for a new SaveFileController dedicated to tests.
 * @author Martina Malagoli
 */
public class SaveFileControllerTest extends SaveFileControllerImpl {

    private static final String FILE_NAME = "saveFileTest.txt";
    private static final String DIR_PATH = System.getProperty("user.home") + File.separator + "FTH" + File.separator;

    /**
     * Initialization of the SaveFileControllerTest class.
     */
    public SaveFileControllerTest() {
        super(FILE_NAME);
    }

    /**
     * Method to remove the test save file.
     */
    public void removeTestFile() {
        final File saveFile = new File(DIR_PATH + FILE_NAME);
        if (saveFile.exists() && !saveFile.delete()) {
           Logger.getLogger("testLogger").severe("The file" + DIR_PATH + FILE_NAME + "wasn't deleted");
        }
    }
}
