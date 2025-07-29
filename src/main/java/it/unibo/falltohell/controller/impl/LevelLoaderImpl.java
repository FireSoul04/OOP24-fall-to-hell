package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.LevelLoader;
import it.unibo.falltohell.model.api.factory.EnemyFactory;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.factory.CollidableBlockFactory;
import it.unibo.falltohell.model.api.gameobject.Merchant;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.factory.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.interactable.SavePoint;
import it.unibo.falltohell.model.impl.gameobject.interactable.CharacterChanger;
import it.unibo.falltohell.model.impl.gameobject.MerchantImpl;
import it.unibo.falltohell.model.impl.gameobject.block.BaseNonCollidableBlock;
import it.unibo.falltohell.model.impl.factory.CollidableBlockFactoryImpl;
import it.unibo.falltohell.model.impl.gameobject.entrance.ShopEntrance;
import it.unibo.falltohell.model.impl.gameobject.entrance.SpringsEntrance;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that handles the loading of a level from file.
 *
 * @author Martina Malagoli
 */
public class LevelLoaderImpl implements LevelLoader {

    private static final String PATH = "src/main/resources/level/";
    private static final double DISTANCE = 20;
    private final List<String> levelFromFile;
    private final Level level;
    private final EnemyFactory enemyFactory;
    private final CollidableBlockFactory collidableBlockFactory;
    private final Merchant merchant;

    /**
     * Initialization of the LevelLoaderImpl class.
     *
     * @param fileName is the name of the file
     * @param level    corresponding to the level in the file
     */
    public LevelLoaderImpl(final String fileName, final Level level) {
        this.level = level;
        this.levelFromFile = new ArrayList<>();
        this.levelFromFile.addAll(new FileControllerImpl().read(PATH + fileName));
        this.enemyFactory = new EnemyFactoryImpl();
        this.collidableBlockFactory = new CollidableBlockFactoryImpl();
        this.merchant = new MerchantImpl(level, Vector2.zero(), new BoxCollider());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadLevel() {
        Vector2 position = Vector2.zero();
        for (int y = 0; y < this.levelFromFile.size(); y++) {
            final char[] identifiers = this.levelFromFile.get(y).toCharArray();
            for (int x = 0; x < identifiers.length; x++) {
                position = new Vector2(x, y).multiply(DISTANCE);
                this.parseToGameObject(identifiers[x], position);
            }
        }
        this.level.setLevelSize(position);
    }

    /**
     * Method to create the correct game object corresponding to the file input.
     *
     * @param identifier is the char associated to a specific game object in the
     *                   file
     * @param position   of the game object in the level
     */
    private void parseToGameObject(final char identifier, final Vector2 position) {
        final Collider collider = new BoxCollider();
        final Optional<GameObject> gameObject = switch (identifier) {
            case 'o' -> Optional.of(this.enemyFactory.createImp(this.level, position));
            case 'k' -> Optional.of(this.enemyFactory.createCentaur(this.level, position));
            case 't' -> Optional.of(this.enemyFactory.createTengu(level, position));
            case 'x' -> Optional.of(this.enemyFactory.createLotawiec(level, position));
            case '#' -> Optional.of(this.collidableBlockFactory.createCollidableBaseBlock(level, position));
            case 'l' -> Optional.of(this.collidableBlockFactory.createLavaBlock(level, position));
            case 'v' -> Optional.of(this.collidableBlockFactory.createVinesBlock(level, position));
            case '-' -> Optional.of(new BaseNonCollidableBlock(level, position));
            case 'e' -> Optional.of(new SpringsEntrance(level, position));
            case 'p' -> {
                final ShopEntrance shopEntrance = new ShopEntrance(level, position);
                shopEntrance.setMerchant(this.merchant);
                yield Optional.of(shopEntrance);
            }
            case 'c' -> Optional.of(new CharacterChanger(level, position, collider, level.getCharacters()));
            case 's' -> Optional.of(new SavePoint(level, position, collider));
            case 'm' -> {
                this.merchant.setPosition(position);
                yield Optional.of(this.merchant);
            }
            case ' ' -> Optional.empty();
            default -> throw new IllegalStateException("Cannot recognize a character in the file:" + identifier);
        };
        gameObject.ifPresent(this.level::addGameObject);
    }
}
