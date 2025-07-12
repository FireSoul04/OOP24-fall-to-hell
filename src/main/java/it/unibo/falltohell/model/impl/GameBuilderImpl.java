package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Game;
import it.unibo.falltohell.model.api.GameBuilder;
import it.unibo.falltohell.model.api.GameData;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Druid;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.character.Rogue;
import it.unibo.falltohell.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameBuilderImpl implements GameBuilder {

    private Optional<Level> level;
    private Optional<GameData> gameData;
    private Optional<GameEventManager<String>> eventManager;
    private List<Character> characters;

    public GameBuilderImpl() {
        this.level = Optional.empty();
        this.gameData = Optional.empty();
        this.eventManager = Optional.empty();
        this.characters = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder createLevel() {
        this.level = Optional.of(new LevelImpl());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder loadGameData() {
        // TODO update when game data is going to load from save file
        this.gameData = Optional.of(new GameDataImpl());
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
        if (level.isEmpty()) {
            throw new IllegalStateException("The characters needs a level to stay inside");
        }
        // TODO change when game data has get character instead of get id
        // final Vector2 position = gameData
        //     .ifPresentOrElse(t -> t.getCurrentCharacter().getPosition(), Vector2::zero);
        final Vector2 position = Vector2.zero();
        final Level lv = this.level.get();
        // TODO add remaining characters
        this.characters.add(new Rogue(lv, position));
        this.characters.add(new Druid(lv, position));
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalStateException if level nor game data are created
     */
    @Override
    public GameBuilder linkGameDataToLevel() {
        if (level.isEmpty() || gameData.isEmpty()) {
            throw new IllegalStateException("Game data and level needs to be created to link them");
        }
        level.get().linkGameData(gameData.get());
        return this;
    }

    /**
     * {@inheritDoc}
     * If no game data is present it creates a new one.
     * @throws IllegalStateException if level is not created
     */
    @Override
    public Game build() {
        if (level.isEmpty()) {
            throw new IllegalStateException("Cannot create a game without a level");
        }
        eventManager.ifPresent(level.get()::setGameEventManager);
        return new GameImpl(level.get(), gameData.orElse(new GameDataImpl()));
    }
}
