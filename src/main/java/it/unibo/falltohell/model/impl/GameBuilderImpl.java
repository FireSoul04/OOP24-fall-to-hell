package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.GameBuilder;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character.CharacterID;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Rogue;
import it.unibo.falltohell.util.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameBuilderImpl implements GameBuilder {

    private final Map<CharacterID, Character> characters;
    private Optional<Level> level;
    private Optional<GameData> gameData;
    private Optional<GameEventManager<String>> eventManager;

    public GameBuilderImpl() {
        this.characters = new HashMap<>();
        this.level = Optional.empty();
        this.gameData = Optional.empty();
        this.eventManager = Optional.empty();
    }

    /**
     * {@inheritDoc}
     * Adds the event manager if already linked.
     */
    @Override
    public GameBuilder createLevel() {
        this.level = Optional.of(new LevelImpl());
        this.eventManager.ifPresent(this.level.get()::setGameEventManager);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder loadGameData() {
        // TODO update when game data is going to load from save file
        this.gameData = Optional.of(new GameDataImpl(this.characters));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder attachGameEventManager(final GameEventManager<String> eventManager) {
        this.eventManager = Optional.of(eventManager);
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if level is not created
     */
    @Override
    public GameBuilder loadCharacters() {
        if (this.level.isEmpty()) {
            throw new IllegalStateException("The characters needs a level to stay inside");
        }
        // TODO change when game data has get character instead of get id
        // final Vector2 position = gameData
        //     .ifPresentOrElse(t -> t.getCurrentCharacter().getPosition(), Vector2::zero);
        final Vector2 position = Vector2.zero();
        final Level lv = this.level.get();
        // TODO add remaining characters
        this.characters.put(CharacterID.ROGUE, new Rogue(lv, position));
        this.characters.put(CharacterID.DRUID, new Druid(lv, position));
        this.level.get().loadCharacters(this.characters);
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if level nor game data are created
     */
    @Override
    public GameBuilder linkGameDataToLevel() {
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
    public Game build() {
        if (this.level.isEmpty()) {
            throw new IllegalStateException("Cannot create a game without a level");
        }
        return new GameImpl(this.level.get(), this.gameData.orElse(new GameDataImpl(this.characters)), this.characters);
    }
}
