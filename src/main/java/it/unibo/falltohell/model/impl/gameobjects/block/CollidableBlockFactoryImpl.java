package it.unibo.falltohell.model.impl.gameobjects.block;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.CollidableBlockFactory;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that handles the creation of different types of block.
 * @author Martina Malagoli
 */
public class CollidableBlockFactoryImpl implements CollidableBlockFactory {

    private static final Vector2 OFFSET_LAVA = new Vector2(0.0, 0.5);
    private static final Vector2 OFFSET_VINES = new Vector2(0.0, 3.0);

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseCollidableBlock createCollidableBaseBlock(final Level level, final Vector2 position) {
        return new BaseCollidableBlock(level, position, new BoxCollider(), "base_block.png");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseCollidableBlock createLavaBlock(final Level level, final Vector2 position) {
        return new LavaBlock(level, position, new BoxCollider(), "lava_block.png", OFFSET_LAVA);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseCollidableBlock createVinesBlock(final Level level, final Vector2 position) {
        return new VinesBlock(level, position, new BoxCollider(), "vines_block.png", OFFSET_VINES);
    }
}
