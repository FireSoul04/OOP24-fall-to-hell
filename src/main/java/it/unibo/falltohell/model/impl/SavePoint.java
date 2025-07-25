package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.controller.impl.SaveFileControllerImpl;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

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
     */
    public SavePoint(final Level level, final Vector2 position, final Collider collider) {
        super(level, position, collider);
        this.saveController = new SaveFileControllerImpl();
        this.initDrawable(Priority.VERY_LOW, "save_point.png");
    }

    /**
     * Method to save the current state of the game.
     */
    @Override
    public void interact(final Character character) {
        this.saveController.save(this.getLevel().getGameData());
    }
}
