package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.controller.impl.SaveFileControllerImpl;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.util.Vector2;

/**
 * Class to save the current state of the game or change the current character.
 * @author Martina Malagoli
 */
public class SavePoint extends GameObjectImpl implements Interactable {

    private final SaveFileController saveController;

    /**
     * Initialization of the SavePoint class.
     * @param level is the current lever
     * @param position of the save point
     * @param collider to see if the player is close enough to interact
     * @param data to write on the save file
     */
    public SavePoint(Level level, Vector2 position, Collider collider, GameData data) {
        super(level, position, 0, 0, collider);
        this.saveController = new SaveFileControllerImpl(data);
    }

    /**
     * Method to save the current state of the game or change the current character depending on the
     * player's choice
     */
    @Override
    public void interact() {
        this.saveController.save();
        //TODO
    }

    private void selectCharacter() {
        //TODO
    }

}
