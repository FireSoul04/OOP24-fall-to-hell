package it.unibo.falltohell.model.impl.level;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import it.unibo.falltohell.model.api.GameEventCondition;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.manager.GameEventManager;
import it.unibo.falltohell.model.api.manager.TimerManager;
import it.unibo.falltohell.model.api.gameobject.movable.Movable;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.manager.StaticCollisionManager;

import it.unibo.falltohell.model.impl.drawable.Label;
import it.unibo.falltohell.model.impl.drawable.Sprite;
import it.unibo.falltohell.model.impl.gameobject.GameObjectImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.entrance.BaseEntrance;
import it.unibo.falltohell.model.impl.manager.StaticCollisionManagerImpl;
import it.unibo.falltohell.model.impl.manager.TimerManagerImpl;
import it.unibo.falltohell.model.impl.manager.AABBCollisionsManager;
import it.unibo.falltohell.model.api.manager.CollisionsManager;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.util.Vector2;

/**
 * Implementation of the {@link Level} interface.
 * Manages the game objects present in the level and handles collision
 * detection.
 * Provides methods to add, remove, and retrieve game objects, as well as to
 * update
 * the state of the level and its objects.
 *
 * @author Lorenzo Casadei
 * @author Davide Mancini
 */
public class LevelImpl implements Level {

    private static final double LABEL_OFFSET_Y = 10;

    private final List<GameObject> gameObjects;
    private final GameCamera camera;
    private final CollisionsManager collisionsManager;
    private final TimerManager timerManager;
    private final StaticCollisionManager jumpCollisionManager;
    private final Map<CharacterID, Character> characters;
    private GameEventManager<String> eventManager;
    private DrawableRenderableHandler drh;
    private Optional<GameData> gameData;
    private Vector2 levelSize;
    private final Label pointsLabel;
    private final Label statsLabel;
    private final Label manaLabel;

    /**
     * Constructs a new LevelImpl with a given list of game objects.
     * If no drawable-renderable handler is linked, it will use a new not linked to
     * the view.
     * If no event manager is linked, it will use a new not linked to the game.
     *
     * @param camera      that follows the player
     * @param gameObjects the initial list of game objects in the level
     */
    public LevelImpl(final GameCamera camera, final GameEventManager<String> eventManager,
                     final DrawableRenderableHandler drh, final List<GameObject> gameObjects) {
        this.gameObjects = new CopyOnWriteArrayList<>(gameObjects);
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.characters = new EnumMap<>(CharacterID.class);
        this.jumpCollisionManager = new StaticCollisionManagerImpl();
        this.camera = camera;
        this.eventManager = eventManager;
        this.drh = drh;
        this.gameData = Optional.empty();

        for (final GameObject go : this.gameObjects) {
            if (go instanceof BaseCollidableBlock || go instanceof BaseEntrance) {
                this.jumpCollisionManager.addObstacle(go);
            }
        }

        this.pointsLabel = new Label("Points: 0", Vector2.zero(), true);
        this.statsLabel = new Label("HP: 0+0", Vector2.down().multiply(LABEL_OFFSET_Y), true);
        this.manaLabel = new Label("Mana: 0+0", Vector2.down().multiply(LABEL_OFFSET_Y * 2), true);

        drh.linkLabel(pointsLabel);
        drh.linkLabel(statsLabel);
        drh.linkLabel(manaLabel);
        drh.linkSprite(
            new Sprite(new GameObjectImpl(this, Vector2.zero()) {
                @Override
                public void update() {
                    this.setPosition(camera.getCameraPosition()
                        .add(new Vector2(camera.getCameraWidth(), camera.getCameraHeight()).multiply(2))
                        .divide(2)
                    );
                }
            }, Priority.BACKGROUND),
            "background.png"
        );
    }

    /**
     * Constructs a new empty LevelImpl.
     * If no drawable-renderable handler is linked, it will use a new not linked to
     * the view.
     * If no event manager is linked, it will use a new not linked to the game.
     *
     * @param camera that follows the player
     */
    public LevelImpl(final GameCamera camera, final GameEventManager<String> eventManager,
            final DrawableRenderableHandler drh) {
        this(camera, eventManager, drh, new ArrayList<>());
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
        gameObject.getDrawable().ifPresent(this.drh::removeLink);
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
    public void update(final double deltaTime) {
        this.gameData.ifPresent(d -> {
            final CharacterStatistics stats = (CharacterStatistics) d.getCurrentCharacter().getStats();
            d.getCurrentCharacter().update(deltaTime);
            this.camera.updateCamera(d.getCurrentCharacter().getPosition(), deltaTime);
            this.pointsLabel.setText("Points: " + d.getPoints());
            this.statsLabel.setText("Life: " + (int) (stats.getLife() * 10)
                + (stats.getTemporaryLife() > 0 ? "+" + (int) (stats.getTemporaryLife() * 10) : "")
                + "/" + (int) (stats.getFullLife() * 10));
            this.manaLabel.setText("Mana: " + (int) (stats.getMana() * 10)
                + (stats.getTemporaryMana() > 0 ? "+" + (int) (stats.getTemporaryMana() * 10) : "")
                + "/" + (int) (stats.getInitialMana() * 10));
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
        return this.drh;
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
    @Override
    public StaticCollisionManager getJumpCollisionManager() {
        return this.jumpCollisionManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getLevelSize() {
        return this.levelSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevelSize(final Vector2 size) {
        this.levelSize = size;
    }
}
