package it.unibo.falltohell.model.impl.level;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.DrawableRenderableHandler;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.controller.api.GameController.GameState;
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
    private static final String EXPOSE_REP = "EI_EXPOSE_REP";

    private final List<GameObject> gameObjects;
    private final GameCamera camera;
    private final CollisionsManager collisionsManager;
    private final TimerManager timerManager;
    private final StaticCollisionManager jumpCollisionManager;
    private final Map<CharacterID, Character> characters;
    private final GameEventManager<String> eventManager;
    private final DrawableRenderableHandler drh;
    private final GameController controller;
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
     * @param controller   that handle the flow of the game
     * @param camera       that follows the player
     * @param eventManager of the game events
     * @param drh          handler for the drawables
     */
    @SuppressFBWarnings(
        value = { EXPOSE_REP, "EI_EXPOSE_REP2" },
        justification = "The camera must be accessed by the renderables, the "
            + "event manager should be updated in the controller and the "
            + "drawable renderable handler must be used to link any drawable"
    )
    public LevelImpl(final GameController controller, final GameCamera camera,
                     final GameEventManager<String> eventManager, final DrawableRenderableHandler drh) {
        this.gameObjects = new CopyOnWriteArrayList<>();
        this.collisionsManager = new AABBCollisionsManager();
        this.timerManager = new TimerManagerImpl();
        this.characters = new EnumMap<>(CharacterID.class);
        this.jumpCollisionManager = new StaticCollisionManagerImpl();
        this.controller = controller;
        this.camera = camera;
        this.eventManager = eventManager;
        this.drh = drh;
        this.gameData = Optional.empty();
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
            final Character currentCharacter = d.getCurrentCharacter();
            if (currentCharacter.isDead()) {
                this.controller.changeState(GameState.OVER);
            }
            final CharacterStatistics stats = (CharacterStatistics) currentCharacter.getStats();
            currentCharacter.update(deltaTime);
            this.camera.updateCamera(currentCharacter.getPosition(), deltaTime);
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
    @SuppressFBWarnings(
        value = EXPOSE_REP,
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
    @SuppressFBWarnings(
        value = EXPOSE_REP,
        justification = "This is the only way to link any drawable from the game objects"
    )
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
    @SuppressFBWarnings(
    value = EXPOSE_REP,
    justification = "The jumpCollisionManager must be exposed to allow game objects to register"
        + "and remove static obstacles for jump collision detection"
    )
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
