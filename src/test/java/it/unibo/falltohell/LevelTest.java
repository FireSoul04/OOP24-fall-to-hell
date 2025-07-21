package it.unibo.falltohell;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
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

public class LevelTest implements Level {

    private final List<GameObject> gameObjects;
    private final CollisionsManager collisionsManager;
    private final TimerManager timerManager;
    private Optional<GameData> gameData;

    public LevelTest() {
        this.gameObjects = new ArrayList<>();
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
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
    public void update(final double deltaTime){
        for(final GameObject gameObject : this.gameObjects) {
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
     * Not used
     */
    @Override
    public void setGameEventManager(final GameEventManager<String> eventManager) {
        throw new UnsupportedOperationException("No use for tests");
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public GameEventManager<String> getGameEventManager() {
        throw new UnsupportedOperationException("No use for tests");
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
        throw new UnsupportedOperationException("No use for tests");
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
