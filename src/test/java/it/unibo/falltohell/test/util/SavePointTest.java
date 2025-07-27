package it.unibo.falltohell.test.util;

import it.unibo.falltohell.model.api.gameobject.interactable.Interactable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.util.Vector2;

/**
 * Class to save the current state of the game for tests.
 * @author Martina Malagoli
 */
public class SavePointTest extends GameObjectImpl implements Interactable {

    private final SaveFileControllerTest saveController;

    /**
     * Initialization of the SavePoint class.
     * @param level is the current lever
     * @param position of the save point
     * @param collider to see if the player is close enough to interact
     */
    public SavePointTest(final Level level, final Vector2 position, final Collider collider) {
        super(level, position, collider);
        this.saveController = new SaveFileControllerTest();
    }

    /**
     * Method to save the current state of the game.
     */
    @Override
    public void interact(final Character character) {
        this.saveController.save(this.getLevel().getGameData());
    }
}

