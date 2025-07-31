package it.unibo.falltohell.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.controller.api.LevelLoader;
import it.unibo.falltohell.model.api.factory.EnemyFactory;
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

/**
 * Class that handles the loading of a level from file.
 *
 * @author Martina Malagoli
 */
public final class LevelLoaderImpl implements LevelLoader {

    private static final String PATH = "/level/";
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
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The level loader must be able to tell the level what there is inside it"
            + " and where to place it"
    )
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
        switch (identifier) {
            case 'o' -> this.enemyFactory.createImp(this.level, position);
            case 'k' -> this.enemyFactory.createCentaur(this.level, position);
            case 't' -> this.enemyFactory.createTengu(this.level, position);
            case 'x' -> this.enemyFactory.createLotawiec(this.level, position);
            case '#' -> this.collidableBlockFactory.createCollidableBaseBlock(this.level, position);
            case 'l' -> this.collidableBlockFactory.createLavaBlock(this.level, position);
            case 'v' -> this.collidableBlockFactory.createVinesBlock(this.level, position);
            case '-' -> new BaseNonCollidableBlock(this.level, position);
            case 'e' -> new SpringsEntrance(this.level, position);
            case 'p' -> new ShopEntrance(this.level, position).setMerchant(this.merchant);
            case 'c' -> new CharacterChanger(this.level, position, collider, this.level.getCharacters());
            case 's' -> new SavePoint(this.level, position, collider);
            case 'm' -> this.merchant.setPosition(position);
            case ' ' -> { }
            default -> throw new IllegalStateException("Cannot recognize a character in the file:" + identifier);
        }
    }
}
