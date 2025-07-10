package it.unibo.falltohell.model.impl.gameobjects.movable.entity;

import it.unibo.falltohell.model.api.GameObject;
import it.unibo.falltohell.model.api.Level;
import it.unibo.falltohell.model.api.gameobjects.Interactable;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.Character;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.CharacterStatistics;
import it.unibo.falltohell.model.api.gameobjects.movable.entity.statistic.buff.BuffManager;
import it.unibo.falltohell.model.impl.GameEventManager;
import it.unibo.falltohell.model.impl.gameobjects.movable.EntityImpl;
import it.unibo.falltohell.model.impl.gameobjects.movable.entity.statistics.buff.BuffManagerImpl;
import it.unibo.falltohell.model.impl.physics.colliders.BoxCollider;
import it.unibo.falltohell.util.Vector2;

/**
 * Base class for a character.
 * Every character has different parameters for life, attack, attack speed, speed, mana.
 *
 * @author Davide Mancini
 */
public abstract class BaseCharacter extends EntityImpl implements Character {

    private static final int MAX_JUMP_HEIGHT = 10;
    private static final Vector2 JUMP_ACCELERATION_STEP = new Vector2(0.0, -0.125);

    private final GameEventManager<String> input;
    private final BuffManager buffManager;
    private final Vector2 jumpAcceleration;
    private int currentJumpHeight;
    private boolean onGround;
    private boolean canInteract;

    /**
     * Base constructor for a new character.
     *
     * @param level
     * @param position
     * @param stats
     */
    public BaseCharacter(final Level level, final Vector2 position, final CharacterStatistics stats) {
        super(level, position, new BoxCollider(Vector2.zero(), stats.getDimensions()), stats);
        this.canInteract = false;
        this.onGround = false;
        this.currentJumpHeight = 0;
        this.jumpAcceleration = JUMP_ACCELERATION_STEP;
        this.input = new GameEventManager<>();
        this.buffManager = new BuffManagerImpl(level.getTimerManager());

        this.input.addCondition("MoveLeft", () -> false);
        this.input.addCondition("MoveRight", () -> true);
        this.input.addCondition("MoveUp", () -> false);
        this.input.addCondition("MoveDown", () -> false);
        this.input.addCondition("Jump", () -> false);
    }

    /**
     * Moves the character left or right based on the direction read by the input.
     * How fast it moves depends on current speed.
     * If both direction are read at the same time, the character remains still.
     *
     * @param deltaTime
     */
    private void move(final double deltaTime) {
        Vector2 velocity = Vector2.zero();
        if (this.input.checkCondition("MoveLeft")) {
            velocity = velocity.add(Vector2.left());
        }
        if (this.input.checkCondition("MoveRight")) {
            velocity = velocity.add(Vector2.right());
        }
        velocity = velocity.multiply(this.getStats().getSpeed().x()).multiply(deltaTime);
        this.setPosition(this.getPosition().add(velocity));
    }

    /**
     * If the character is on ground it can jump until it reach max jump height or jump less than max height if the
     * jump key is released.
     */
    private void jump() {
        if (this.input.checkCondition("Jump")
            && (this.onGround || this.currentJumpHeight > 0 && this.currentJumpHeight < MAX_JUMP_HEIGHT)
        ) {
            this.currentJumpHeight++;
            this.onGround = false;
            this.setPosition(this.getPosition().add(
                this.jumpAcceleration.multiply(MAX_JUMP_HEIGHT - this.currentJumpHeight)
            ));
        }
        if (!this.input.checkCondition("Jump")) {
            this.currentJumpHeight = 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime) {
        this.input.update();
        this.canInteract = false;
        this.move(deltaTime);
    }

    /**
     * {@inheritDoc}
     * Notify if the character is on ground and check if player is colliding with an interactable.
     */
    @Override
    public void onCollision(final GameObject other, final Vector2 direction) {
        if (other.isSolid() && direction.equals(Vector2.up())) {
            this.currentJumpHeight = 0;
            this.onGround = true;
        }
        if (other instanceof Interactable) {
            this.canInteract = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interact(final Interactable interactable) {
        // TODO: Add an event (like key press) that when it happen the player will interact with the object
        if (this.canInteract) {
            interactable.interact();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuffManager getBuffManager() {
        return this.buffManager;
    }
}
