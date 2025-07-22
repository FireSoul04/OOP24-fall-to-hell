package it.unibo.falltohell;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.impl.DrawableRenderableHandlerImpl;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.TimerManager;
import it.unibo.falltohell.model.api.gameobjects.Movable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.CollisionsManager;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.model.impl.TimerManagerImpl;
import it.unibo.falltohell.model.impl.physics.colliders.AABBCollisionsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Class for new level dedicated to tests.
 * The characters and the view features are disabled on this class.
 */
public class LevelTest implements Level {

    private final List<GameObject> gameObjects;
    private final CollisionsManager collisionsManager;
    private final TimerManager timerManager;
    private GameEventManager<String> eventManager;
    private Optional<GameData> gameData;

    /**
     * Creates a new level with default managers.
     * The game objects list and event manager are empty by default.
     */
    public LevelTest() {
        this.gameObjects = new ArrayList<>();
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.eventManager = new GameEventManager<>();
        this.gameData = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObject> getGameObjects() {
        return Collections.unmodifiableList(this.gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGameObject(final GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGameObject(final GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        final Stream<GameObject> gameObjectStream = this.gameObjects.stream();
        for (final GameObject gameObject : gameObjectStream.toList()) {
            if(gameObject instanceof Movable movable) {
                movable.update(deltaTime);
            } else {
                gameObject.update();
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
    public void linkGameData(final GameData gameData) {
        this.gameData = Optional.of(gameData);
    }

    /**
     * {@inheritDoc}
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
     * Not used
     */
    @Override
    public void setDrawableRenderableHandler(final DrawableRenderableHandler drh) {
        throw new UnsupportedOperationException("No use for tests");
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public DrawableRenderableHandler getDrawableRenderableHandler() {
        return new DrawableRenderableHandlerImpl();
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public void loadCharacters(final Map<Character.CharacterID, Character> characters) {
        throw new UnsupportedOperationException("No use for tests");
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public Map<Character.CharacterID, Character> getCharacters() {
        throw new UnsupportedOperationException("No use for tests");
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public void setPlayer(final Character player) {
        throw new UnsupportedOperationException("No use for tests");
    }
}
