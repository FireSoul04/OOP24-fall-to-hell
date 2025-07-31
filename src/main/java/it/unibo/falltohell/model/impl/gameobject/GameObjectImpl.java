package it.unibo.falltohell.model.impl.gameobject;

import java.util.Optional;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.falltohell.model.api.drawable.Drawable;
import it.unibo.falltohell.util.Priority;
import it.unibo.falltohell.model.api.gameobject.GameObject;
import it.unibo.falltohell.model.api.physics.Collider;
import it.unibo.falltohell.model.impl.drawable.Sprite;
import it.unibo.falltohell.util.Vector2;
import it.unibo.falltohell.model.api.level.Level;

/**
 * Default implementation of the {@link GameObject} interface.
 * Represents a generic object in the game world, with position, size, solidity,
 * collider, and a reference to the level it belongs to. Upon creation, the
 * object
 * is automatically added to the specified level.
 * @author Casadei Lorenzo
 */
public class GameObjectImpl implements GameObject {
    private static final String EXPOSE_REP = "EI_EXPOSE_REP2";
    private static final String OVERRIDABLE_IN_CONSTRUCCTOR = "MC_OVERRIDABLE_METHOD_CALL_IN_CONSTRUCTOR";
    private static final String JUSTIFICATION = "Level is passed and stored for controlled access within game logic"
        + "The level must add the GameObject here.";
    private final Level level;
    private final Optional<Collider> collider;
    private Vector2 pos;
    private boolean isSolid;
    private Optional<Drawable> drawable;

    /**
     * Base constructs for the GameObject.
     * @param lv the level of this GameObject.
     * @param position the position of this GameObject.
     * @param isSolid if the GameObject is solid.
     * @param collider the collider of this GameObject.
     * @param drawable the drawable of this GameObject.
     */
    @SuppressFBWarnings(
    value = { EXPOSE_REP, OVERRIDABLE_IN_CONSTRUCCTOR },
    justification = JUSTIFICATION
    )
    public GameObjectImpl(final Level lv, final Vector2 position, final boolean isSolid,
                          final Optional<Collider> collider, final Optional<Drawable> drawable) {
        this.level = lv;
        this.pos = position;
        this.isSolid = isSolid;
        this.collider = collider;
        this.drawable = drawable;
        lv.addGameObject(this);
    }
    /**
     * Constructs a solid GameObject and adds it to the specified level.
     *
     * @param lv       the level to which this object will be added
     * @param position the position of the object
     */
    @SuppressFBWarnings(
    value = { EXPOSE_REP, OVERRIDABLE_IN_CONSTRUCCTOR },
    justification = JUSTIFICATION
    )
    public GameObjectImpl(final Level lv, final Vector2 position) {
        this(lv, position, true, Optional.empty(), Optional.empty());
    }

    /**
     * Constructs a solid GameObject and adds it to the specified level.
     *
     * @param lv       the level to which this object will be added
     * @param position the position of the object
     * @param collider the collider for this object
     */
    @SuppressFBWarnings(
    value = { EXPOSE_REP, OVERRIDABLE_IN_CONSTRUCCTOR },
    justification = JUSTIFICATION
    )
    public GameObjectImpl(final Level lv, final Vector2 position, final Collider collider) {
        this(lv, position, true, Optional.of(collider), Optional.empty());
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
    @SuppressFBWarnings(
    value = { EXPOSE_REP, OVERRIDABLE_IN_CONSTRUCCTOR },
    justification = JUSTIFICATION
    )
    public GameObjectImpl(final Level lv, final Vector2 position, final boolean isSolid, final Collider collider) {
        this(lv, position, isSolid, Optional.of(collider), Optional.empty());
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
    public Optional<Collider> getCollider() {
        return this.collider;
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
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP",
    justification = "GameObject must access its Level for logic such as registration and rendering"
    )
    @Override
    public final Level getLevel() {
        return this.level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * @param priority of the image associated to the game object
     * @param fileName of the image associated to the game object
     */
    protected final void initDrawable(final Priority priority, final String fileName) {
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
     * @param priority of the image associated to the game object
     * @param fileName of the image associated to the game object
     */
    protected final void initDrawable(final Vector2 offset, final Priority priority, final String fileName) {
        this.drawable = Optional.of(new Sprite(this, offset, priority));
        this.drawable.ifPresent(value -> {
            if (value instanceof Sprite) {
                this.level.getDrawableRenderableHandler().linkSprite(value, fileName);
            }
        });
    }
}
