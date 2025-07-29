package it.unibo.falltohell.model.api.builder;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.GameEventManager;

/**
 * Builder for the game (model).
 *
 * @author Davide Mancini
 */
public interface LevelBuilder {

    /**
     * Creates a level to build.
     * @return this builder with the level initialized
     */
    LevelBuilder createLevel();

    /**
     * Loads from the save file the game data for the game.
     * @return this builder with the game data
     */
    LevelBuilder loadGameData();

    /**
     * Attach the event manager to the game.
     * @param eventManager to handle event like key presses
     * @return this builder with an event manager
     */
    LevelBuilder attachGameEventManager(GameEventManager<String> eventManager);

    /**
     * Attach the DrawableRenderableHandler to the level.
     * @param drh handler to attach to
     * @return this builder with the handler
     */
    LevelBuilder attachDrawableRenderableHandlerToLevel(DrawableRenderableHandler drh);

    /**
     * Attach a camera to the level.
     * @param camera that follows the player
     * @return this builder with the camera
     */
    LevelBuilder attachCamera(GameCamera camera);

    /**
     * Loads the characters into the level.
     * @return this builder with the characters
     */
    LevelBuilder loadCharacters();

    /**
     * Links game data to the level for game objects that need a reference to it.
     * @return this builder with game data inside level
     */
    LevelBuilder linkGameDataToLevel();

    /**
     * @return build the game
     */
    Level build();
}
