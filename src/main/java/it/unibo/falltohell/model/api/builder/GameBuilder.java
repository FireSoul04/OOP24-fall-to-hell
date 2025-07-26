package it.unibo.falltohell.model.api.builder;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;

/**
 * Builder for the game (model).
 *
 * @author Davide Mancini
 */
public interface GameBuilder {

    /**
     * Creates a level inside game.
     * @return this builder with the level
     */
    GameBuilder createLevel();

    /**
     * Loads from the save file the game data for the game.
     * @return this builder with the game data
     */
    GameBuilder loadGameData();

    /**
     * Attach the event manager to the game.
     * @param eventManager to handle event like key presses
     * @return this builder with an event manager
     */
    GameBuilder attachGameEventManager(GameEventManagerImpl<String> eventManager);

    /**
     * Attach the DrawableRenderableHandler to the level.
     * @param drh handler to attach to
     * @return this builder with the handler
     */
    GameBuilder attachDrawableRenderableHandlerToLevel(DrawableRenderableHandler drh);

    /**
     * Attach a camera to the game.
     * @param camera that follows the player
     * @return this builder with the camera
     */
    GameBuilder attachCamera(GameCamera camera);

    /**
     * Loads the characters into the game.
     * @return this builder with the characters
     */
    GameBuilder loadCharacters();

    /**
     * Links game data to the level for game objects that need a reference to it.
     * @return this builder with game data inside level
     */
    GameBuilder linkGameDataToLevel();

    /**
     * @return build the game
     */
    Game build();
}
