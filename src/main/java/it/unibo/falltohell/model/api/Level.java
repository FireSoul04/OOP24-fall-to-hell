package it.unibo.falltohell.model.api;

import it.unibo.falltohell.model.impl.GameEventManager;

import java.util.List;

public interface Level {
    
    /**
     * @return the list of game objects currently present in the level
     */
    List<GameObject> getGameObject();

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
}
