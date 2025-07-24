package it.unibo.falltohell.model.impl.gameobjects.block;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.BlockFactory;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Dimensions;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that handles the creation of different types of block.
 * @author Martina Malagoli
 */
public class BlockFactoryImpl implements BlockFactory {

    private static final Dimensions BLOCK_DIMENSION = new Dimensions(GameObject.TILE_SIZE, GameObject.TILE_SIZE);

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseBlock createBaseBlock(final Level level, final Vector2 position) {
        return new BaseBlock(level, position, 0, 0, new BoxCollider(BLOCK_DIMENSION), "BaseBlock.png");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseBlock createLavaBlock(final Level level, final Vector2 position) {
        return new LavaBlock(level, position, 0, 0, new BoxCollider(BLOCK_DIMENSION), "LavaBlock.png");
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public BaseBlock createVinesBlock(final Level level, final Vector2 position) {
        return new VinesBlock(level, position, 0, 0, new BoxCollider(BLOCK_DIMENSION), "VinesBlock.png");
    }
}
