package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;

import java.util.ArrayList;
import java.util.List;

import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.impl.gameobjects.MovableImpl;
import it.unibo.falltohell.model.impl.physics.colliders.AABBCollisionsManager;
import it.unibo.falltohell.model.api.physics.CollisionsManager;
import it.unibo.falltohell.model.api.GameObject;
/**
 * Implementation of the {@link Level} interface.
 * <p>
 * Manages the game objects present in the level and handles collision detection.
 * Provides methods to add, remove, and retrieve game objects, as well as to update
 * the state of the level and its objects.
 * </p>
 */
public class LevelImpl implements Level{

    private final List<GameObject> gameObjects;
    private final CollisionsManager collisionsManager;
    private final TimerManager timerManager;
    private final GameData gameData;

    /**
     * Constructs a new LevelImpl with a given list of game objects.
     *
     * @param gameObjects the initial list of game objects in the level
     */
    public LevelImpl(final List<GameObject> gameObjects, final GameData gameData) {
        this.gameObjects = gameObjects;
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.gameData = gameData;
    }
    /**
     * Constructs a new empty LevelImpl.
     */
    public LevelImpl() {
        this.gameObjects = new ArrayList<>();
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.gameData = new GameDataImpl();
    }
    /**
     * Adds a game object to the level.
     *
     * @param gameObject the game object to add
     */
    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }
    /**
     * Removes a game object from the level.
     *
     * @param gameObject the game object to remove
     */
    public void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }
    /**
     * Returns a copy of the list of all game objects in the level.
     *
     * @return a new list containing all game objects
     */
    public List<GameObject> getGameObject() {
        return new ArrayList<>(this.gameObjects);
    }
    /**
     * Updates all movable game objects in the level and checks for collisions.
     *
     * @param deltaTime the time elapsed since the last update 
     */
    public void update(double deltaTime){
        for(GameObject gameObject : this.gameObjects) {
            if(gameObject instanceof MovableImpl) {
                ((MovableImpl) gameObject).update(deltaTime);
            }
        }
        this.collisionsManager.checkCollisions(this.gameObjects);
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TimerManager getTimerManager() {
        return this.timerManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameData getGameData() {
        return this.gameData;
    }
}
