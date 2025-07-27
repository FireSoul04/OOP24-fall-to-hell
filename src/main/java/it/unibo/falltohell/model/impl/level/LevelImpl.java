package it.unibo.falltohell.model.impl.level;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.impl.DrawableRenderableHandlerImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.model.impl.manager.TimerManagerImpl;
import it.unibo.falltohell.model.impl.manager.AABBCollisionsManager;
import it.unibo.falltohell.model.api.manager.CollisionsManager;
import it.unibo.falltohell.util.Vector2;

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
    private GameEventManagerImpl<String> eventManager;
    private DrawableRenderableHandler drh;
    private Optional<GameData> gameData;

    private final Label pointsLabel;
    private final Label statsLabel;

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
        this.gameObjects = new ArrayList<>(gameObjects);
        this.camera = camera;
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.eventManager = new GameEventManagerImpl<>();
        this.characters = new EnumMap<>(CharacterID.class);
        this.drh = new DrawableRenderableHandlerImpl();
        this.gameData = Optional.empty();

        this.pointsLabel = new Label("Points: 0", Vector2.zero(), true);
        this.statsLabel = new Label("HP: 0", Vector2.down().multiply(10), true);
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
    @Override
    public void addGameObject(final GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    /**
     * Removes a game object from the level.
     *
     * @param gameObject the game object to remove
     */
    @Override
    public void removeGameObject(final GameObject gameObject) {
        this.gameObjects.remove(gameObject);
        gameObject.getDrawable().ifPresent(this.drh::removeLink);
    }

    /**
     * Returns a copy of the list of all game objects in the level.
     *
     * @return a new list containing all game objects
     */
    @Override
    public List<GameObject> getGameObjects() {
        return Collections.unmodifiableList(this.gameObjects);
    }

    /**
     * Updates all game objects in the level and checks for collisions.
     * Only the selected character is updated.
     *
     * @param deltaTime the time elapsed since the last update
     */
    @Override
    public void update(final double deltaTime) {
        this.gameData.ifPresent(d -> {
            d.getCurrentCharacter().update(deltaTime);
            this.camera.updateCamera(d.getCurrentCharacter().getPosition(), deltaTime);
            this.pointsLabel.setText("Points: " + d.getPoints());
            this.statsLabel.setText("HP: " + d.getCurrentCharacter().getStats().getLife());
        });
        final Stream<GameObject> gameObjectStream = this.gameObjects.stream().filter(t -> !(t instanceof Character));
        for (final GameObject gameObject : gameObjectStream.toList()) {
            if (gameObject instanceof Movable movable) {
                movable.update(deltaTime);
            }
            gameObject.update();
        }
        this.collisionsManager.checkCollisions(this.gameObjects);
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
     */
    @Override
    public void setDrawableRenderableHandler(final DrawableRenderableHandler drh) {
        this.drh = drh;
        drh.linkLabel(pointsLabel);
        drh.linkLabel(statsLabel);
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
        return Collections.unmodifiableMap(this.characters);
    }
}
