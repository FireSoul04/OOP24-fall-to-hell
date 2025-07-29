package it.unibo.falltohell.model.impl.gameobject.interactable;

import it.unibo.falltohell.controller.api.SaveFileController;
import it.unibo.falltohell.controller.impl.SaveFileControllerImpl;
import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.timer.CustomTimerImpl;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.interactable.Interactable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;

/**
 * Class to save the current state of the game.
 * @author Martina Malagoli
 */
public class SavePoint extends GameObjectImpl implements Interactable {

    private static final Vector2 LABEL_POSITION = new Vector2(70.0, 50.0);
    private static final long LABEL_DURATION = 1500;
    private final SaveFileController saveController;
    private final Label correctSaveLabel;
    private final Label incorrectSaveLabel;

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
        this.correctSaveLabel = new Label("Saved the game", LABEL_POSITION, false);
        this.getLevel().getDrawableRenderableHandler().linkLabel(this.correctSaveLabel);
        this.incorrectSaveLabel = new Label("Something went wrong", LABEL_POSITION, false);
        this.getLevel().getDrawableRenderableHandler().linkLabel(this.incorrectSaveLabel);
    }

    /**
     * Method to save the current state of the game.
     */
    @Override
    public void interact(final Character character) {
        final boolean correctSave = this.saveController.save(this.getLevel().getGameData());
        final Label label;
        if (correctSave) {
            label = this.correctSaveLabel;
        } else {
            label = this.incorrectSaveLabel;
        }
        label.setVisible(true);
        final String timerName = "saving" + label.hashCode();
        this.getLevel().getTimerManager().restartIfPresent(timerName, new CustomTimerImpl(LABEL_DURATION,
            () -> label.setVisible(false)));
    }
}
