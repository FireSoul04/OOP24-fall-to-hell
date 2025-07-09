package it.unibo.falltohell.model.impl;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.util.Vector2;

/**
 * Class that represents the sprite associated to a specific drawable object and
 * that handles information about its rendering.
 * @author Martina Malagoli
 */
public class Sprite implements Drawable {

    private boolean mirrored;
    private boolean visible;
    private final GameObject gameObject;
    
    /**
     * Default initialization of the Sprite class.
     * @param gameObject is the game object associated with this drawable object
     */
    public Sprite(final GameObject gameObject) {
        this(gameObject, false);
    }

    /**
     * Initialization of the Sprite class with customized mirroring information.
     * @param mirroring tells if the Sprite should be initialized mirrored or not
     * @param gameObject is the game object associated with this drawable object
     */
    public Sprite(final GameObject gameObject, final boolean mirroring) {
        this.mirrored = mirroring;
        this.visible = true;
        this.gameObject = gameObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mirror(final boolean mirroring) {
        this.mirrored = mirroring;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMirrored() {
        return this.mirrored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visibility) {
        this.visible = visibility;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPosition() {
        return this.gameObject.getPosition();
    }
}
