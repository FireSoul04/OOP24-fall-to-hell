package it.unibo.falltohell.model.impl;

import java.util.Optional;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.Level;

/**
 * Default implementation of the {@link GameObject} interface.
 * <p>
 * Represents a generic object in the game world, with position, size, solidity,
 * collider, and a reference to the level it belongs to. Upon creation, the
 * object
 * is automatically added to the specified level.
 * </p>
 */
public class GameObjectImpl implements GameObject {
    private Vector2 pos;
    private boolean isSolid;
    private Collider collider;
    private Level level;
    private Optional<Drawable> drawable;

    /**
     * Constructs a solid GameObject and adds it to the specified level.
     *
     * @param lv       the level to which this object will be added
     * @param position the position of the object
     * @param width    the width (in tiles) of the object
     * @param height   the height (in tiles) of the object
     * @param collider the collider for this object
     */
    public GameObjectImpl(Level lv, Vector2 position, Collider collider) {
        this.pos = position;
        this.isSolid = true; // Default
        this.collider = collider;
        lv.addGameObject(this);
        this.level = lv;
        this.drawable = Optional.empty();

    }

    /**
     * Constructs a GameObject with a specified solidity and adds it to the
     * specified level.
     *
     * @param lv       the level to which this object will be added
     * @param position the position of the object
     * @param width    the width (in tiles) of the object
     * @param height   the height (in tiles) of the object
     * @param isSolid  whether the object is solid
     * @param collider the collider for this object
     */
    public GameObjectImpl(Level lv, Vector2 position, boolean isSolid, Collider collider) {
        this.pos = position;
        this.isSolid = isSolid;
        this.collider = collider;
        lv.addGameObject(this);
        this.level = lv;
        this.drawable = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    public Vector2 getPosition() {
        return this.pos;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSolid() {
        return this.isSolid;
    }

    /**
     * {@inheritDoc}
     */
    public void setPosition(Vector2 position) {
        this.pos = position;
    }

    /**
     * {@inheritDoc}
     */
    public void setSolid(boolean solid) {
        this.isSolid = solid;
    }

    /**
     * {@inheritDoc}
     */
    public Collider getCollider() {
        return this.collider;
    }

    /**
     * {@inheritDoc}
     */
    public void onCollision(GameObject other) {

    }

    /**
     * {@inheritDoc}
     */
    public void onCollision(GameObject other, Vector2 direction) {

    }

    /**
     * {@inheritDoc}
     */
    public void onCollisionExit(GameObject other, Vector2 direction) {

    }

    /**
     * {@inheritDoc}
     */
    public Level getLevel() {
        return this.level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {

    }

    public Optional<Drawable> getDrawable() {
        return drawable;
    }

    public void setDrawable(Optional<Drawable> drawable) {
        this.drawable = drawable;
    }

    /**
     * Initializes the graphical representation of this entity by associating it
     * with a {@link Sprite}.
     * <p>
     * This method should be called by subclasses <b>after</b> their construction is
     * complete,
     * to ensure that {@code this} refers to the fully initialized subclass
     * instance.
     * It sets the drawable of the entity using {@link #setDrawable} and wraps the
     * sprite in an {@link Optional}.
     *
     * @implNote This method avoids invoking {@code setDrawable(new Sprite(this))}
     *           inside the constructor
     *           to prevent premature access to uninitialized subclass state during
     *           object construction.
     *
     * @see Sprite
     *
     */
    protected void initDrawable() {
        this.setDrawable(Optional.of(new Sprite(this)));
    }

    /**
     * Initializes the graphical representation of this entity with a custom offset
     * by associating it with a {@link Sprite}.
     *
     * <p>
     * Should be called after subclass construction, just like
     * {@link #initDrawable()}.
     *
     * @implNote This method avoids invoking {@code setDrawable(new Sprite(this))}
     *           inside the constructor
     *           to prevent premature access to uninitialized subclass state during
     *           object construction.
     *
     * @param offset the {@link Vector2} offset to apply to the sprite's position
     * @see Sprite
     */
    protected void initDrawable(final Vector2 offset) {
        this.setDrawable(Optional.of(new Sprite(this, offset)));
    }
}
