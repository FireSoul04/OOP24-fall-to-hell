package it.unibo.falltohell.model.api.level;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.GameEventCondition;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.manager.StaticCollisionManager;

import it.unibo.falltohell.util.Vector2;

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
     * Returns a copy of the list of all game objects in the level.
     *
     * @return a new list containing all game objects
     */
    List<GameObject> getGameObjects();

    /**
     * add a gameObject to the level.
     *
     * @param gameObject to be added in the level
     */
    void addGameObject(GameObject gameObject);

    /**
     * Removes a game object from the level.
     *
     * @param gameObject the game object to remove
     */
    void removeGameObject(GameObject gameObject);

    /**
     * Updates all game objects in the level and checks for collisions.
     * Only the selected character is updated.
     *
     * @param deltaTime the time elapsed since the last update
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
     * Check if game event has a condition with a certain name and return if that event is active or not.
     * @param name of the event
     * @return if the event is active or not
     */
    boolean checkCondition(String name);

    /**
     * Method to add a condition in the game event manager.
     * @param name of the event
     * @param event is when the event is active
     */
    void addCondition(String name, GameEventCondition event);

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

    /**
     * Returns the StaticCollisionManager responsible for managing collision checks
     * specific to jump mechanics.
     *
     * @return the StaticCollisionManager used to check collisions during jumps
     */
    StaticCollisionManager getJumpCollisionManager();

    /**
     * @return the size of the level
     */
    Vector2 getLevelSize();

    /**
     * set the size of the level.
     *
     * @param size the size of the level.
     */
    void setLevelSize(Vector2 size);
}
