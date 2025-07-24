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
    private final Vector2 offset;
    private final Priority priority;
    /**
     * Default initialization of the Sprite class.
     * @param gameObject is the game object associated with this drawable object
     * @param priority of the sprite when it has to be rendered
     */
    public Sprite(final GameObject gameObject, Priority priority) {
        this(gameObject, Vector2.zero(), priority);
    }

    /**
     * Initialization of the Sprite class with customized offset information.
     * @param gameObject is the game object associated with this drawable object
     * @param offset is the vector used to move a sprite from the position of its associated collider
     * @param priority of the sprite when it has to be rendered
     */
    public Sprite(final GameObject gameObject, final Vector2 offset, Priority priority) {
        this.mirrored = false;
        this.visible = true;
        this.gameObject = gameObject;
        this.offset = offset;
        this.priority = priority;
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
        return this.gameObject.getPosition().add(this.offset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Priority getPriority() {
        return this.priority;
    }


}
