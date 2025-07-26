package it.unibo.falltohell.model.impl.factory;

import it.unibo.falltohell.model.api.level.Level;
import it.unibo.falltohell.model.api.factory.CollidableBlockFactory;
import it.unibo.falltohell.model.impl.gameobject.block.BaseCollidableBlock;
import it.unibo.falltohell.model.impl.gameobject.block.LavaBlock;
import it.unibo.falltohell.model.impl.gameobject.block.VinesBlock;
import it.unibo.falltohell.model.impl.physics.BoxCollider;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that handles the creation of different types of block.
 * @author Martina Malagoli
 */
public class CollidableBlockFactoryImpl implements CollidableBlockFactory {

    private static final Vector2 OFFSET_LAVA = Vector2.up().multiply(0.5);
    private static final Vector2 OFFSET_VINES = Vector2.up().multiply(3);

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
