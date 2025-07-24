package it.unibo.falltohell.model.impl.gameobjects.block;

import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.BlockFactory;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that handles the creation of different types of block.
 * @author Martina Malagoli
 */
public class BlockFactoryImpl implements BlockFactory {

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseBlock createBaseBlock(final Level level, final Vector2 position) {
        return new BaseBlock(level, position, new BoxCollider());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseBlock createLavaBlock(final Level level, final Vector2 position) {
        return new LavaBlock(level, position, new BoxCollider());
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseBlock createVinesBlock(final Level level, final Vector2 position) {
        return new VinesBlock(level, position, new BoxCollider());
    }
}
