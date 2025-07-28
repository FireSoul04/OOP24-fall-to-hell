package it.unibo.falltohell.test.util;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.impl.DrawableRenderableHandlerImpl;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.manager.CollisionsManager;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.model.impl.manager.StaticCollisionManager;
import it.unibo.falltohell.model.impl.manager.TimerManagerImpl;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.manager.AABBCollisionsManager;

import java.util.*;
import java.util.stream.Stream;

/**
 * Class for new level dedicated to tests.
 * The characters and the view features are disabled on this class.
 */
public class LevelTest implements Level {

    private final List<GameObject> gameObjects;
    private final CollisionsManager collisionsManager;
    private final TimerManager timerManager;
    private final Map<CharacterID, Character> characters;
    private GameEventManagerImpl<String> eventManager;
    private Optional<GameData> gameData;
    private StaticCollisionManager jumpCollisionManager;

    /**
     * Creates a new level with default managers.
     * The game objects list is empty by default.
     * The game event manager has every character dependant event false.
     */
    public LevelTest() {
        this.gameObjects = new ArrayList<>();
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.eventManager = new GameEventManagerImpl<>();
        this.gameData = Optional.empty();
        this.characters = new EnumMap<>(CharacterID.class);
        this.jumpCollisionManager = new StaticCollisionManager();

        this.eventManager.addCondition("ActiveAbility", () -> false);
        this.eventManager.addCondition("NormalAttack", () -> false);
        this.eventManager.addCondition("MoveLeft", () -> false);
        this.eventManager.addCondition("MoveRight", () -> false);
        this.eventManager.addCondition("MoveUp", () -> false);
        this.eventManager.addCondition("MoveDown", () -> false);
        this.eventManager.addCondition("Interact", () -> false);
        this.eventManager.addCondition("Jump", () -> false);
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

        if (gameObject instanceof BaseCollidableBlock || gameObject instanceof BaseEntrance) {
            this.jumpCollisionManager.addObstacle(gameObject);
        }
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
            if (gameObject instanceof Movable movable) {
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
    public void setGameEventManager(final GameEventManagerImpl<String> eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameEventManagerImpl<String> getGameEventManager() {
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
     */
    @Override
    public DrawableRenderableHandler getDrawableRenderableHandler() {
        return new DrawableRenderableHandlerImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadCharacters(final Map<CharacterID, Character> characters) {
        this.characters.clear();
        this.characters.putAll(characters);
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
    public StaticCollisionManager getJumpCollisionManager() {
        return this.jumpCollisionManager;
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public void setLevelSize(final Vector2 size){
        throw new UnsupportedOperationException("No use for tests");
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public Vector2 getLevelSize() {
        throw new UnsupportedOperationException("No use for tests");
    }
}
