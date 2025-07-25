package it.unibo.falltohell.model.impl;

import java.util.Optional;

import it.unibo.falltohell.model.api.Drawable;
import it.unibo.falltohell.model.api.Drawable.Priority;
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
     * @param collider the collider for this object
     */
    public GameObjectImpl(final Level lv, final Vector2 position, final Collider collider) {
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
     * @param isSolid  whether the object is solid
     * @param collider the collider for this object
     */
    public GameObjectImpl(final Level lv, final Vector2 position, final boolean isSolid, final Collider collider) {
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
    @Override
    public Vector2 getPosition() {
        return this.pos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSolid() {
        return this.isSolid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Vector2 position) {
        this.pos = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSolid(final boolean solid) {
        this.isSolid = solid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collider getCollider() {
        return this.collider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCollisionExit(final GameObject other, final Vector2 direction) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * Initializes the graphical representation of this entity by associating it
     * with a {@link Sprite}.
     * <p>
     * This method should be called by subclasses <b>after</b> their construction is
     * complete,
     * to ensure that {@code this} refers to the fully initialized subclass
     * instance.
     * It sets the drawable object of the entity and wraps the
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
    protected void initDrawable(final Priority priority, final String fileName) {
        this.initDrawable(Vector2.zero(), priority, fileName);
    }

    /**
     * Initializes the graphical representation of this entity with a custom offset
     * by associating it with a {@link Sprite}.
     *
     * <p>
     * Should be called after subclass construction.
     *
     * @implNote This method avoids invoking {@code setDrawable(new Sprite(this))}
     *           inside the constructor
     *           to prevent premature access to uninitialized subclass state during
     *           object construction.
     *
     * @param offset the {@link Vector2} offset to apply to the sprite's position
     * @see Sprite
     */
    protected void initDrawable(final Vector2 offset, final Priority priority, final String fileName) {
        this.drawable = Optional.of(new Sprite(this, offset, priority));
        this.drawable.ifPresent(value -> this.level.getDrawableRenderableHandler().linkSprite(value, fileName));
    }
}
