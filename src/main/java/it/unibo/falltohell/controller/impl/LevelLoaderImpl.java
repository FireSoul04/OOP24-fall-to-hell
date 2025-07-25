package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.LevelLoader;
import it.unibo.falltohell.model.api.EnemyFactory;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.BlockFactory;
import it.unibo.falltohell.model.api.gameobjects.Merchant;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.EnemyFactoryImpl;
import it.unibo.falltohell.model.impl.SavePoint;
import it.unibo.falltohell.model.impl.gameobjects.CharacterChanger;
import it.unibo.falltohell.model.impl.gameobjects.MerchantImpl;
import it.unibo.falltohell.model.impl.gameobjects.block.BaseNonCollidableBlock;
import it.unibo.falltohell.model.impl.gameobjects.block.CollidableBlockFactoryImpl;
import it.unibo.falltohell.model.impl.gameobjects.entrance.ShopEntrance;
import it.unibo.falltohell.model.impl.gameobjects.entrance.SpringsEntrance;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the loading of a level from file.
 * @author Martina Malagoli
 */
public class LevelLoaderImpl implements LevelLoader {

    private static final String PATH = "src/main/resources/level/";
    private static final double DISTANCE = 20;
    private final List<String> levelFromFile;
    private final Level level;
    private final EnemyFactory enemyFactory;
    private final BlockFactory blockFactory;
    private final Merchant merchant;

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
        this.blockFactory = new CollidableBlockFactoryImpl();
        this.merchant = new MerchantImpl(level, Vector2.zero(), new BoxCollider());

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
        switch (identifier) {
            case 'o' -> this.enemyFactory.createImp(this.level, position, character);
            case 'k' -> this.enemyFactory.createCentaur(this.level, position, character);
            case 't' -> this.enemyFactory.createTengu(level, position, character);
            case 'x' -> this.enemyFactory.createLotawiec(level, position, character);
            case '#' -> this.blockFactory.createBaseBlock(level, position);
            case 'l' -> this.blockFactory.createLavaBlock(level, position);
            case 'v' -> this.blockFactory.createVinesBlock(level, position);
            case '-' -> new BaseNonCollidableBlock(level, position);
            case 'e' -> new SpringsEntrance(level, position);
            case 'p' -> new ShopEntrance(level, position).setMerchant(this.merchant);
            case 'c' -> new CharacterChanger(level, position, collider, level.getCharacters());
            case 's' -> new SavePoint(level, position, collider);
            case 'm' -> this.merchant.setPosition(position);
            case ' ' -> {}
            default -> throw new IllegalStateException("Cannot recognize a character in the file:" + identifier);
        }
    }
}
