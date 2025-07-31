package it.unibo.falltohell.test.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.impl.DrawableRenderableHandlerImpl;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.GameEventCondition;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.manager.GameEventManager;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.manager.CollisionsManager;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.model.api.manager.StaticCollisionManager;
import it.unibo.falltohell.model.impl.manager.StaticCollisionManagerImpl;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.manager.AABBCollisionsManager;

import java.util.stream.Stream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Collections;

/**
 * Class for new level dedicated to tests.
 * The characters and the view features are disabled on this class.
 */
public class LevelTest implements Level {

    private final List<GameObject> gameObjects;
    private final CollisionsManager collisionsManager;
    private final TimerManagerTest timerManager;
    private final Map<CharacterID, Character> characters;
    private final StaticCollisionManager jumpCollisionManager;
    private final GameEventManager<String> eventManager;
    private Optional<GameData> gameData;

    /**
     * Creates a new level with default managers.
     * The game objects list is empty by default.
     * The game event manager has every character dependant event false.
     */
    public LevelTest() {
        this.gameObjects = new ArrayList<>();
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerTest();
        this.eventManager = new GameEventManagerImpl<>();
        this.gameData = Optional.empty();
        this.characters = new EnumMap<>(CharacterID.class);
        this.jumpCollisionManager = new StaticCollisionManagerImpl();

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
    public final void removeGameObject(final GameObject gameObject) {
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
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Any game object can add, remove and check for timers"
    )
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
    public boolean checkCondition(final String name) {
        return this.eventManager.checkCondition(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCondition(final String name, final GameEventCondition event) {
        this.eventManager.addCondition(name, event);
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
        return Collections.unmodifiableMap(this.characters);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "The jumpCollisionManager must be exposed to allow game objects to register"
            + "and remove static obstacles for jump collision detection"
    )
    @Override
    public StaticCollisionManager getJumpCollisionManager() {
        return this.jumpCollisionManager;
    }

    /**
     * {@inheritDoc}
     * Not used
     */
    @Override
    public void setLevelSize(final Vector2 size) {
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
