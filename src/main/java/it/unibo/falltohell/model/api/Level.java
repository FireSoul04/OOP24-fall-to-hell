package it.unibo.falltohell.model.api;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;
import it.unibo.falltohell.model.impl.GameEventManager;

import java.util.List;
import java.util.Map;

/**
 * Interface for a level in the game.
 * It contains every game object and update them every frame.
 * It also has the data of the player, a timer manager and a game event manager.
 *
 * @author Lorenzo Casadei
 * @author Davide Mancini
 */
public interface Level {

    /**
     * @return the list of game objects currently present in the level
     */
    List<GameObject> getGameObjects();

    /**
     * @param gameObject to be added in the level
     */
    void addGameObject(GameObject gameObject);

    /**
     * @param gameObject to be removed in the level
     */
    void removeGameObject(GameObject gameObject);

    /**
     * @param deltaTime the time elapsed since the last update (in seconds)
     */
    void update(double deltaTime);

    /**
     * @return the timer manager of the level
     */
    TimerManager getTimerManager();

    /**
     * Save a reference to game data inside this level.
     *
     * @param gameData to link
     */
    void linkGameData(GameData gameData);

    /**
     * @return the game data of the level
     */
    GameData getGameData();

    /**
     * @param eventManager manager to all events of the level
     */
    void setGameEventManager(GameEventManager<String> eventManager);

    /**
     * @return manager to all events of the level
     */
    GameEventManager<String> getGameEventManager();

    /**
     * @param drh handler to all drawables of the level
     */
    void setDrawableRenderableHandler(DrawableRenderableHandler drh);

    /**
     * @return handler to all drawables of the level
     */
    DrawableRenderableHandler getDrawableRenderableHandler();

    /**
     * Save a reference to all playable characters inside the level.
     *
     * @param characters playable
     */
    void loadCharacters(Map<CharacterID, Character> characters);

    /**
     * @return all playable characters
     */
    Map<CharacterID, Character> getCharacters();
}
