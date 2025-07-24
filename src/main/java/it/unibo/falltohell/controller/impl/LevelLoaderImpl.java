package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.LevelLoader;
import it.unibo.falltohell.model.api.EnemyFactory;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.BlockFactory;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.SavePoint;
import it.unibo.falltohell.model.impl.gameobjects.CharacterChanger;
import it.unibo.falltohell.model.impl.gameobjects.MerchantImpl;
import it.unibo.falltohell.model.impl.gameobjects.block.BlockFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.entrance.ShopEntrance;
import it.unibo.falltohell.model.impl.gameobjects.entrance.SpringsEntrance;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that handles the loading of a level from file.
 * @author Martina Malagoli
 */
public class LevelLoaderImpl implements LevelLoader {

    private static final String PATH = "src/main/resources/level";
    private static final double DISTANCE = 20;
    private final List<String> levelFromFile;
    private final Level level;
    private final EnemyFactory enemyFactory;
    private final BlockFactory blockFactory;

    /**
     * Initialization of the LevelLoaderImpl class.
     * @param fileName is the name of the file
     * @param level corresponding to the level in the file
     */
    public LevelLoaderImpl(final String fileName, final Level level) {
        this.level = level;
        this.levelFromFile = new ArrayList<>();
        this.levelFromFile.addAll(new FileControllerImpl().read(PATH + fileName));
        this.enemyFactory =  new EnemyFactoryImpl();
        this.blockFactory = new BlockFactoryImpl();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadLevel() {
        for (int y = 0; y < this.levelFromFile.size(); y++) {
            final char[] identifiers = this.levelFromFile.get(y).toCharArray();
            for (int x = 0; x < identifiers.length; x++) {
                final Vector2 position = new Vector2(x, y).multiply(DISTANCE);
                this.parseToGameObject(identifiers[x], position);
            }
        }
    }

    /**
     * Method to create the correct game object corresponding to the file input.
     * @param identifier is the char associated to a specific game object in the file
     * @param position of the game object in the level
     */
    private void parseToGameObject(final char identifier, final Vector2 position) {
        final Character character = level.getGameData().getCurrentCharacter();
        final Collider collider = new BoxCollider();
        final Optional<GameObject> gameObject = switch (identifier) {
            case 'o' -> Optional.of(this.enemyFactory.createImp(this.level, position, character));
            case 'k' -> Optional.of(this.enemyFactory.createCentaur(this.level, position, character));
            case 't' -> Optional.of(this.enemyFactory.createTengu(level, position, character));
            case 'x' -> Optional.of(this.enemyFactory.createLotawiec(level, position, character));
            case '#' -> Optional.of(this.blockFactory.createBaseBlock(level, position));
            case 'l' -> Optional.of(this.blockFactory.createLavaBlock(level, position));
            case 'v' -> Optional.of(this.blockFactory.createVinesBlock(level, position));
            case 'e' -> Optional.of(new SpringsEntrance(level, position, collider));
            case 'p' -> Optional.of(new ShopEntrance(level, position, collider));
            case 'c' -> Optional.of(new CharacterChanger(level, position, collider, level.getCharacters(), ""));
            case 's' -> Optional.of(new SavePoint(level, position, collider, level.getGameData(), ""));
            case 'm' -> Optional.of(new MerchantImpl(level, position, collider));
            case ' ' -> Optional.empty();
            default -> throw new IllegalStateException("Cannot recognize a character in the file:" + identifier);
        };
    }
}
