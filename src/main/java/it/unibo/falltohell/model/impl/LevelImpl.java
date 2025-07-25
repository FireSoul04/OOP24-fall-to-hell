package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.impl.DrawableRenderableHandlerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import it.unibo.falltohell.model.api.*;
import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;
import it.unibo.falltohell.model.impl.physics.colliders.AABBCollisionsManager;
import it.unibo.falltohell.model.api.physics.CollisionsManager;

/**
 * Implementation of the {@link Level} interface.
 * <p>
 * Manages the game objects present in the level and handles collision
 * detection.
 * Provides methods to add, remove, and retrieve game objects, as well as to
 * update
 * the state of the level and its objects.
 * </p>
 *
 * @author Lorenzo Casadei
 * @author Davide Mancini
 */
public class LevelImpl implements Level {

    private final List<GameObject> gameObjects;
    private final GameCamera camera;
    private final CollisionsManager collisionsManager;
    private final TimerManager timerManager;
    private Map<CharacterID, Character> characters;
    private GameEventManager<String> eventManager;
    private DrawableRenderableHandler drh;
    private Optional<GameData> gameData;
    private Optional<Character> player;

    /**
     * Constructs a new LevelImpl with a given list of game objects.
     * If no drawable-renderable handler is linked, it will use a new not linked to
     * the view.
     * If no event manager is linked, it will use a new not linked to the game.
     *
     * @param camera      that follows the player
     * @param gameObjects the initial list of game objects in the level
     */
    public LevelImpl(final GameCamera camera, final List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
        this.camera = camera;
        this.player = Optional.empty();
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.eventManager = new GameEventManager<>();
        this.characters = new HashMap<>();
        this.drh = new DrawableRenderableHandlerImpl();
        this.gameData = Optional.empty();

    }

    /**
     * Constructs a new empty LevelImpl.
     * If no drawable-renderable handler is linked, it will use a new not linked to
     * the view.
     * If no event manager is linked, it will use a new not linked to the game.
     *
     * @param camera that follows the player
     */
    public LevelImpl(final GameCamera camera) {
        this(camera, new ArrayList<>());
    }

    /**
     * Adds a game object to the level.
     *
     * @param gameObject the game object to add
     */
    public void addGameObject(final GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    /**
     * Removes a game object from the level.
     *
     * @param gameObject the game object to remove
     */
    public void removeGameObject(final GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    /**
     * Returns a copy of the list of all game objects in the level.
     *
     * @return a new list containing all game objects
     */
    public List<GameObject> getGameObjects() {
        return Collections.unmodifiableList(this.gameObjects);
    }

    /**
     * Updates all movable game objects in the level and checks for collisions.
     *
     * @param deltaTime the time elapsed since the last update
     */
    public void update(final double deltaTime) {
        final Stream<GameObject> gameObjectStream = this.gameObjects.stream();
        for (final GameObject gameObject : gameObjectStream.toList()) {
            if (gameObject instanceof Movable movable) {
                movable.update(deltaTime);
            } else {
                gameObject.update();
            }
        }
        this.collisionsManager.checkCollisions(this.gameObjects);
        this.player.ifPresent(p -> this.camera.updateCamera(p.getPosition(), deltaTime));
        this.drh.updateAll(camera);
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
    public void linkGameData(final GameData gameData) {
        this.gameData = Optional.of(gameData);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if game data is never initialized
     */
    @Override
    public GameData getGameData() {
        return this.gameData.orElseThrow(() -> new IllegalStateException("Game data is not initialized"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameEventManager(final GameEventManager<String> eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameEventManager<String> getGameEventManager() {
        return this.eventManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDrawableRenderableHandler(final DrawableRenderableHandler drh) {
        this.drh = drh;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DrawableRenderableHandler getDrawableRenderableHandler() {
        return this.drh;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCharacters(final Map<CharacterID, Character> characters) {
        this.characters = Collections.unmodifiableMap(characters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<CharacterID, Character> getCharacters() {
        return this.characters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayer(final Character player) {
        this.player = Optional.of(player);
    }
}
