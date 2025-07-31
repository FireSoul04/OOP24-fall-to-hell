package it.unibo.falltohell.model.impl.builder;

import it.unibo.falltohell.controller.api.DrawableRenderableHandler;
import it.unibo.falltohell.controller.api.GameController;
import it.unibo.falltohell.controller.api.LevelLoader;
import it.unibo.falltohell.controller.impl.DrawableRenderableHandlerImpl;
import it.unibo.falltohell.controller.impl.LevelLoaderImpl;
import it.unibo.falltohell.controller.impl.SaveFileControllerImpl;
import it.unibo.falltohell.model.api.builder.LevelBuilder;
import it.unibo.falltohell.model.api.GameCamera;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character;
import it.unibo.falltohell.model.api.gameobject.movable.entity.character.Character.CharacterID;
import it.unibo.falltohell.model.api.manager.GameEventManager;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Archer;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Caster;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobject.movable.entity.character.Rogue;
import it.unibo.falltohell.model.impl.level.LevelImpl;
import it.unibo.falltohell.model.impl.manager.GameEventManagerImpl;
import it.unibo.falltohell.util.Vector2;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/**
 * Builder used to build the class containing core information for the game.
 * @author Davide Mancini
 */
public class LevelBuilderImpl implements LevelBuilder {

    private final GameController controller;
    private final Map<CharacterID, Character> characters;
    private Optional<Level> level;
    private Optional<GameData> gameData;
    private Optional<GameCamera> camera;
    private Optional<GameEventManager<String>> eventManager;
    private Optional<DrawableRenderableHandler> drh;

    /**
     * Creates a game builder with all parameters empty.
     *
     * @param controller handler of the flow of the game, needed for the level to work
     */
    public LevelBuilderImpl(final GameController controller) {
        this.controller = controller;
        this.characters = new EnumMap<>(CharacterID.class);
        this.level = Optional.empty();
        this.gameData = Optional.empty();
        this.camera = Optional.empty();
        this.eventManager = Optional.empty();
        this.drh = Optional.empty();
    }

    /**
     * {@inheritDoc}
     * Adds the event manager and drawable-renderable handler if already linked.
     */
    @Override
    public LevelBuilder createLevel() {
        if (this.camera.isEmpty()) {
            throw new IllegalStateException("Cannot create a level without a camera");
        }
        this.level = Optional.of(new LevelImpl(
            this.controller,
            this.camera.get(),
            this.eventManager.orElse(new GameEventManagerImpl<>()),
            this.drh.orElse(new DrawableRenderableHandlerImpl())
        ));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LevelBuilder loadGameData() {
        if (this.level.isEmpty()) {
            throw new IllegalStateException("The characters needs a level to stay inside");
        }
        this.gameData = Optional.of(new SaveFileControllerImpl().load(this.characters));
        final Character currentCharacter = this.gameData.get().getCurrentCharacter();
        currentCharacter.setPosition(this.gameData.get().getLastSavedPosition());
        this.level.get().addGameObject(currentCharacter);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LevelBuilder attachGameEventManager(final GameEventManager<String> eventManager) {
        this.eventManager = Optional.of(eventManager);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LevelBuilder attachDrawableRenderableHandlerToLevel(final DrawableRenderableHandler drh) {
        this.drh = Optional.of(drh);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LevelBuilder attachCamera(final GameCamera camera) {
        this.camera = Optional.of(camera);
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if level is not created
     */
    @Override
    public LevelBuilder loadCharacters() {
        if (this.level.isEmpty()) {
            throw new IllegalStateException("The characters needs a level to stay inside");
        }
        final Vector2 position = Vector2.zero();
        final Level lv = this.level.get();
        this.characters.put(CharacterID.ARCHER, new Archer(lv, position));
        this.characters.put(CharacterID.CASTER, new Caster(lv, position));
        this.characters.put(CharacterID.DRUID, new Druid(lv, position));
        this.characters.put(CharacterID.ROGUE, new Rogue(lv, position));
        this.level.get().loadCharacters(this.characters);
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if level nor game data are created
     */
    @Override
    public LevelBuilder linkGameDataToLevel() {
        if (this.level.isEmpty() || this.gameData.isEmpty()) {
            throw new IllegalStateException("Game data and level needs to be created to link them");
        }
        this.level.get().linkGameData(this.gameData.get());
        return this;
    }

    /**
     * {@inheritDoc}
     * If no game data is present it creates a new one.
     * @throws IllegalStateException if level is not created
     */
    @Override
    public Level build() {
        if (this.level.isEmpty()) {
            throw new IllegalStateException("Cannot build the level without creating it");
        }
        final LevelLoader ll = new LevelLoaderImpl("level.txt", this.level.get());
        ll.loadLevel();
        this.camera.ifPresent(t -> t.setLevelSize(this.level.get().getLevelSize()));
        return this.level.get();
    }
}
